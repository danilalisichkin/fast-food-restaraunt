package com.fastfoodrestaraunt.backend.controller.api;

import com.fastfoodrestaraunt.backend.controller.doc.ProductControllerDoc;
import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductAddingDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductDto;
import com.fastfoodrestaraunt.backend.core.dto.product.ProductUpdatingDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.ProductSortField;
import com.fastfoodrestaraunt.backend.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController implements ProductControllerDoc {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageDto<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            @RequestParam(defaultValue = "id") ProductSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) Long categoryId) {

        PageDto<ProductDto> page = productService.getPageOfProducts(offset, limit, sortBy, sortOrder, categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProduct(id);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductAddingDto addingDto) {
        ProductDto product = productService.createProduct(addingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(
            @PathVariable Long id,
            @RequestBody @Valid ProductUpdatingDto updatingDto) {

        ProductDto product = productService.updateProduct(id, updatingDto);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
