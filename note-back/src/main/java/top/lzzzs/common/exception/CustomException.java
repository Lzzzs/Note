package top.lzzzs.common.exception;

import top.lzzzs.common.Rcode;

import java.text.MessageFormat;

/**
 * 自定义异常类型
 **/
public class CustomException extends RuntimeException {

    Rcode Rcode;

    public CustomException(Rcode resultCode) {
        super(resultCode.message());
        this.Rcode = resultCode;
    }

    public CustomException(Rcode resultCode, Object... args) {
        super(resultCode.message());
        String message = MessageFormat.format(resultCode.message(), args);
        resultCode.setMessage(message);
        this.Rcode = resultCode;
    }

    public Rcode getRcode() {
        return Rcode;
    }
}
