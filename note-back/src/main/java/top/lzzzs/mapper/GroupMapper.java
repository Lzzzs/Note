package top.lzzzs.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.lzzzs.entity.Organize;

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

}
