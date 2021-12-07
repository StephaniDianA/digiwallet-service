package com.stephani.digiwallet.service;

import com.stephani.digiwallet.entity.dto.OrderDto;
import com.stephani.digiwallet.exception.DataNotFound;
import com.stephani.digiwallet.mongo.model.Order;
import com.stephani.digiwallet.mongo.repository.OrderRepository;
import com.stephani.digiwallet.payload.request.OrderRequest;
import com.stephani.digiwallet.payload.response.DetailOrderResponse;
import com.stephani.digiwallet.redis.repository.RedisOrderRepository;
import com.stephani.digiwallet.util.Constant;
import com.stephani.digiwallet.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RedisOrderRepository redisOrderRepository;

    public DetailOrderResponse create(OrderRequest request) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String formatDateTime = now.format(formatter);
        Order order = Order.builder()
                .referenceNumber(request.getCustomerId() + formatDateTime)
                .customerId(request.getCustomerId())
                .pickupDate(request.getPickupDate())
                .pickupTime(request.getPickupTime())
                .item(request.getItem())
                .status(Constant.STATUS_CREATED).build();

        Order orderRepo = orderRepository.save(order);

        OrderDto orderDto = OrderDto.builder()
                .id(orderRepo.getId())
                .referenceNumber(orderRepo.getReferenceNumber())
                .customerId(orderRepo.getCustomerId())
                .pickupDate(orderRepo.getPickupDate())
                .pickupTime(orderRepo.getPickupTime())
                .item(orderRepo.getItem())
                .status(orderRepo.getStatus()).build();

        redisOrderRepository.save(orderDto);

        return new DetailOrderResponse(orderDto, ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }

    public DetailOrderResponse update(String id, OrderRequest request) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if(orderOptional.isEmpty())
            throw new DataNotFound("Order id-"+ id +" not found");

        Order order = orderOptional.get();
        order.setReferenceNumber(request.getReferenceNumber());
        order.setCustomerId(request.getCustomerId());
        order.setCourierId(request.getCourierId());
        order.setPickupDate(request.getPickupDate());
        order.setPickupTime(request.getPickupTime());
        order.setItem(request.getItem());
        order.setStatus(request.getStatus());
        Order orderRepo = orderRepository.save(order);

        OrderDto orderDto = OrderDto.builder()
                .id(orderRepo.getId())
                .referenceNumber(orderRepo.getReferenceNumber())
                .customerId(orderRepo.getCustomerId())
                .pickupDate(orderRepo.getPickupDate())
                .pickupTime(orderRepo.getPickupTime())
                .item(orderRepo.getItem())
                .status(orderRepo.getStatus()).build();

        redisOrderRepository.save(orderDto);

        return new DetailOrderResponse(orderDto, ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }

    public DetailOrderResponse findByReffNum(String reffNum) {
        OrderDto response;

        Optional<OrderDto> orderDtoCache = redisOrderRepository.findByReferenceNumber(reffNum);
        if(orderDtoCache.isEmpty()) {
            Optional<Order> orderOptional = orderRepository.findByReferenceNumber(reffNum);

            if(orderOptional.isEmpty())
                throw new DataNotFound("Order "+ reffNum +" not found");

            Order order = orderOptional.get();
            response = OrderDto.builder()
                    .id(order.getId())
                    .referenceNumber(order.getReferenceNumber())
                    .customerId(order.getCustomerId())
                    .courierId(order.getCourierId())
                    .pickupDate(order.getPickupDate())
                    .pickupTime(order.getPickupTime())
                    .item(order.getItem())
                    .status(order.getStatus()).build();

            redisOrderRepository.save(response);
        } else {
            response = orderDtoCache.get();
        }

        return new DetailOrderResponse(response, ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }
}
