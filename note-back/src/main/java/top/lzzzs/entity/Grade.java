package top.lzzzs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Grade {
    @JsonIgnore
    private String userId;

    private int grade;

    private int experience;
}
