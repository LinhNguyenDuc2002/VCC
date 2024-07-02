package com.example.vcc_task5.util;

import com.example.vcc_task5.payload.CommonResponse;
import org.springframework.http.ResponseEntity;

public final class ResponseUtil {
    public static <T> ResponseEntity<CommonResponse<T>> wrapResponse(T data, String message, boolean status, int code) {
        CommonResponse<T> response = new CommonResponse<>();

        if (data != null) {
            response.setData(data);
        }

        response.setCode(code);
        response.setStatus(status);
        response.setMessage(message);
        return ResponseEntity.ok(response);
    }
}
