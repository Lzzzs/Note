package top.lzzzs.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.lzzzs.common.dto.LoginDto;
import top.lzzzs.entity.Users;

@Mapper
public interface UserMapper {
    int createUser(Users user);

    Users selectUserByUserId(String userid);

    Users selectUser(LoginDto loginInfo);

    // 初始化等级表中的用户
    int initGrade(String userId);

    int initPoint(String userId);

}
