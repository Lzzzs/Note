package top.lzzzs.entity;

import lombok.Data;

import java.util.List;

@Data
public class Comments {
    private int id;

    private String userId;

    private String commentContent;

    private String createdTime;

    private int parentId;

    private int replyId;

    private int noteId;


    private String avatarUrl;

    private int level;

    private int grade;

    private String replyUserName;

    private List<Comments> childrens;
}
