package com.dunwise.boot.system.controller;

import com.dunwise.boot.system.entity.User;
import com.dunwise.boot.system.service.UserService;
import com.dunwise.boot.system.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/users")
@Tag(name = "用户管理", description = "用户 CRUD")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/me")
    public UserVO me() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setNickname("Administrator");
        user.setStatus(CommonConstants.YES);

        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setStatus(user.getStatus());
        return vo;
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/{id}")
    public UserVO update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateById(user);

        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        return vo;
    }
}
