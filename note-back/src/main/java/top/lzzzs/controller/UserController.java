package top.lzzzs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.lzzzs.common.R;
import top.lzzzs.common.annotation.JwtIgnore;
import top.lzzzs.common.dto.LoginDto;
import top.lzzzs.common.dto.RefreshTokenDto;
import top.lzzzs.service.UserService;
import top.lzzzs.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @JwtIgnore
    @PostMapping("/login")
    public R login(@RequestBody LoginDto loginInfo) {
        return userService.excuteLogin(loginInfo);
    }

    /**
     * 注册接口
     *
     * @param registerInfo
     * @return R
     */
    @JwtIgnore
    @PostMapping("/register")
    public R register(@RequestBody LoginDto registerInfo) {
        return userService.createUser(registerInfo);
    }

    /**
     * 刷新token
     *
     * @return R
     */
    @PostMapping("/refresh-token")
    @JwtIgnore
    public R refreshToken(@RequestBody RefreshTokenDto userInfo) {
        // 检查refreshToken是否过期，过期的话会到全局异常进行处理
        JwtUtil.isExpiration(userInfo.getRefreshToken());

        Map<Object, Object> res = new HashMap<>();

        // create token and refreshToken
        String token = JwtUtil.createToken(userInfo.getLoginInfo(), false);
        String refreshToken = JwtUtil.createToken(userInfo.getLoginInfo(), true);

        res.put("token", token);
        res.put("refreshToken", refreshToken);

        return R.success(res);
    }

}
