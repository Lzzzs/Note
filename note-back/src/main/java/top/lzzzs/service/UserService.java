package top.lzzzs.service;

import top.lzzzs.common.R;
import top.lzzzs.common.dto.LoginDto;

public interface UserService {
    R createUser(LoginDto registerInfo);

    R excuteLogin(LoginDto loginInfo);
}
