package com.example.vcc_task5.exception;

public class CommonException extends RuntimeException {
    private boolean status;

    private Object data;

    private String message;

    private int code;

    public CommonException() {
    }

    public CommonException(boolean status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public CommonException(boolean status, Object data, String message, int code) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
