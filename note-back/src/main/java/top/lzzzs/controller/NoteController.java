package top.lzzzs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lzzzs.common.R;
import top.lzzzs.common.annotation.JwtIgnore;
import top.lzzzs.common.dto.CommentDianzanDto;
import top.lzzzs.common.dto.DianzanDto;
import top.lzzzs.entity.Comments;
import top.lzzzs.service.NoteService;

import java.util.Map;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    NoteService noteService;

    /**
     * 保存笔记中的图片，支持多文件上传
     *
     * @param files
     * @return
     */
    @PostMapping("/saveNoteContentImage")
    public R saveNoteContentImage(@RequestParam("files") MultipartFile[] files) {
        return noteService.saveNoteContentImage(files);
    }

    /**
     * 保存笔记数据
     *
     * @param file
     * @param params
     * @return
     */
    @PostMapping("/saveNoteData")
    public R saveNoteData(@RequestParam("noteImg") MultipartFile file, @RequestParam Map<String, Object> params) {
        return noteService.saveNote(file, params);
    }


    @JwtIgnore
    @GetMapping("/getNotes")
    public R getNotes(@RequestParam("page") int page,
                      @RequestParam("size") int size,
                      String tag) {
        return noteService.getNotes(page, size, tag);
    }

    @JwtIgnore
    @GetMapping("/getNoteByNoteId")
    public R getNoteByNoteId(@RequestParam("id") int id) {
        return noteService.getNoteByNoteId(id);
    }

    /**
     * 模块查询笔记
     */
    @JwtIgnore
    @GetMapping("/searchBylike")
    public R searchBylike(@RequestParam("page") int page,
                          @RequestParam("size") int size,
                          @RequestParam("searchValue") String searchValue) {
        return noteService.searchBylike(page, size, searchValue);
    }

    /**
     * 获取用户与笔记之间的点赞以及收藏关系
     *
     * @param userId
     * @param noteId
     * @return
     */
    @JwtIgnore
    @GetMapping("/getUserNoteAllRelation")
    public R getUserNoteAllRelation(@RequestParam("userId") String userId,
                                    @RequestParam("noteId") int noteId) {
        return noteService.getUserNoteAllRelation(userId, noteId);
    }

    /**
     * 插入一条点赞数据
     *
     * @param dianzanDto
     * @return R
     */
    @PostMapping("/insertDianzanOne")
    public R insertDianzanOne(@RequestBody DianzanDto dianzanDto) {
        return noteService.insertDianzanOne(dianzanDto);
    }

    /**
     * 插入一条收藏数据
     *
     * @param dianzanDto
     * @return R
     */
    @PostMapping("/insertCollectOne")
    public R insertCollectOne(@RequestBody DianzanDto collectDto) {
        return noteService.insertCollectOne(collectDto);
    }

    /**
     * 删除一条点赞数据
     *
     * @param dianzanDto
     * @return R
     */
    @PostMapping("/deleteDianzanOne")
    public R deleteDianzanOne(@RequestBody DianzanDto dianzanDto) {
        return noteService.deleteDianzanOne(dianzanDto);
    }

    /**
     * 删除一条收藏数据
     *
     * @param dianzanDto
     * @return R
     */
    @PostMapping("/deleteCollectOne")
    public R deleteCollectOne(@RequestBody DianzanDto collectDto) {
        return noteService.deleteCollectOne(collectDto);
    }

    /**
     * 根据笔记id获取评论数据
     *
     * @param noteId
     * @return R
     */
    @JwtIgnore
    @GetMapping("/getCommentByNoteId")
    public R getCommentByNoteId(@RequestParam("noteId") int noteId) {
        return noteService.getCommentByNoteId(noteId);
    }

    @JwtIgnore
    @GetMapping("/getCommentLikeRelation")
    public R getCommentLikeRelation(@RequestParam("userId") String userId, @RequestParam("noteId") int noteId) {
        return noteService.getCommentLikeRelation(userId, noteId);
    }

    @PostMapping("saveComment")
    public R saveComment(@RequestBody Comments commentData) {
        return noteService.saveComment(commentData);
    }

    @PostMapping("/addDianzanComment")
    public R addDianzanComment(@RequestBody CommentDianzanDto commentDianzanDto) {
        return noteService.addDianzanComment(commentDianzanDto);
    }

    @PostMapping("/reduceDianzanComment")
    public R reduceDianzanComment(@RequestBody CommentDianzanDto commentDianzanDto) {
        return noteService.reduceDianzanComment(commentDianzanDto);
    }

    @PostMapping("/deleteCommentById")
    public R deleteCommentById(@RequestBody Map<String, String> commentInfo) {
        return noteService.deleteCommentById(commentInfo);
    }
}
