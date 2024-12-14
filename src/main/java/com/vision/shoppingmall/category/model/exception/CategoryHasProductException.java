package com.vision.shoppingmall.category.model.exception;

public class CategoryHasProductException extends RuntimeException {
    public CategoryHasProductException() {
        super("해당 카테고리에 속한 제품이 있습니다.");
    }
}
