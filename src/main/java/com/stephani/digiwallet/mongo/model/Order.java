package com.stephani.digiwallet.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String referenceNumber;
    private String pickupDate;
    private String pickupTime;
    private String item;
    private String status;
    private String customerId;
    private String courierId;
}
