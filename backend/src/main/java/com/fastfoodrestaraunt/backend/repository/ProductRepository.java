package com.fastfoodrestaraunt.backend.repository;

import com.fastfoodrestaraunt.backend.entity.Category;
import com.fastfoodrestaraunt.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    List<Product> findAllByCategory(Category category);

    boolean existsByName(String name);
}
