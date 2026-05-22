package com.example.cyclemarket.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private Integer totalPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
    @Column(nullable = false)
    private String recipientFullName;
    @Column(nullable = false)
    private String recipientPhone;
    @Column(nullable = false)
    private String deliveryAddress;
    private String comment;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

}
