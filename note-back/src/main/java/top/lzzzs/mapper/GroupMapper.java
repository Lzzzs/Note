package top.lzzzs.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface GroupMapper {

    int addGroup(Map<String, String> groupInfo);


}
