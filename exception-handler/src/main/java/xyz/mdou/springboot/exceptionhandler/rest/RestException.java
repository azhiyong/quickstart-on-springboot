package xyz.mdou.springboot.exceptionhandler.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RestException extends RuntimeException {
    private String code;
    private String message;
}
