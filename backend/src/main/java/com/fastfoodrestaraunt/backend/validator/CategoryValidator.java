package com.fastfoodrestaraunt.backend.validator;

import com.fastfoodrestaraunt.backend.exception.DataUniquenessConflictException;
import com.fastfoodrestaraunt.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidator {
    private final CategoryRepository categoryRepository;

    public void validateCategoryNameUnique(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new DataUniquenessConflictException(
                    String.format("category with name %s already exists", name));
        }
    }
}
