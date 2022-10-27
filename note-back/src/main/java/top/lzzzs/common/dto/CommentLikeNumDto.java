package top.lzzzs.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommentLikeNumDto {
    private int id;

    // 0 代表无联系， 1 代表已点赞
    private int isLiked;

    private int likeNum;

    private List<CommentLikeNumDto> childrens;
}
