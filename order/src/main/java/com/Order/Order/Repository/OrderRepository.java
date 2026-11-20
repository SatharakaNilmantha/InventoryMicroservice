package com.Order.Order.Repository;


import com.Order.Order.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders WHERE id = ?1", nativeQuery = true)
    Order getOrderById(long id);

    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<Order> getAllOrders();
}