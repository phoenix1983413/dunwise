# Dunwise 公司级代码脚手架设计方案

> 版本：v1.0（设计稿，待确认）
> 日期：2026-09-18
> 依据：《Dunwise 整体技术架构规划.docx》四层架构 + 本机 workspace-java 现有工程实践（spiano-cloud / spiano-cloud-template / dunwisecode / pig / soloncode）
> 性质：**本次仅做设计规划，不生成代码**

---

## 一、设计目标与原则

| # | 原则 | 说明 |
|---|------|------|
| 1 | 能力复用 | 底层 mica + starters 同时服务单体与微服务，业务不写两套代码 |
| 2 | 可插拔 | 所有能力 Starter 按需引入，不用就不依赖 |
| 3 | AI 能力中心化 | 所有 LLM 请求统一走 `dunwise-ai`，业务不感知模型厂商 |
| 4 | 平滑迁移 | boot 项目改依赖/加模块即可升级到 cloud 微服务 |
| 5 | 多端一致 | boot/cloud 对外 REST 接口规范统一，uniapp 直接复用 |
| 6 | 版本统一 | BOM 父工程集中管理全部模块与三方版本（对应文档"现存可优化点"第 1 条） |
| 7 | 开箱即用 | 模板工程内置 RBAC、登录、代码生成、Flyway 迁移，`git clone` 即可起项目 |

## 二、总体仓库规划（关键决策）

**推荐：A 方案 —— "1 个框架 Monorepo + 3 个独立产品仓"**

```
┌──────────────────────────────────────────────────────┐
│  dunwise（框架 Monorepo，Maven 多模块）                  │
│  mica + bom + starters + boot + cloud + generator      │
│  + monitor + deploy                                   │
└──────────────────────────────────────────────────────┘
┌──────────────┐ ┌──────────────┐ ┌──────────────────────┐
│ dunwise-ui   │ │ dunwise-     │ │ AI 产品（已有/独立）   │
│ (Vue3 前端)   │ │ uniapp(多端)  │ │ dunwisecode         │
│ boot-ui/cloud│ │              │ │ dunwiseskills       │
│ -ui 共仓      │ │              │ │ dunwiseclaw         │
└──────────────┘ └──────────────┘ └──────────────────────┘
```

| 仓库 | 内容 | 理由 |
|------|------|------|
| `dunwise` | 框架层 + 脚手架模板（本方案核心） | 版本联动强（BOM 管理一切），monorepo 避免"改一个 starter 发五个仓"的版本地狱；对标 pig 单仓模式 |
| `dunwise-ui` | boot-ui / cloud-ui（Vue3 + AntD） | 前后端发布节奏不同，独立仓独立流水线 |
| `dunwise-uniapp` | APP/小程序/H5/鸿蒙多端 | 独立技术栈、独立发布渠道 |
| AI 产品仓 ×3 | dunwisecode（已有）/ dunwiseskills / dunwiseclaw | 独立产品、独立商业化节奏，通过 `dunwise-ai` starter 与框架解耦 |

**统一坐标**：`groupId = com.dunwise`（与现有 dunwisecode-parent 一致），版本号格式 `YYYY.M.x`（对齐 dunwisecode-parent 的 `2026.8.20` 风格，dunwise 框架建议改为 `1.0.0` 语义化 + `-SNAPSHOT`，便于 BOM 锁定，列为决策点 D4）。

## 三、框架 Monorepo 整体目录结构

