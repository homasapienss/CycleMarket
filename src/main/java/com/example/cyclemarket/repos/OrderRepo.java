package com.example.cyclemarket.repos;

import com.example.cyclemarket.entities.Order;
import com.example.cyclemarket.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);

    List<Order> findOrdersByUser(User user);
}
