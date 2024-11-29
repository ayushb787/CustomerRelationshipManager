package org.demo.crm.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private int statusCode;

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data, 200);
    }

    public static <T> ApiResponse<T> error(String message, int statusCode) {
        return new ApiResponse<>(false, message, null, statusCode);
    }
}
