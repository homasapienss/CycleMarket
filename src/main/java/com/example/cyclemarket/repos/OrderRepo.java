package com.example.cyclemarket.repos;

import com.example.cyclemarket.entities.Order;
import com.example.cyclemarket.entities.OrderStatus;
import com.example.cyclemarket.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findOrdersByUser(User user);

    List<Order> getOrdersByShop_Id(Long shopId);

    Optional<Order> findByIdAndUser_Email(Long id, String userEmail);

    List<Order> findOrdersByShop_IdAndStatus(Long shopId, OrderStatus status);

    List<Order> findOrdersByStatus(OrderStatus status);

    Long shopId(Long shopId);

    List<Order> findOrdersByShop_Id(Long shopId);

    Optional<Order> findOrderByIdAndShop_Id(Long id, Long shopId);
}