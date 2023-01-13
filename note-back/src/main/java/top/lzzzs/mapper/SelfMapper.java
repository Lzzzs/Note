package top.lzzzs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lzzzs.entity.Grade;

import java.util.List;
import java.util.Map;

@Mapper
public interface SelfMapper {
    Grade getGradeInfo(String id);

    int getPointInfo(String id);

    int updateGradeInfo(Grade gradeInfo);

    int addPoint(@Param("userId") String userId, @Param("incrValue") int incrValue);

    int exchangeAward(@Param("userId") String userId, @Param("awardPoint") int awardPoint);

    List<Map<String, Object>> getUserAllNotesByUserId(@Param("page") int page,
                                                      @Param("size") int size,
                                                      @Param("id") String id);

    int getUserAllNotesCount(String id);

    List<Map<String, Object>> getUserAllCollectNotesByUserId(@Param("page") int page,
                                                             @Param("size") int size,
                                                             @Param("id") String id);

    int getUserAllCollectNotesCount(String id);

    int deleteNoteByNoteId(int noteId);
}
