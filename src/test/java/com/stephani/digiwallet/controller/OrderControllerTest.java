package com.stephani.digiwallet.controller;

import com.stephani.digiwallet.mongo.model.Order;
import com.stephani.digiwallet.mongo.repository.OrderRepository;
import com.stephani.digiwallet.util.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@SpringBootTest
public class OrderControllerTest {

    @Autowired
    private OrderController orderController;
    @MockBean
    private OrderRepository orderRepository;

    private String id = "99999";
    private String reffNum = "12307122021131900";
    private Order order = Order.builder()
            .id("99999")
            .referenceNumber("12307122021131900")
            .customerId("123")
            .pickupDate("07-12-2021")
            .pickupTime("anytime")
            .item("PET")
            .courierId(null)
            .status(Constant.STATUS_CREATED)
            .build();

    @Test
    public void findByRefnumTest() {
        when(orderRepository.findByReferenceNumber(reffNum)).thenReturn(Optional.of(order));
        assertEquals("123", Objects.requireNonNull(orderController.findByReffNum(reffNum).getBody()).getOrder().getCustomerId());
    }
//
//    @Test
//    public void createOrderTest() {
//        Order orderData = this.order;
//        orderData.setId(null);
//
//        OrderRequest orderRequest = OrderRequest.builder()
//                .referenceNumber("12307122021131900")
//                .customerId("123")
//                .pickupDate("07-12-2021")
//                .pickupTime("anytime")
//                .item("PET")
//                .status(Constant.STATUS_CREATED).build();
//        when(orderRepository.save(orderData)).thenReturn(this.order);
//        assertEquals("123", Objects.requireNonNull(orderController.create(orderRequest).getBody()).getOrder().getCustomerId());
//    }
}
