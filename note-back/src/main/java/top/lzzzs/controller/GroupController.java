package top.lzzzs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lzzzs.common.R;
import top.lzzzs.service.GroupService;

import java.util.Map;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    /**
     * 创建group
     *
     * @return R
     */
    @PostMapping("/addGroup")
    public R addGroup(@RequestBody Map<String, String> groupInfo) {
        return groupService.addGroup(groupInfo);
    }
}
