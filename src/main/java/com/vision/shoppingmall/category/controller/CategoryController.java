package com.vision.shoppingmall.category.controller;

import com.vision.shoppingmall.category.model.request.CategoryUpdateRequest;
import com.vision.shoppingmall.category.model.request.CreateCategoryRequest;
import com.vision.shoppingmall.category.model.response.CategoryListResponse;
import com.vision.shoppingmall.category.model.response.CategoryResponse;
import com.vision.shoppingmall.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public String getCategoryList(Model model,
                                  @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<CategoryListResponse> categories = categoryService.getCategories(page);
        model.addAttribute("categories", categories);
        model.addAttribute("paging", categories);
        return "category/list";
    }

    @GetMapping("/new-category")
    public String getCategoryForm(Model model) {
        model.addAttribute("modalTitle", "카테고리 생성하기");
        return "category/category-form";
    }

    @PostMapping("/new-category")
    public String createCategory(@ModelAttribute @Valid CreateCategoryRequest request, BindingResult result) {
        //1. 카테고리 생성 실패 시에는 오류 반환
        if(result.hasErrors()) {
            throw new IllegalArgumentException((
                    result.getAllErrors().getFirst().getDefaultMessage()
            ));
        }
        //2. 카테고리 생성 성공 시에 카테고리 목록 페이지로 리다이렉트
        categoryService.create(request);
        return "redirect:/categories";
    }

    @GetMapping("/update-category/{id}")
    public String updateCategoryForm(Model model, @PathVariable Long id) {

        CategoryResponse category = categoryService.findCategory(id);

        model.addAttribute("category", category);
        model.addAttribute("modalTitle", "카테고리 수정하기");
        return "category/category-form";
    }

    @PostMapping("/update-category/{id}")
    public String updatedCategory(@PathVariable Long id, @ModelAttribute @Valid CategoryUpdateRequest request, BindingResult result) {
        if(result.hasErrors()) {
            throw new IllegalArgumentException((
                    result.getAllErrors().getFirst().getDefaultMessage()
            ));
        }

        //1. 카테고리 수정 성공시 목록조회 리다이렉트
        categoryService.update(id, request);
        return "redirect:/categories";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable int id, @ModelAttribute @Valid CategoryUpdateRequest request,
                                 BindingResult result) {
        //1. 카테고리 수정 성공시 목록으로 리다이렉트


        return "category/category-form";
    }
}
