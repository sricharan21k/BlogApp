package com.app.blogApp.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    String message;
}
