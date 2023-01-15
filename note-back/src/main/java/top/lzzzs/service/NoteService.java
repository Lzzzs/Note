package top.lzzzs.service;

import org.springframework.web.multipart.MultipartFile;
import top.lzzzs.common.R;
import top.lzzzs.common.dto.CommentDianzanDto;
import top.lzzzs.common.dto.DianzanDto;
import top.lzzzs.entity.Comments;

import java.util.Map;

public interface NoteService {

    R saveNoteContentImage(MultipartFile[] files);

    R saveNote(MultipartFile file, Map<String, Object> params);

    R getNotes(int page, int size, String tag);

    R getNoteByNoteId(int id);

    R searchBylike(int page, int size, String searchValue);

    R getUserNoteAllRelation(String userId, int noteId);

    R insertDianzanOne(DianzanDto dianzanDto);

    R insertCollectOne(DianzanDto collectDto);

    R deleteDianzanOne(DianzanDto dianzanDto);

    R deleteCollectOne(DianzanDto collectDto);

    R getCommentByNoteId(int noteId);

    R getCommentLikeRelation(String userId, int noteId);

    R addDianzanComment(CommentDianzanDto CommentDianzanDto);

    R reduceDianzanComment(CommentDianzanDto commentDianzanDto);

    R saveComment(Comments commentData);

    R deleteCommentById(Map<String, String> commentInfo);
}
