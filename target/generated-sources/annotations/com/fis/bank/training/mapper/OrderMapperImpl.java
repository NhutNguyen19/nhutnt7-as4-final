package com.fis.bank.training.mapper;

import com.fis.bank.training.dto.request.OrderRequest;
import com.fis.bank.training.dto.response.OrderResponse;
import com.fis.bank.training.model.Order;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toOrder(OrderRequest request) {
        if ( request == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.checkIn( request.getCheckIn() );
        order.checkOut( request.getCheckOut() );
        order.orderDate( request.getOrderDate() );
        order.status( request.getStatus() );
        order.totalAmount( request.getTotalAmount() );

        return order.build();
    }

    @Override
    public OrderResponse toOrderResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse.OrderResponseBuilder orderResponse = OrderResponse.builder();

        orderResponse.id( order.getId() );
        orderResponse.checkIn( order.getCheckIn() );
        orderResponse.checkOut( order.getCheckOut() );
        orderResponse.orderDate( order.getOrderDate() );
        orderResponse.status( order.getStatus() );
        orderResponse.totalAmount( order.getTotalAmount() );

        return orderResponse.build();
    }
}
