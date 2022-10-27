package top.lzzzs.entity;

import lombok.Data;

@Data
public class Notes {
    private String id;

    private String title;

    private String content;

    private String tag;

    private String noteImg;

    private String createdTime;

    private Users user;

    private int likeNum;

    private int collectNum;
}
