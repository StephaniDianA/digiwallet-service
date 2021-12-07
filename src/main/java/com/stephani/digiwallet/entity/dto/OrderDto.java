package com.stephani.digiwallet.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "Order", timeToLive = 60)
public class OrderDto {

    private static final long serialVersionUID = 1L;

    private String id;
    private String referenceNumber;
    private String pickupDate;
    private String pickupTime;
    private String item;
    private String status;
    private String customerId;
    private String courierId;
}
