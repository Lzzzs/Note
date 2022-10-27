package top.lzzzs.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.lzzzs.common.R;
import top.lzzzs.common.Rcode;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕获
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public R handleException(CustomException e) {
        // 打印异常信息
        log.error("### 异常信息:{} ###", e.getMessage());
        return R.set(e.getRcode().code(), e.getRcode().message(), null);
    }


    /**
     * token过期
     *
     * @param e
     * @return R
     */
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    R handleExpiredJwtException(ExpiredJwtException e) {
        return R.set(HttpStatus.UNAUTHORIZED.value(), "token过期", e.getMessage());
    }


    /**
     * 处理url请求参数错误
     *
     * @param e
     * @return R
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    R handleConstraintViolationException(ConstraintViolationException e) {
        log.error("=====================参数校验错误=====================", e);
        return R.set(HttpStatus.BAD_REQUEST.value(), "参数校验错误", e.getMessage());
    }

    /**
     * 处理请求体参数校验错误
     *
     * @param e
     * @return R
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("=====================参数校验错误=====================", e);

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return R.set(HttpStatus.BAD_REQUEST.value(), "参数校验错误", errors);
    }


    /**
     * 处理空指针异常
     *
     * @param e
     * @return R
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handlerNPE(NullPointerException e) {
        log.error("=====================空指针异常=====================", e);
        return R.set(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
    }

    /**
     * 处理Exception
     *
     * @param e
     * @return R
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handlerException(Exception e) {
        log.error("=====================系统异常=====================", e);
        return R.set(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常", null);
    }
}
