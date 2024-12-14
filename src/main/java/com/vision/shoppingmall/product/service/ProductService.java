package com.vision.shoppingmall.product.service;

import com.vision.shoppingmall.category.model.entity.Category;
import com.vision.shoppingmall.category.model.exception.CategoryNameDuplicationException;
import com.vision.shoppingmall.category.model.exception.CategoryNotFoundException;
import com.vision.shoppingmall.category.repository.CategoryRepository;
import com.vision.shoppingmall.product.model.entity.Product;
import com.vision.shoppingmall.product.model.entity.ProductStatus;
import com.vision.shoppingmall.product.model.request.ProductUpdateRequest;
import com.vision.shoppingmall.product.model.response.ProductResponse;
import com.vision.shoppingmall.product.repository.ProductRepository;
import com.vision.shoppingmall.product.model.request.CreateProductRequest;
import com.vision.shoppingmall.product.model.response.ProductListResponse;
import com.vision.shoppingmall.product.model.exception.ProductNotFountException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void removeCategory(List<Product> products) {
        //1. 카테고리 아이디를 null로 변경
        products.forEach(product -> {
           product.setCategory(null);
           productRepository.save(product);
        });

    }


    public void createProduct(CreateProductRequest request) {
        //1. 카테고리 존재 여부
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        //2. 제품 등록
        Product product = Product.create(request, category);
        productRepository.save(product);
    }

    public Page<ProductListResponse> getProductList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> products = productRepository.findByProductStatus(ProductStatus.ACTIVE, pageable);
        return products.map(ProductListResponse::from);
    }

    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFountException::new);

        return ProductResponse.from(product);
    }

    public void updateProduct(Long id, ProductUpdateRequest request) {
        //1. 해당 제품 존재하는지 검증
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFountException::new);

        //1-2. 카테고리 존재 여부
        Category category = request.getCategoryId() != null
                ? categoryRepository.findById(request.getCategoryId()).orElseThrow(CategoryNotFoundException::new)
                : null;

        //3. 제품 수정
        product.update(request, category);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        //1. 해당 제품 존재하는지 검증
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFountException::new);

        //2. 상태 변경
        product.delete();

        //3. 상태 저장
        productRepository.save(product);
    }
}
