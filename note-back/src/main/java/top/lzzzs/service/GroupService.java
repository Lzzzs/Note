package top.lzzzs.service;

import top.lzzzs.common.R;

import java.util.Map;

public interface GroupService {

    R addGroup(Map<String, String> groupInfo);

    R selectAllOrganize();

    R selectOrganizeByUserId(String userId);

    R addOrganizeUser(Map<String, Object> organizeInfo);

    R selectOrganizeById(int id);

    R selectOrganizeUserById(int id);

    R getOrganizeNotes(int id, int page, int size, String tag);
}
