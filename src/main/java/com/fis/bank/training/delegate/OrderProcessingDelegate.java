package com.fis.bank.training.delegate;

import com.fis.bank.training.constant.Status;
import com.fis.bank.training.model.Order;
import com.fis.bank.training.repository.OrderRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderProcessingDelegate implements JavaDelegate {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String orderId = (String) execution.getVariable("orderId");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(Status.APPROVED);
        order.setCheckOut(LocalDateTime.now());
        orderRepository.save(order);

        System.out.println("✅ Đơn hàng " + orderId + " đã được xử lý...");

        execution.setVariable("orderStatus", "APPROVED");
    }
}
