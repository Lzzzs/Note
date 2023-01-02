package top.lzzzs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/selectAllOrganize")
    public R selectAllOrganize() {
        return groupService.selectAllOrganize();
    }

    @GetMapping("/selectOrganizeByUserId")
    public R selectOrganizeByUserId(@RequestParam("userId") String userId) {
        return groupService.selectOrganizeByUserId(userId);
    }

    // 添加organize_user 关系
    @PostMapping("/addOrganizeUser")
    public R addOrganizeUser(@RequestBody Map<String, Object> organizeInfo) {
        /**
         * {
         *     id:
         *     userId:
         * }
         */
        return groupService.addOrganizeUser(organizeInfo);
    }

    @GetMapping("/selectOrganizeById")
    public R selectOrganizeById(@RequestParam("id") int id) {
        return groupService.selectOrganizeById(id);
    }

    @GetMapping("/selectOrganizeUserById")
    public R selectOrganizeUserById(@RequestParam("id") int id) {
        return groupService.selectOrganizeUserById(id);
    }

    // 分页查询
    @GetMapping("/getOrganizeNotes")
    public R getOrganizeNotes(@RequestParam("id") int id, @RequestParam("page") int page, @RequestParam("size") int size, String tag) {
        return groupService.getOrganizeNotes(id, page, size, tag);
    }
}
