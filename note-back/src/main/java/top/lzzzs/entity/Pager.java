package top.lzzzs.entity;

import lombok.Data;

import java.util.List;

@Data
public class Pager<T> {
    // 分页起码页
    private int page;
    // 每页记录数
    private int size;
    // 返回的集合
    private List<T> rows;
    // 总条数
    private long total;
}
