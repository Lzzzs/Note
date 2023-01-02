package top.lzzzs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lzzzs.common.R;
import top.lzzzs.entity.Notes;
import top.lzzzs.entity.Organize;
import top.lzzzs.entity.Pager;
import top.lzzzs.mapper.GroupMapper;
import top.lzzzs.mapper.NoteMapper;
import top.lzzzs.service.GroupService;
import top.lzzzs.utils.DateUtil;

import java.util.HashMap;
import java.util.List;
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
        // 添加group
        groupMapper.addGroup(groupInfo);
        System.out.println(groupInfo);

        Map<String, Object> map = new HashMap<String, Object>();
        // 这里获取的id是上一次新增的id
        map.put("id", groupInfo.get("id"));
        map.put("userId", id);

        // 向organize_user 添加一条数据
        groupMapper.addOrganizeToUser(map);

        return R.success(null);
    }


    @Override
    public R selectAllOrganize() {
        List<Organize> organizes = groupMapper.selectAllOrganize();
        return R.success(organizes);
    }

    @Override
    public R selectOrganizeByUserId(String userId) {
        String id = noteMapper.getId(userId);
        List<Organize> organizes = groupMapper.selectOrganizeByUserId(id);
        return R.success(organizes);
    }

    @Override
    public R addOrganizeUser(Map<String, Object> organizeInfo) {
        String id = noteMapper.getId((String) organizeInfo.get("userId"));
        organizeInfo.put("userId", id);
        groupMapper.addOrganizeToUser(organizeInfo);

        return R.success(null);
    }

    @Override
    public R selectOrganizeById(int id) {
        return R.success(groupMapper.selectOrganizeById(id));
    }

    @Override
    public R selectOrganizeUserById(int id) {
        return R.success(groupMapper.selectOrganizeUserById(id));
    }

    // 分页查询organize数据

    /**
     * {
     * id,
     * page,
     * size,
     * ?tag
     * }
     */
    @Override
    public R getOrganizeNotes(int id, int page, int size, String tag) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", (page - 1) * size);
        map.put("size", size);
        map.put("tag", tag);
        map.put("id", id);

        Pager<Notes> pager = new Pager<>();
        List<Notes> organize = groupMapper.selectOrganizeByPager(map);

        Map<String, Object> countMap = new HashMap<>();
        countMap.put("id", id);
        countMap.put("tag", tag);
        long count = groupMapper.getOrganizeCount(countMap);

        pager.setRows(organize);
        pager.setTotal(count);
        pager.setPage(page);
        pager.setSize(size);

        return R.success(pager);
    }

}
