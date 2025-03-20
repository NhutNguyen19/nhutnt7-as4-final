package com.fis.bank.training.repository;

import com.fis.bank.training.constant.Status;
import com.fis.bank.training.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByStatus(Status status);

    List<Order> findByIdIn(List<String> orderIds);
}
