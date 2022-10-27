package top.lzzzs.common.dto;

import lombok.Data;

@Data
public class RefreshTokenDto {
    LoginDto loginInfo;
    String refreshToken;
}
