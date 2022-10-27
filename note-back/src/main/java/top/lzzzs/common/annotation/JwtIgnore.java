package top.lzzzs.common.annotation;

import java.lang.annotation.*;

/**
 * JWT验证忽略注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JwtIgnore {
}