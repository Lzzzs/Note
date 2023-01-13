package top.lzzzs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lzzzs.common.R;
import top.lzzzs.entity.Grade;
import top.lzzzs.entity.Pager;
import top.lzzzs.mapper.NoteMapper;
import top.lzzzs.mapper.SelfMapper;
import top.lzzzs.service.SelfService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SelfServiceImpl implements SelfService {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    SelfMapper selfMapper;

    @Override
    public R getGradeInfo(String userId) {
        String id = noteMapper.getId(userId);

        return R.success(selfMapper.getGradeInfo(id));
    }

    @Override
    public R getPointInfo(String userId) {
        String id = noteMapper.getId(userId);

        return R.success(selfMapper.getPointInfo(id));
    }


    public Grade handleGrade(Grade gradeInfo) {
        // 目前只有1,2,3 总共三个等级，等级容量100 -> 500 -> 1000
        HashMap<Integer, Integer> gradeMap = new HashMap<>();
        gradeMap.put(1, 100);
        gradeMap.put(2, 500);
        gradeMap.put(3, 1000);

        // 每次添加的经验量
        int incrValue = 20;

        Integer currentContain = gradeMap.get(gradeInfo.getGrade());
        int target = gradeInfo.getExperience() + incrValue;
        if (target > currentContain) {
            // 经验溢出，将等级提升一级
            gradeInfo.setGrade(gradeInfo.getGrade() + 1);
            gradeInfo.setExperience(target - currentContain);
        } else {
            gradeInfo.setExperience(target);
        }

        return gradeInfo;
    }

    @Override
    public int addExperience(String userId) {
        Grade gradeInfo = selfMapper.getGradeInfo(userId);
        // 处理等级经验逻辑
        Grade grade = this.handleGrade(gradeInfo);

        return selfMapper.updateGradeInfo(grade);
    }

    @Override
    public int addPoint(String userId) {
        int incrValue = 20;

        return selfMapper.addPoint(userId, incrValue);
    }

    @Override
    public R exchangeAward(String userId, int awardPoint) {
        String id = noteMapper.getId(userId);
        return R.success(selfMapper.exchangeAward(id, awardPoint));
    }

    @Override
    public R getUserAllNotesByUserId(int page, int size, String userId) {
        String id = noteMapper.getId(userId);
        int temp = (page - 1) * size;

        Pager<Map<String, Object>> pager = new Pager<>();
        pager.setPage(page);
        pager.setSize(size);

        List<Map<String, Object>> userAllNotes = selfMapper.getUserAllNotesByUserId(temp, size, id);
        int total = selfMapper.getUserAllNotesCount(id);
        pager.setTotal(total);
        pager.setRows(userAllNotes);

        return R.success(pager);
    }

    @Override
    public R getUserAllCollectNotesByUserId(int page, int size, String userId) {
        String id = noteMapper.getId(userId);
        int temp = (page - 1) * size;

        Pager<Map<String, Object>> pager = new Pager<>();
        pager.setPage(page);
        pager.setSize(size);

        List<Map<String, Object>> userAllCollectNotes = selfMapper.getUserAllCollectNotesByUserId(temp, size, id);
        int total = selfMapper.getUserAllCollectNotesCount(id);
        pager.setTotal(total);
        pager.setRows(userAllCollectNotes);

        return R.success(pager);
    }


    @Override
    public R deleteNotes(Map<String, Object> deleteNoteInfo) {
        System.out.println(deleteNoteInfo);
        List<Number> notes = (List<Number>) deleteNoteInfo.get("notes");

        for (Number noteId : notes) {
            selfMapper.deleteNoteByNoteId((Integer) noteId);
        }

        return R.success(null);
    }
}