```
dunwise/                                        # 框架 Monorepo
├── pom.xml                                     # 根 POM：聚合 + 插件管理（compiler/surefire/flatten）
├── dunwise-dependencies/                       # 【三方版本管理中心】只管 dependencyManagement，无代码
├── dunwise-bom/                                # 【自家组件 BOM】管理 mica/starters 版本，业务工程 import 即用
│
├── dunwise-mica/                               # ── 第 1 层：底层基础工具（无 Spring 强依赖，对标 Hutool+mica）
│   ├── pom.xml                                 #    聚合 POM
│   ├── dunwise-mica-core                       #    字符串/集合/日期/验证码/XSS 过滤/ip2region/常量/异常基类
│   ├── dunwise-mica-crypto                     #    国密 SM2/SM3/SM4、AES/RSA、数据脱敏
│   ├── dunwise-mica-redis                      #    Redis 工具、Redisson 分布式锁、分布式 ID、幂等
│   ├── dunwise-mica-http                       #    HTTP 客户端封装（RestClient/OkHttp 适配）
│   ├── dunwise-mica-oss                        #    OSS 统一抽象：本地/MinIO/阿里云/华为 OBS 适配器
│   ├── dunwise-mica-excel                      #    EasyExcel 封装（导入导出模板）
│   ├── dunwise-mica-pdf                        #    PDF 解析/导出
│   └── dunwise-mica-concurrency                #    线程池、重试、异步工具
│
├── dunwise-starters/                           # ── 第 2 层：可插拔能力 Starter（Spring Boot Starter）
│   ├── pom.xml                                 #    聚合 POM，全部从 dunwise-mica 取工具
│   ├── dunwise-spring-boot-starter-web          #    Web 通用：全局异常/统一返回/日志链路/参数校验/Swagger
│   ├── dunwise-spring-boot-starter-security     #    RBAC+数据权限+OAuth2/OIDC+第三方登录+账号安全策略
│   ├── dunwise-spring-boot-starter-mybatis      #    MP 增强：自动填充/逻辑删除/分页/多租户/数据权限插件
│   ├── dunwise-spring-boot-starter-ai           #    ★ AI 统一网关：多模型适配/路由/Key 管理/限流熔断/MCP/Skill
│   ├── dunwise-spring-boot-starter-notify       #    站内信/邮件/短信/钉钉/飞书/企微，MQ 异步+模板
│   ├── dunwise-spring-boot-starter-job          #    XXL-Job 封装：动态任务管理/执行日志
│   ├── dunwise-spring-boot-starter-flow         #    Flowable 封装（设计器 UI 在 dunwise-ui）
│   ├── dunwise-spring-boot-starter-monitor      #    埋点/健康检查/Prometheus/Spring Boot Admin
│   ├── dunwise-spring-boot-starter-gateway      #    仅 cloud 用：网关鉴权/黑名单/限流/日志/动态路由
│   └── dunwise-spring-boot-starter-event        #    统一事件总线（文档优化点 3）：RocketMQ/Kafka ↔ 本地事件自适应
│
├── dunwise-boot/                               # ── 第 3 层 A：单体脚手架模板
│   ├── pom.xml                                 #    import dunwise-bom，按需引 starter
│   ├── dunwise-boot-api                        #    对外 REST API 定义 + DTO（uniapp/cloud 复用契约）
│   ├── dunwise-boot-system                     #    系统管理：用户/角色/菜单/RBAC/字典/日志
│   ├── dunwise-boot-biz                        #    业务示例模块（演示标准分层，交付前删除）
│   └── dunwise-boot-start                      #    启动模块：Application + 配置 + Flyway 迁移
│
├── dunwise-cloud/                              # ── 第 3 层 B：微服务脚手架模板
│   ├── pom.xml                                 #    import dunwise-bom
│   ├── dunwise-cloud-gateway                   #    网关服务（dunwise-spring-boot-starter-gateway）
│   ├── dunwise-cloud-auth                      #    认证授权服务（三模块：-api/-biz/-start，下同）
│   ├── dunwise-cloud-system                    #    系统管理服务
│   ├── dunwise-cloud-ai                        #    AI 服务（复用 dunwise-ai starter，独立部署）
│   └── dunwise-cloud-biz                       #    业务服务示例（含 Feign 调用示范）
│
├── dunwise-generator/                          # 代码生成器：后端 CRUD + Vue3 页面，读取 PG 元数据
├── dunwise-monitor/                            # 独立监控服务（Spring Boot Admin Server + 聚合）
├── dunwise-tests/                              # 跨模块集成测试 + 架构约束测试（ArchUnit）
├── docker/                                     # 一键环境：PG18+PostGIS/Redis/Nacos/Sentinel/XXL-Job/MinIO
│   └── docker-compose.yml
└── docs/                                       # 架构图、开发规范、Starter 使用手册
```

