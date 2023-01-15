package top.lzzzs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.lzzzs.common.R;
import top.lzzzs.common.Rcode;
import top.lzzzs.common.dto.CommentDianzanDto;
import top.lzzzs.common.dto.CommentLikeNumDto;
import top.lzzzs.common.dto.DianzanDto;
import top.lzzzs.common.exception.CustomException;
import top.lzzzs.entity.Comments;
import top.lzzzs.entity.Notes;
import top.lzzzs.entity.Pager;
import top.lzzzs.entity.Users;
import top.lzzzs.mapper.GroupMapper;
import top.lzzzs.mapper.NoteMapper;
import top.lzzzs.mapper.SelfMapper;
import top.lzzzs.mapper.UserMapper;
import top.lzzzs.service.NoteService;
import top.lzzzs.service.SelfService;
import top.lzzzs.utils.DateUtil;
import top.lzzzs.utils.TagUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoteServiceImpl implements NoteService {
    @Value("${resources-url.base-url}")
    private String baseurl;

    @Value("${resources-url.image-out-url}")
    private String imgOutUrl;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    SelfService selfService;

    /**
     * 保存笔记中的图片，支持多文件上传
     *
     * @param files
     * @return
     */
    @Override
    public R saveNoteContentImage(MultipartFile[] files) {
        Map<String, String> urlMap = this.multifileUpload(files);
        return R.success(urlMap);
    }

    /**
     * 保存笔记数据
     *
     * @param file
     * @param params
     * @return
     */
    @Override
    public R saveNote(MultipartFile file, Map<String, Object> params) {
        String noteImgUrl = this.upload(file, false);
        if (noteImgUrl != "") {
            params.put("noteImg", noteImgUrl);
            params.put("createdTime", DateUtil.createdTime());

            // 保存笔记 返回笔记id会被赋值到params
            noteMapper.saveNote(params);

            // 如果有groupId，说明是分组笔记
            if (params.containsKey("groupId")) {

                Map<String, Object> map = new HashMap<>();

                map.put("organize_id", Integer.parseInt((String) params.get("groupId")));
                map.put("note_id", params.get("id"));

                // 添加organize和note的关系
                groupMapper.addOrganizeToNote(map);
            }

            // 获取提交笔记的用户的数据表id
            String userId = (String) params.get("userId");
            Users user = userMapper.selectUserByUserId(userId);

            // 发布一个笔记 用户经验添加20 奖品兑换点数添加20
            selfService.addExperience(user.getId());
            selfService.addPoint(user.getId());

            // 保存用户与笔记之间的联系，返回笔记id会被赋值到params
            noteMapper.saveUserNoteRelation(user.getId(), (Number) params.get("id"));

            return R.success(null);
        } else {
            return R.error();
        }
    }

    /**
     * 分页查询笔记
     *
     * @param page
     * @param size
     * @param tag  可选
     * @return
     */
    @Override
    public R getNotes(int page, int size, String tag) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", (page - 1) * size);
        map.put("size", size);
        map.put("tag", tag);

        Pager<Notes> pager = new Pager<>();
        List<Notes> notes = noteMapper.selectNoteByPager(map);
        long noteCount = noteMapper.getNoteCount(tag);
        pager.setRows(notes);
        pager.setTotal(noteCount);
        pager.setPage(page);
        pager.setSize(size);

        return R.success(pager);
    }

    @Override
    public R getNoteByNoteId(int id) {
        if (noteMapper.getNoteByNoteId(id) == null) {
            throw new CustomException(Rcode.NOTE_DATA_NONE);
        }

        return R.success(noteMapper.getNoteByNoteId(id));
    }

    /**
     * 模糊搜索功能
     * 1. 先根据tag来进行搜索
     * 2. 根据title搜索
     * 3. 根据userId搜索
     *
     * @param page
     * @param size
     * @param searchValue
     * @return
     */
    @Override
    public R searchBylike(int page, int size, String searchValue) {
        Map<String, Object> map = new HashMap<>();
        Pager<Notes> pager = new Pager<>();


        pager.setPage(page);
        pager.setSize(size);

        // 如果搜索内容匹配tag，那么直接tag查询
        if (TagUtil.contains(searchValue)) {
            String currentTag = TagUtil.getCurrentTag(searchValue);

            return this.getNotes(page, size, searchValue);
        }

        page = (page - 1) * size;

        // 根据title搜索
        List<Notes> notes = noteMapper.selectNoteBylike(page, size, "title", searchValue);
        if (!notes.isEmpty()) {
            pager.setRows(notes);
            pager.setTotal(noteMapper.getNoteBylikeTitleCount(searchValue));
            return R.success(pager);
        }

        // 根据userId搜索
        List<Notes> list = noteMapper.selectNoteBylikeUserId(page, size, searchValue);
        if (!list.isEmpty()) {
            pager.setRows(list);
            pager.setTotal(noteMapper.getNoteBylikeUserIdCount(searchValue));
            return R.success(pager);
        }

        // 没有匹配到数据，直接返回null
        return R.success(null);
    }

    @Override
    public R getUserNoteAllRelation(String userId, int noteId) {
        Map<String, Integer> relationMap = new HashMap<>();
        String id = noteMapper.getId(userId);

        relationMap.put("isLike", this.getUserNoteDianzanRelation(id, noteId));
        relationMap.put("isCollect", this.getUserNoteCollectRelation(id, noteId));

        return R.success(relationMap);
    }

    @Override
    public R insertDianzanOne(DianzanDto dianzanDto) {
        // 换成数据表中的实际id
        String userId = dianzanDto.getUserId();
        String id = noteMapper.getId(userId);
        dianzanDto.setUserId(id);

        // 获取发布该笔记的用户id
        String noteUserId = noteMapper.getUserIdByNoteId(dianzanDto.getNoteId());
        // 笔记获得点赞 用户经验添加20 奖品兑换点数添加20
        selfService.addExperience(noteUserId);
        selfService.addPoint(noteUserId);

        // notes中的like_num加一
        noteMapper.addNoteLikeNum(dianzanDto.getNoteId());

        return R.success(noteMapper.insertDianzanOne(dianzanDto));
    }

    @Override
    public R insertCollectOne(DianzanDto collectDto) {
        // 换成数据表中的实际id
        String userId = collectDto.getUserId();
        String id = noteMapper.getId(userId);
        collectDto.setUserId(id);

        // notes中的collect_num加一
        noteMapper.addNoteCollectNum(collectDto.getNoteId());

        return R.success(noteMapper.insertCollectOne(collectDto));
    }

    @Override
    public R deleteDianzanOne(DianzanDto dianzanDto) {
        // 换成数据表中的实际id
        String userId = dianzanDto.getUserId();
        String id = noteMapper.getId(userId);
        dianzanDto.setUserId(id);

        // notes中的like_num减一
        noteMapper.reduceNoteLikeNum(dianzanDto.getNoteId());

        return R.success(noteMapper.deleteDianzanOne(dianzanDto));

    }

    @Override
    public R deleteCollectOne(DianzanDto collectDto) {
        // 换成数据表中的实际id
        String userId = collectDto.getUserId();
        String id = noteMapper.getId(userId);
        collectDto.setUserId(id);

        // notes中的like_num减一
        noteMapper.reduceNoteCollectNum(collectDto.getNoteId());

        return R.success(noteMapper.deleteCollectOne(collectDto));
    }

    @Override
    public R getCommentByNoteId(int noteId) {
        // 1. 先获取该noteId下所有一级评论
        List<Comments> firstCommentList = noteMapper.getFirstCommentByNoteId(noteId);

        for (Comments firstComment : firstCommentList) {
            // 2. 填充发布该评论的用户信息
            this.paddingUserInfo(firstComment);
            firstComment.setLevel(0); // 0 代表父评论， 1代表子评论

            // 3. 根据父评论id获取子评论
            List<Comments> childrenCommentList = noteMapper.getChildrenCommentsByFatherCommentId(firstComment.getId());
            for (Comments childrenComment : childrenCommentList) {
                // 4. 填充子评论的用户信息
                this.paddingUserInfo(childrenComment);
                childrenComment.setLevel(1);
                // 5. 修改子评论的replyUserName
                Comments replyComment = noteMapper.getCommentByCommentId(childrenComment.getReplyId());
                Map<String, Object> userInfo = noteMapper.getUserInfoById(replyComment.getUserId());
                childrenComment.setReplyUserName((String) userInfo.get("userid"));
            }
            // 6. 将对应的子评论插入到父评论中的子列表中
            firstComment.setChildrens(childrenCommentList);
        }

        return R.success(firstCommentList);
    }

    @Override
    public R getCommentLikeRelation(String userId, int noteId) {
        String id = noteMapper.getId(userId);

        // 1. 获取父级评论的likenum
        List<CommentLikeNumDto> firstCommentLikeList = noteMapper.getFirstCommentLikeByNoteId(noteId);
        for (CommentLikeNumDto firstCommentLike : firstCommentLikeList) {
            // 2. 获取父级评论与当前登录用户的点赞关系，0代表没点赞，1代表已点赞
            int relation = noteMapper.getCommentLikeRelation(id, firstCommentLike.getId());
            firstCommentLike.setIsLiked(relation);

            // 3. 获取子评论
            List<CommentLikeNumDto> childrenCommentLikeList = noteMapper.getChildrenCommentLikeRelationByFatherCommentId(firstCommentLike.getId());
            for (CommentLikeNumDto childrenLikeNum : childrenCommentLikeList) {
                // 4. 获取子级评论与当前登录用户的点赞关系，0代表没点赞，1代表已点赞
                int childrenRelation = noteMapper.getCommentLikeRelation(id, childrenLikeNum.getId());
                childrenLikeNum.setIsLiked(childrenRelation);
            }
            // 5. 将对应的子级评论 塞入父级评论中
            firstCommentLike.setChildrens(childrenCommentLikeList);
        }

        return R.success(firstCommentLikeList);
    }

    @Override
    public R addDianzanComment(CommentDianzanDto commentDianzanDto) {
        String id = noteMapper.getId(commentDianzanDto.getUserId());

        noteMapper.addCommentLikeRelation(id, commentDianzanDto.getCommentId());

        noteMapper.addCommentLikeNum(commentDianzanDto.getCommentId());

        return R.success(1);
    }

    @Override
    public R reduceDianzanComment(CommentDianzanDto commentDianzanDto) {
        String id = noteMapper.getId(commentDianzanDto.getUserId());

        noteMapper.reduceCommentLikeRelation(id, commentDianzanDto.getCommentId());

        noteMapper.reduceCommentLikeNum(commentDianzanDto.getCommentId());

        return R.success(1);
    }

    @Override
    public R saveComment(Comments commentData) {
        String id = noteMapper.getId(commentData.getUserId());
        commentData.setUserId(id);
        commentData.setCreatedTime(DateUtil.createdTime());

        // 保存之后返回的id会到commentData中
        noteMapper.saveComment(commentData);
        // 初始化comemnt_like_num表
        noteMapper.initCommentLikeNum(commentData.getId());

        return R.success(1);
    }


    public void paddingUserInfo(Comments comment) {
        Map<String, Object> userInfo = noteMapper.getUserInfoById(comment.getUserId());
        comment.setUserId((String) userInfo.get("userid"));
        comment.setAvatarUrl((String) userInfo.get("avatar_url"));
        comment.setGrade(Math.toIntExact((Long) userInfo.get("grade")));
    }

    public int getUserNoteDianzanRelation(String id, int noteId) {
        // 1 代表有联系， 0代表无联系
        return noteMapper.selectUserNoteLikeRelation(id, noteId);
    }

    public int getUserNoteCollectRelation(String id, int noteId) {
        // 1 代表有联系， 0代表无联系
        return noteMapper.selectUserNoteCollectRelation(id, noteId);
    }

    /**
     * 单文件上传方法
     *
     * @param file
     * @return
     */
    private String upload(MultipartFile file, Boolean isContentImage) {
        String url = "";
        FileOutputStream out = null;
        InputStream in = null;
        try {
            // 获取当天的时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String time = formatter.format(date);
            // 时间戳
            String format = Long.toString(System.currentTimeMillis());
            // 文件名
            String fileName = file.getOriginalFilename();
            // 后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            File f;
            if (isContentImage) {
                // 文件 相对路径输出 笔记内的图片路径多一个note-content
                // f = new File("src/main/resources/static/note-img/" + time + "/note-content/" + format + suffixName);
                f = new File(imgOutUrl + time + "/note-content/" + format + suffixName);
                System.out.println(imgOutUrl + time + "/note-content/" + format + suffixName);
            } else {
                // f = new File("src/main/resources/static/note-img/" + time + "/" + format + suffixName);
                f = new File(imgOutUrl + time + "/" + format + suffixName);
                System.out.println(imgOutUrl + time + "/" + format + suffixName);
            }


            // 检测 文件夹和文件是否存在,不存在则创建
            if (!f.getParentFile().exists()) {
                // 创建文件夹
                f.getParentFile().mkdirs();
                // 创建文件
                f.createNewFile();
            }

            // 输出流   文件必须已经存在
            out = new FileOutputStream(f);
            // 文件的输入流
            in = file.getInputStream();
            // 边读边写
            byte[] b = new byte[1024 * 1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b);
            }
            // 刷新流
            out.flush();

            // http://120.79.94.54:8888/static/
            // String baseUrl = "http://localhost:8888/static/";
            if (isContentImage) {
                url = baseurl + "note-img/" + time + "/note-content/" + format + suffixName;
            } else {
                url = baseurl + "note-img/" + time + "/" + format + suffixName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return url;

    }

    private Map<String, String> multifileUpload(MultipartFile[] files) {
        Map<String, String> urlMap = new HashMap<>();
        for (MultipartFile file : files) {
            String url = this.upload(file, true);
            String filename = file.getOriginalFilename();
            urlMap.put(filename, url);
        }
        return urlMap;
    }


    @Override
    public R deleteCommentById(Map<String, String> commentInfo) {
        int commentId = Integer.parseInt(commentInfo.get("id"));

        Comments comment = noteMapper.getCommentByCommentId(commentId);
        int level = comment.getLevel();

        if (level == 0) {
            // 当前要删除的是父级评论

            // 获取当前父评论下的所有子评论, 将评论删除
            List<Comments> childrenComments = noteMapper.getChildrenCommentByParentId(commentId);
            for (Comments childrenComment : childrenComments) {
                // 删除comments表中的评论，以及删除comment_like_num中的关系
                deleteComment(childrenComment.getId());
            }

            // 删除父评论
            deleteComment(comment.getId());
        } else {
            // 当前要删除的是子级评论
            deleteComment(commentId);
        }

        return R.success(null);
    }

    // 删除comments表中的评论，以及删除comment_like_num中的关系, 和删除comment_like_relation
    public void deleteComment(int id) {
        noteMapper.deleteCommentLikeNumById(id);
        noteMapper.deleteCommentLikeRelationById(id);
        noteMapper.deleteCommentById(id);
    }

}
