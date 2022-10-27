package top.lzzzs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Users {
    @JsonIgnore
    private String id;

    private String userid;

    @JsonIgnore
    private String password;

    private String createdTime;

    private String avatarUrl;

}
