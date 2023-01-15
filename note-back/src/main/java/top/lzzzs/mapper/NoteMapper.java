package top.lzzzs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.lzzzs.common.dto.CommentLikeNumDto;
import top.lzzzs.common.dto.DianzanDto;
import top.lzzzs.entity.Comments;
import top.lzzzs.entity.Notes;
import top.lzzzs.entity.Users;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoteMapper {
    int saveNote(Map<String, Object> noteInfo);

    // 保存笔记与用户之间的关联关系
    int saveUserNoteRelation(@Param("userId") String userId, @Param("noteId") Number noteId);

    // 分页查询笔记
    List<Notes> selectNoteByPager(Map<String, Object> params);

    // 笔记总数量
    long getNoteCount(String tag);

    Notes getNoteByNoteId(int id);

    // 分页模糊查询
    List<Notes> selectNoteBylike(@Param("page") int page,
                                 @Param("size") int size,
                                 @Param("field") String field,
                                 @Param("searchValue") String searchValue);

    // 分页模糊查询根据userid
    List<Notes> selectNoteBylikeUserId(@Param("page") int page,
                                       @Param("size") int size,
                                       @Param("searchValue") String searchValue);

    // 获取根据title模糊查询的总条数
    long getNoteBylikeTitleCount(String searchValue);

    // 获取根据userid模糊查询的总条数
    long getNoteBylikeUserIdCount(String searchValue);

    // 获取用户表中的id，通过userId查询
    String getId(String userId);

    // 查询用户与笔记之间的点赞联系 返回1代表有联系，0代表与笔记无联系
    int selectUserNoteLikeRelation(@Param("id") String id, @Param("noteId") int noteId);

    // 查询用户与笔记之间的收藏联系 返回1代表有联系，0代表与笔记无联系
    int selectUserNoteCollectRelation(@Param("id") String id, @Param("noteId") int noteId);

    /**
     * like_num 和 collect_num 表数据添加和删除
     */
    int insertDianzanOne(DianzanDto dianzanDto);

    int insertCollectOne(DianzanDto collectDto);

    int deleteDianzanOne(DianzanDto dianzanDto);

    int deleteCollectOne(DianzanDto collectDto);

    /**
     * likenum 和 collectnum的添加和减少
     */
    int addNoteLikeNum(int noteId);

    int reduceNoteLikeNum(int noteId);

    int addNoteCollectNum(int noteId);

    int reduceNoteCollectNum(int noteId);

    // 根据笔记id获取该笔记的发布者id
    String getUserIdByNoteId(int noteId);

    // 获取该noteId下所有一级评论 默认根据点赞数排序 其次根据发布时间
    List<Comments> getFirstCommentByNoteId(int noteId);

    Comments getCommentByCommentId(int commentId);

    Map<String, Object> getUserInfoById(String id);

    // 获取父评论下的所有子评论 默认根据点赞数排序 其次根据发布时间
    List<Comments> getChildrenCommentsByFatherCommentId(int fatherCommentId);

    List<CommentLikeNumDto> getFirstCommentLikeByNoteId(int noteId);

    int getCommentLikeRelation(@Param("id") String id, @Param("commentId") int commentId);

    List<CommentLikeNumDto> getChildrenCommentLikeRelationByFatherCommentId(int fatherCommentId);

    int addCommentLikeRelation(@Param("id") String id, @Param("commentId") int commentId);

    int addCommentLikeNum(int commentId);

    int reduceCommentLikeRelation(@Param("id") String id, @Param("commentId") int commentId);

    int reduceCommentLikeNum(int commentId);

    int saveComment(Comments commentData);

    int initCommentLikeNum(int id);

    int deleteCommentById(int id);

    int deleteCommentLikeNumById(int id);

    int deleteCommentLikeRelationById(int id);

    List<Comments> getChildrenCommentByParentId(int id);
}