### 业务服务的标准三模块结构（沿用团队已验证的 spiano 模板模式）

```
dunwise-cloud-biz/                              # 每个微服务 = 3 模块
├── dunwise-cloud-biz-api                       # ① Feign 接口 + DTO + 枚举（供其他服务依赖，无实现）
├── dunwise-cloud-biz-biz                       # ② 业务实现
│   └── src/main/java/com/dunwise/biz/
│       ├── controller/                         #    REST 层：参数校验、组装返回，不写业务
│       ├── service/  service/impl/             #    业务层：事务边界、业务编排
│       ├── mapper/                             #    MyBatis-Plus Mapper
│       ├── repository/                         #    复杂查询/领域仓储（可选）
│       ├── entity/  dto/  vo/  convert/       #    实体/传输对象/视图对象/MapStruct 转换
│       └── config/                            #    模块内配置
└── dunwise-cloud-biz-start                     # ③ 启动壳：Application + application.yml
    └── src/main/resources/
        ├── application.yml  application-local.yml  application-prod.yml
        └── db/migration/                      #    Flyway：V1__init.sql / V2__xx.sql
```

**规则**：`-biz` 只依赖 `-api` 和 starters；`-start` 只依赖 `-biz`。boot 单体中同样按 `xxx-api / xxx-biz / xxx-start` 或 `xxx（单模块）` 组织（小项目允许合并，见决策点 D5）。

## 四、各层职责说明

### 4.1 底层基础工具层（dunwise-mica）

| 职责 | 约束 |
|------|------|
| 纯工具库：加解密、Redis/锁/ID、OSS 抽象、Excel/PDF、并发重试、HTTP、XSS、ip2region | **零 Spring 上下文强依赖、零 Bean 注入、零业务注解**；需要 Spring 的能力一律上移到 starter。被 boot、cloud、所有 starter、AI 产品共同依赖 |

### 4.2 可插拔能力 Starter 层

| Starter | 职责 | 关键设计 |
|---------|------|----------|
| starter-web | 统一返回体 `R<T>`、全局异常、链路日志、参数校验、API 文档（springdoc） | 所有服务的强制底座 |
| starter-security | RBAC 菜单权限、数据权限、OAuth2/OIDC、第三方登录、账号安全策略 | 单体走本地 Session/过滤器链；微服务走网关鉴权 + 资源服务器，同一套注解 `@PreAuthorize`/`@DataScope` |
| starter-mybatis | MP 自动填充（创建人/时间）、逻辑删除、分页、**多租户**、数据权限插件 | 多租户列 `tenant_id` 默认开启可关 |
| starter-ai ★ | OpenAI 兼容协议适配（DeepSeek/Kimi/GLM）、模型路由、API-Key 管理、调用统计、限流熔断、失败重试、**MCP 协议封装、Skill 调用抽象**、响应缓存（文档优化点 2） | 全公司唯一 LLM 出口；业务/dunwisecode/dunwiseclaw 一律走它 |
| starter-notify | 站内信/邮件/短信/钉钉/飞书/企微，模板管理 | 经 starter-event 异步发送 |
| starter-job | XXL-Job 封装、动态任务、执行日志 | boot 模式可降级为 @Scheduled |
| starter-flow | Flowable 7 封装，设计器 UI 集成 dunwise-ui | 可插拔，不引不生效 |
| starter-monitor | 埋点、健康检查、Micrometer → Prometheus | 自动注册 Spring Boot Admin |
| starter-gateway | 网关鉴权/黑名单/限流/请求日志/动态路由 | 仅 cloud 依赖 |
| starter-event | 统一事件总线 | cloud：RocketMQ/Kafka；boot：Spring 本地事件，同一套 `EventPublisher` API |

### 4.3 应用脚手架层

