package com.fastfoodrestaraunt.backend.repository;

import com.fastfoodrestaraunt.backend.entity.Category;
import com.fastfoodrestaraunt.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    Page<Product> findAllByCategory(Pageable pageable, Category category);

    boolean existsByName(String name);
}
