package com.fastfoodrestaraunt.backend.repository;

import com.fastfoodrestaraunt.backend.core.enums.Status;
import com.fastfoodrestaraunt.backend.entity.Order;
import com.fastfoodrestaraunt.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByStatus(Pageable pageable, Status status);

    Page<Order> findAllByUser(Pageable pageable, User user);

    Page<Order> findAllByUserAndStatus(Pageable pageable, User user, Status status);
}