| 工程 | 形态 | 组成 | 适用场景 |
|------|------|------|----------|
| dunwise-boot | 单体 | 引需要的 starter，单实例部署；内置 system 模块开箱即用 | 中小项目、快速交付 |
| dunwise-cloud | 微服务 | Nacos 注册配置 + Sentinel + Gateway；gateway/auth/system/ai/biz 五服务示范 | 大型/高并发项目 |
| 迁移路径 | — | boot 的 entity/DTO/权限模型与 cloud 完全一致，改依赖 + 拆模块即迁移 | — |

### 4.4 AI 智能体产品层（本方案只做接口预留）

- `dunwisecode`（已有）、`dunwiseskills`、`dunwiseclaw` 独立仓库独立部署；
- 与脚手架的唯一桥接 = `dunwise-spring-boot-starter-ai`：业务调 Agent 能力、Agent 反调业务 REST（通过 boot/cloud-api 契约）；
- `dunwiseskill-sdk` 的安全校验逻辑在 starter-ai 的 Skill 加载器中强制执行。

## 五、技术选型建议

| 领域 | 选型 | 版本建议 | 说明 |
|------|------|----------|------|
| 语言/JDK | Java | **21 (LTS)** | 与 dunwisecode-parent、spiano 模板一致 |
| 单体框架 | Spring Boot | **4.1.x**（对齐 spiano 已验证版本）| 保守可选 3.5.x，见决策点 D2 |
| 微服务 | Spring Cloud + Spring Cloud Alibaba | 2025.x + 2025.x | Nacos 注册/配置、Sentinel 流控 |
| ORM | MyBatis-Plus | 最新稳定版 | 多租户/数据权限插件是刚需 |
| 数据库 | **PostgreSQL 18 + PostGIS** | — | 文档指定（覆盖架构图早期 MySQL 表述）；公安/安防项目常有地理围栏需求，PostGIS 是硬理由 |
| Schema 管理 | Flyway | — | spiano 模板已验证 V1__xxx.sql 模式，boot/cloud 统一 |
| 缓存/锁 | Redis 7 + Redisson | — | 分布式锁、分布式 ID、幂等 |
| 对象存储 | MinIO（默认），兼容 OSS/OBS | — | mica-oss 抽象 |
| 网关 | Spring Cloud Gateway | — | starter-gateway |
| 任务调度 | XXL-Job | — | starter-job |
| 工作流 | Flowable 7 | — | starter-flow |
| 安全 | Spring Security + OAuth2/OIDC | — | 文档指定；对标 pig |
| API 文档 | springdoc-openapi | — | starter-web 内置 |
| 对象转换 | MapStruct | — | 模板默认集成 |
| 事件总线 | RocketMQ（cloud）/ Spring Events（boot） | — | starter-event 统一 API |
| 监控 | Micrometer + Prometheus + Grafana + Spring Boot Admin | — | starter-monitor |
| LLM | DeepSeek / Kimi / GLM（OpenAI 兼容） | — | 全部经 starter-ai 代理 |
| AI 协议 | MCP + Skill SDK | — | starter-ai 内封装 |
| 前端 | Vue3 + Vite + TS + Pinia + Ant Design Vue 4 | — | boot-ui / cloud-ui |
| 多端 | UniApp (Vue3) | — | APP/小程序/H5/鸿蒙 |
| 构建 | Maven + flatten-maven-plugin | — | BOM 版本统一 |
| CI | Gitea/GitLab CI + ArchUnit 架构测试 | — | 包依赖约束自动化 |

## 六、版本与依赖管理（BOM 设计）

```
dunwise-dependencies (父，管三方版本)
   └── dunwise-bom (管自家 mica + starters 版本)
          └── 业务工程只需：
              <dependencyManagement>
                <dependency>
                  <groupId>com.dunwise</groupId>
                  <artifactId>dunwise-bom</artifactId>
                  <version>1.0.0</version> <type>pom</type> <scope>import</scope>
                </dependency>
              </dependencyManagement>
```

