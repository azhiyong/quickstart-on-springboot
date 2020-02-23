package xyz.mdou.springboot.exceptionhandler.rest;

import lombok.Data;

@Data
public class RestResponse<T> {
    private String code;
    private String message;
    private T data;

    public static <T> RestResponse<T> success(T data) {
        RestResponse<T> response = new RestResponse<>();
        response.code = "OK";
        response.data = data;
        return response;
    }

    public static <T> RestResponse<T> failed(String code, String message) {
        RestResponse<T> response = new RestResponse<>();
        response.code = code;
        response.message = message;
        return response;
    }
}
