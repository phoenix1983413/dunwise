package com.dunwise.boot.system.controller;

import com.dunwise.boot.system.dto.UserLoginDTO;
import com.dunwise.boot.system.dto.UserRegisterDTO;
import com.dunwise.boot.system.service.UserService;
import com.dunwise.boot.system.vo.TokenVO;
import com.dunwise.boot.system.vo.UserVO;
import com.dunwise.mica.core.constant.CommonConstants;
import com.dunwise.starter.security.jwt.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证管理", description = "用户登录、注册、令牌刷新")
public class AuthController {

    private final UserService userService;

    private final JwtTokenService jwtTokenService;

    public AuthController(UserService userService, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public TokenVO login(@Valid @RequestBody UserLoginDTO dto) {
        UserVO userVO = new UserVO();
        String token = jwtTokenService.generateToken(dto.getUsername(), "1");

        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(token);
        tokenVO.setExpiresIn(jwtTokenService.getExpirationDateFromToken(token).getTime());
        tokenVO.setUsername(dto.getUsername());
        tokenVO.setUserId("1");
        return tokenVO;
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public void register(@Valid @RequestBody UserRegisterDTO dto) {
    }

    @Operation(summary = "刷新令牌")
    @PostMapping("/refresh")
    public TokenVO refresh(@RequestHeader(CommonConstants.AUTHORIZATION) String authorization) {
        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(authorization);
        return tokenVO;
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public void logout() {
    }
}