- 业务工程**禁止**手写三方版本号，统一由 BOM 锁定；
- BOM 发版走 CI：单测 → ArchUnit 架构检查 → deploy 到私服（Nexus/私仓）；
- boot 与 cloud 模板仅是 BOM 的两个"预组装套餐"，版本始终一致。

## 七、落地实施步骤（7 个阶段）

| 阶段 | 内容 | 交付物/验收标准 | 预估 |
|------|------|----------------|------|
| **P0 地基** | 建 monorepo；根 POM + dependencies + bom；docker/ 一键起 PG/Redis/Nacos；CI 流水线 | `mvn install` 全绿；docker compose up 环境可用 | 0.5~1 周 |
| **P1 mica 层** | mica-core/crypto/redis/oss/http 先行，其余按需；对标 gitee mica 按需裁剪适配 | mica 单测覆盖核心工具；无私服可 deploy | 1~2 周 |
| **P2 核心 starters** | web / mybatis / security 三个先行（RBAC 后台的硬依赖） | 一个 demo 应用引入三件套即完成登录+CRUD+分页 | 2~3 周 |
| **P3 boot 模板** | dunwise-boot 四模块 + boot-ui 基础后台（登录/用户/角色/菜单/字典）；Flyway 初始化 | clone → 改配置 → 起服务 → 后台可用，作为公司标准交付起点 | 2~3 周 |
| **P4 cloud 模板** | gateway/auth/system/ai/biz 五服务 + Nacos/Sentinel 接入；boot→cloud 迁移演练一次 | 五服务注册发现、网关鉴权、Feign 互通 | 2~3 周 |
| **P5 增值 starters** | ai / notify / job / flow / monitor / event / generator | generator 一键生成前后端 CRUD；监控大盘出图 | 3~4 周（ai 可与 P4 并行） |
| **P6 多端 + AI 产品接入** | uniapp 工程；dunwisecode / dunwiseskills / dunwiseclaw 经 starter-ai 接入 | 多端登录跑通；Skill 安全校验链路闭环 | 按产品排期 |

**里程碑策略**：P0~P3 完成即具备"公司标准单体脚手架"可用版本（最快对外交付线），P4 后具备微服务能力，P5 后能力完整。

## 八、与现有资产的关系

| 现有资产 | 处理方式 |
|----------|----------|
| spiano-cloud（common/starters/system 等） | **经验平移，不直接改名**：spiano-common-* 的分层思路映射到 dunwise starters；成熟代码可按模块择优迁移（剔除 spiano 品牌绑定部分） |
| spiano-cloud-template（api/biz/start 三模块） | **直接继承该模式**，作为 dunwise 业务服务标准结构 |
| dunwisecode（已有 monorepo） | 保持在 AI 产品层独立，通过 starter-ai 桥接，groupId `com.dunwise` 已一致 |
| pig / Jeecg | RBAC 权限模型与 system 模块设计参考（菜单-角色-用户-数据权限四张核心表） |
| soloncode | Agent/Skill 封装方式参考，映射进 starter-ai 的 Skill 抽象 |
| gitee mica（net.dreamlu） | dunwise-mica 的直接对标，可引入源码级适配（注意 License 与包名隔离） |

## 九、待确认决策点（A/B/C/D 选答）

| # | 决策 | 选项 |
|---|------|------|
| **D1 仓库策略** | 框架怎么拆仓 | A. 框架 Monorepo + 产品独立仓（本方案推荐）/ B. 框架与模板也分仓 / C. 全部 Monorepo |
| **D2 Spring Boot 版本** | 底线版本 | A. 4.1.x 对齐 spiano 已验证栈（推荐）/ B. 3.5.x 保守稳妥（生态兼容性最好） |
| **D3 Starter 命名** | artifactId 风格 | A. `dunwise-spring-boot-starter-xxx`（业界标准，推荐）/ B. `dunwise-xxx`（按架构文档原样） |
| **D4 版本号策略** | 框架版本格式 | A. `1.0.0` 语义化 + SNAPSHOT（推荐，利于 BOM 锁定）/ B. `YYYY.M.x` 对齐 dunwisecode 风格 |
| **D5 boot 业务模块粒度** | 单体模板内部结构 | A. 多模块 api/system/biz/start（与 cloud 完全同构，推荐）/ B. 单模块精简（更贴合小项目） |
| **D6 security 实现** | 鉴权技术路线 | A. Spring Security + OAuth2（文档指定，对标 pig，推荐）/ B. Sa-Token（更轻，国内后台常用） |
| **D7 数据库确认** | 架构图写 MySQL、正文写 PG18 | A. **PostgreSQL 18 + PostGIS**（正文为准，推荐）/ B. MySQL 8（生态熟悉度高） |

