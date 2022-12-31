package top.lzzzs.common;

import java.io.Serializable;
public class R implements Serializable {
    private int code;
    private String message;
    private Object data;

    public static R set(int code, String message) {
        R r = new R();

        r.setCode(code);
        r.setMessage(message);
        r.setData(null);

        return r;
    }

    public static R set(int code, String message, Object data) {
        R r = new R();

        r.setCode(code);
        r.setMessage(message);
        r.setData(data);

        return r;
    }


    public static R success(String message, Object data) {
        return R.set(200, message, data);
    }

    public static R success(Object data) {
        return R.set(200, "success", data);
    }

    public static R error() {
        return R.error("error");
    }
    public static R error(String message) {
        return R.set(500, message, null);
    }


    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
