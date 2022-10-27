package top.lzzzs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.lzzzs.common.R;
import top.lzzzs.common.Rcode;
import top.lzzzs.common.dto.LoginDto;
import top.lzzzs.common.exception.CustomException;
import top.lzzzs.entity.Users;
import top.lzzzs.mapper.UserMapper;
import top.lzzzs.service.UserService;
import top.lzzzs.utils.DateUtil;
import top.lzzzs.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Value("${resources-url.base-url}")
    private String baseUrl;

    @Autowired
    UserMapper userMapper;

    @Override
    public R createUser(LoginDto registerInfo) {
        Users user = userMapper.selectUserByUserId(registerInfo.getUserId());
        if (user == null) {
            Users userInfo = new Users();

            String id = UUID.randomUUID().toString().replaceAll("-", "");
            userInfo.setId(id);
            userInfo.setUserid(registerInfo.getUserId());
            userInfo.setPassword(DigestUtils.md5DigestAsHex(registerInfo.getPassword().getBytes())); // md5加密

            String[] avatarArr = {"avatarOne.png", "avatarTwo.png"};
            // http://120.79.94.54:8888/static/
            // http://localhost:8888/static/
            userInfo.setAvatarUrl(baseUrl + avatarArr[(int) (Math.random() * avatarArr.length)]);

            userInfo.setCreatedTime(DateUtil.createdTime());

            if (userMapper.createUser(userInfo) == 1) {
                // 初始化用户等级
                userMapper.initGrade(id);

                // 初始化point
                userMapper.initPoint(id);

                return R.success(null);
            } else {
                return R.error();
            }
        } else {
            return R.error(registerInfo.getUserId() + "用户已存在");
        }
    }

    @Override
    public R excuteLogin(LoginDto loginInfo) {
        // 密码加密，然后查看用户是否存在
        loginInfo.setPassword(DigestUtils.md5DigestAsHex(loginInfo.getPassword().getBytes()));
        Users user = userMapper.selectUser(loginInfo);

        if (user != null) {
            Map<String, Object> result = new HashMap<>();
            String token = JwtUtil.createToken(loginInfo, false);
            String refreshToken = JwtUtil.createToken(loginInfo, true);

            result.put("user", user);
            result.put("token", token);
            result.put("refreshToken", refreshToken);

            return R.success(result);
        } else {
            throw new CustomException(Rcode.USER_LOGIN_ERROR);
        }
    }
}