---

## 十、决策锁定（v1.1，2026-09-18 确认）

deane 已确认 **D1~D7 全部按推荐方案执行**：

| # | 决策项 | 锁定结论 |
|---|--------|----------|
| D1 | 仓库策略 | **A. 框架 Monorepo + 产品独立仓** |
| D2 | Spring Boot 版本 | **A. 4.1.x**（对齐 spiano 已验证栈） |
| D3 | Starter 命名 | **A. `dunwise-spring-boot-starter-xxx`**（业界标准前缀） |
| D4 | 版本号策略 | **A. `1.0.0-SNAPSHOT` 语义化 + BOM 锁定** |
| D5 | boot 业务模块粒度 | **A. 多模块 api/system/biz/start**（与 cloud 同构） |
| D6 | security 实现 | **A. Spring Security + OAuth2/OIDC** |
| D7 | 数据库 | **A. PostgreSQL 18 + PostGIS** |

## 十一、实施反馈（P0 落地时发现，v1.1 补充）

以下 5 点是在实际搭建 P0 骨架时必须修正的设计细节，均已按后者落地：

| # | 设计稿原描述 | 实际落地方案与原因 |
|---|--------------|--------------------|
| 1 | BOM 作为普通子模块（继承根 POM） | **两个 BOM 改为独立无父 POM**。否则 `dunwise-parent` import `dunwise-bom` 会形成 `parent → bom → parent` 循环，Maven 直接报错（`dependencies ... form a cycle`）。独立坐标同时便于发布私服供业务工程单独引用 |
| 2 | 未明确 JSON 库版本假设 | **Spring Boot 4 已迁移至 Jackson 3**：JSON 包名从 `com.fasterxml.jackson.*` 变为 `tools.jackson.*`，业务与测试代码 import 需注意；`spring.jackson.*` 配置项仍有效 |
| 3 | 国密依赖"由 hutool 提供" | **必须显式引入 `org.bouncycastle:bcprov-jdk18on`**：hutool-crypto 将 BC 声明为 optional，不传递；且 **BC 未注册 "SM2" 的 KeyFactory**，SM2 密钥必须用 `KeyFactory.getInstance("EC", "BC")` 解析 |
| 4 | Flowable 7 | 升级为 **Flowable 8.0.0**（与 Java 21 + Spring Boot 4 世代对齐）；仍在 BOM 中预置版本，依赖以注释形式预留，启用前需做兼容性验证 |
| 5 | "starter 全部实装" | 重型依赖（Flowable、Redisson、MinIO、Micrometer 等）在骨架阶段以 **XML 注释形式预留**，避免首轮构建耗时过长；模块 POM 与 README 已标明启用阶段 |

**已交付（P0）**：Monorepo 骨架与双层 BOM；mica-core / mica-crypto / mica-concurrency 实装并通过 15 个单测；starter-web 实装（全局异常 + 链路 ID + 响应增强）；boot 最小可运行应用并通过 6 个 HTTP 冒烟用例；docker 基础设施编排；GitLab CI 四阶段流水线；README 与规范/路线图文档。

**下一步**：按 P1（mica 补齐）→ P2（security / mybatis）→ P3（boot 完整化 + boot-ui）推进，详细拆分见仓库内 `docs/03-实施路线图.md`。

