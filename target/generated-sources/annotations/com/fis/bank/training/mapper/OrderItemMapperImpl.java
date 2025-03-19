package com.fis.bank.training.mapper;

import com.fis.bank.training.dto.request.OrderItemRequest;
import com.fis.bank.training.dto.response.OrderItemResponse;
import com.fis.bank.training.model.OrderItem;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItem toOrderItem(OrderItemRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderItem.OrderItemBuilder orderItem = OrderItem.builder();

        orderItem.quantity( request.getQuantity() );
        orderItem.unitPrice( request.getUnitPrice() );
        orderItem.order( request.getOrder() );
        orderItem.product( request.getProduct() );

        return orderItem.build();
    }

    @Override
    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemResponse.OrderItemResponseBuilder orderItemResponse = OrderItemResponse.builder();

        orderItemResponse.id( orderItem.getId() );
        orderItemResponse.quantity( orderItem.getQuantity() );
        orderItemResponse.unitPrice( orderItem.getUnitPrice() );
        orderItemResponse.order( orderItem.getOrder() );
        orderItemResponse.product( orderItem.getProduct() );

        return orderItemResponse.build();
    }
}
