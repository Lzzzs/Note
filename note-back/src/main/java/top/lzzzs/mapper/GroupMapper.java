package top.lzzzs.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.lzzzs.entity.Notes;
import top.lzzzs.entity.Organize;
import top.lzzzs.entity.Users;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupMapper {
    // 添加一个Organize
    int addGroup(Map<String, String> groupInfo);

    int addOrganizeToUser(Map<String, Object> info);

    // 查询所有group
    List<Organize> selectAllOrganize();

    // 根据userid查询用户已经加入过的group
    List<Organize> selectOrganizeByUserId(String userId);

    Organize selectOrganizeById(int id);

    // 查询organize有哪些用户
    List<Users> selectOrganizeUserById(int id);

    // 分页查询笔记
    List<Notes> selectOrganizeByPager(Map<String, Object> params);

    // 笔记总数量
    long getOrganizeCount(Map<String, Object> params);

    // 添加organize和note的关系
    int addOrganizeToNote(Map<String, Object> info);
}
