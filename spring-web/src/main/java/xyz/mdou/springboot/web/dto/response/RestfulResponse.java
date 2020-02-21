package xyz.mdou.springboot.web.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RestfulResponse<T> {

    private String status;

    private T data;

    private String message;

    public static <T> RestfulResponse<T> ok(T data) {
        return new RestfulResponse<T>().setStatus(RestfulStatus.OK).setData(data);
    }

    public static <T> RestfulResponse<T> fail(String message) {
        return new RestfulResponse<T>().setStatus(RestfulStatus.FAIL).setMessage(message);
    }

    static class RestfulStatus {
        static final String OK = "ok";
        static final String FAIL = "fail";
    }
}
