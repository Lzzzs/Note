package top.lzzzs.service;

import top.lzzzs.common.R;

import java.util.Map;

public interface SelfService {
    R getGradeInfo(String userId);

    R getPointInfo(String userId);

    int addExperience(String userId);

    int addPoint(String userId);

    R exchangeAward(String userId, int awardPoint);

    R getUserAllNotesByUserId(int page, int size, String userId);

    R getUserAllCollectNotesByUserId(int page, int size, String userId);

    R deleteNotes(Map<String, Object> deleteNoteInfo);
}
