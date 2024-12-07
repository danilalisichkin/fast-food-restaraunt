package com.fastfoodrestaraunt.backend.repository;

import com.fastfoodrestaraunt.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
}
