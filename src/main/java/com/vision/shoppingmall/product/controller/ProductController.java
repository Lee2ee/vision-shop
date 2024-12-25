package com.vision.shoppingmall.product.controller;

import com.vision.shoppingmall.category.model.response.CategoryResponse;
import com.vision.shoppingmall.category.service.CategoryService;
import com.vision.shoppingmall.product.model.request.CreateProductRequest;
import com.vision.shoppingmall.product.model.request.ProductUpdateRequest;
import com.vision.shoppingmall.product.model.response.ProductListResponse;
import com.vision.shoppingmall.product.model.response.ProductResponse;
import com.vision.shoppingmall.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("")
    public String getProductList(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<ProductListResponse> products = productService.getProductList(page);
        model.addAttribute("products", products);
        return "product/list";
    }

    @GetMapping("/new-product")
    public String getProductForm(Model model) {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new CreateProductRequest());
        return "product/product-form";
    }

    @PostMapping("/new-product")
    public String createProduct(@ModelAttribute(name = "product") @Valid CreateProductRequest request, BindingResult result, Model model) {
        //1. 유효성 검사 실행시 request객체 담아서 다시 제품 등록 화면 반환 (입력한 것 그대로)
        if(result.hasErrors()){
            List<CategoryResponse> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("product", request);
            return "product/product-form";
        }
        //2. 제품 추가 성공시 목록 페이지로 리다이렉트
        productService.createProduct(request);
        return "redirect:/products";
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(Model model, @PathVariable Long id) {
        //1. 제품 및 카테고리 정보 포함하여 뷰 템플릿 리턴
        List<CategoryResponse> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        ProductResponse product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "product/product-form";
    }

    @PostMapping("/update-product/{id}")
    public String updatedProduct(@PathVariable Long id, @ModelAttribute(name = "product") @Valid ProductUpdateRequest request, BindingResult result, Model model) {
        //1. 제품 및 카테고리 정보 포함하여 뷰 템플릿 리턴
        if(result.hasErrors()) {
            List<CategoryResponse> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);

//            ProductResponse product = productService.getProduct(id);
            model.addAttribute("product", request);
            return "product/product-form";
        }
        //2. 제품 수정 후 제품 목록으로 리다이렉트
        productService.updateProduct(id, request);
        return "redirect:/products";
    }

}