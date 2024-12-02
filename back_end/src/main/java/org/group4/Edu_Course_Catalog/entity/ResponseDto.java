package org.group4.Edu_Course_Catalog.entity;


public class ResponseDto {
    private Integer code;
    private String message;
    private Object data;

    // 成功状态码
    public static final int SUCCESS = 200;
    // 失败状态码
    public static final int ERROR = 500;

    // 私有构造函数
    public ResponseDto(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseDto(String message) {
        this.message = message;
    }

    // 成功静态方法
    public static ResponseDto success() {
        return new ResponseDto(SUCCESS, "操作成功", null);
    }

    public static ResponseDto success(Object data) {
        return new ResponseDto(SUCCESS, "操作成功", data);
    }

    public static ResponseDto success(String message, Object data) {
        return new ResponseDto(SUCCESS, message, data);
    }

    // 错误静态方法
    public static ResponseDto error(String message) {
        return new ResponseDto(ERROR, message, null);
    }

    public static ResponseDto error(Integer code, String message) {
        return new ResponseDto(code, message, null);
    }

    // getter和setter方法
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
