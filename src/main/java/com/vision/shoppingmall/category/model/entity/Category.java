package com.vision.shoppingmall.category.model.entity;

import com.vision.shoppingmall.category.model.request.CreateCategoryRequest;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@Entity(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    public static Category create(CreateCategoryRequest request) {
        return Category.builder()
                .categoryName(request.getCategoryName())
                .build();
    }

    public void update(String categoryName) {
        this.setCategoryName(categoryName);
    }
}
