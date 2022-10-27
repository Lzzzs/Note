package top.lzzzs.interceptor;


import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.lzzzs.common.Rcode;
import top.lzzzs.common.annotation.JwtIgnore;
import top.lzzzs.common.exception.CustomException;
import top.lzzzs.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
            if (jwtIgnore != null) {
                return true;
            }
        }

        // 获取请求头信息authorization信息
        final String authHeader = request.getHeader(JwtUtil.AUTH_HEADER_KEY);
        log.info("## authHeader= {}", authHeader);
        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
            log.info("### 用户未登录，请先登录 ###");
            throw new CustomException(Rcode.USER_NOT_LOGGED_IN);
        }
        // 获取token
        final String token = authHeader.substring(7);

        // 验证token是否过期，手动抛出异常
        try {
            JwtUtil.isExpiration(token);
        } catch (ExpiredJwtException e) {
            throw new CustomException(Rcode.PERMISSION_TOKEN_EXPIRED);
        }

        return true;
    }
}
