package com.vision.shoppingmall.product.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductNameDuplicationException extends RuntimeException {
    public ProductNameDuplicationException() {
        super("해당 제품이 이미 존재합니다.");
    }
}
