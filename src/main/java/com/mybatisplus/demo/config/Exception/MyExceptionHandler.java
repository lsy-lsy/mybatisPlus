package com.mybatisplus.demo.config.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("code","user.notexsit");
        map.put("message",e.getMessage());
//        转发到error
        return "forward:/error";
//        return map;

    }

}
