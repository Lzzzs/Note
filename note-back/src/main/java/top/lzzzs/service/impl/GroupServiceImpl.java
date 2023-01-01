package top.lzzzs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lzzzs.common.R;
import top.lzzzs.mapper.GroupMapper;
import top.lzzzs.mapper.NoteMapper;
import top.lzzzs.service.GroupService;
import top.lzzzs.utils.DateUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    GroupMapper groupMapper;

    @Override
    public R addGroup(Map<String, String> groupInfo) {
        String id = noteMapper.getId(groupInfo.get("userId"));
        groupInfo.put("userId", id);
        groupInfo.put("createdTime", DateUtil.createdTime());

        System.out.println(groupInfo);
        groupMapper.addGroup(groupInfo);

        return R.success(null);
    }
}
