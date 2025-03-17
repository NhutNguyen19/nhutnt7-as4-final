package com.fis.bank.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1002, "Vai trò không được tìm thấy", HttpStatus.NOT_FOUND),
    INVALID_USERNAME(1003, "Tài khoản lớn hơn {min} kí tự", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Mật khẩu lớn hơn {min} kí tự", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1005, "Phân loại đã tồn tại", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Chưa xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Bạn chưa được cho phép", HttpStatus.FORBIDDEN),
    EMAIL_EXISTED(1008, "Email đã tồn tại, vui lòng chọn email khác", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1009, "Tài khoản đã tồn tại, vui lòng chọn tài khoản khác", HttpStatus.BAD_REQUEST),
    USERNAME_IS_MISSING(1010, "Vui lòng nhập tài khoản", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1011, "Tài khoản không tồn tại", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(1012, "Sản phẩm không tồn tại", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final HttpStatusCode statusCode;
    private final String message;
}
