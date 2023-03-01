package com.ivan.order.response;

import java.time.LocalDateTime;

public record ApiResponse(Integer status, String message) {

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }
}
