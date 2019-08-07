package com.example.wanandroid2.base;

public class BaseResponse<T> {
    private T data;
    private int errorCode;
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BaseResponse(T data, int errorCode, String errorMsg) {

        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
