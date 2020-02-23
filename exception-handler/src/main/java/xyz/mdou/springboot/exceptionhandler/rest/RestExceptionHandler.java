package xyz.mdou.springboot.exceptionhandler.rest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RestException.class)
    public RestResponse<Object> restException(RestException exception) {
        return RestResponse.failed(exception.getCode(), exception.getMessage());
    }
}
