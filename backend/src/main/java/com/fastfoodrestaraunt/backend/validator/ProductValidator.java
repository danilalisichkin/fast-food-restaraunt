package com.fastfoodrestaraunt.backend.validator;

import com.fastfoodrestaraunt.backend.exception.DataUniquenessConflictException;
import com.fastfoodrestaraunt.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductValidator {
    private final ProductRepository productRepository;

    public void validateProductNameUnique(String name) {
        if (productRepository.existsByName(name)) {
            throw new DataUniquenessConflictException(
                    String.format("product with name %s already exists", name));
        }
    }
}
