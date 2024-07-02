package com.example.vcc_task5.exception;

import com.example.vcc_task5.payload.CommonResponse;
import com.example.vcc_task5.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonResponse<Object>> handleNotFoundException(CommonException ex){
        return ResponseUtil.wrapResponse(ex.getData(), ex.getMessage(), ex.isStatus(), ex.getCode());
    }
}
