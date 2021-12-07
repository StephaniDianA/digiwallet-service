package com.stephani.digiwallet.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private String referenceNumber;
    @NotBlank
    private String pickupDate;
    @NotBlank
    private String pickupTime;
    @NotBlank
    private String item;
    private String status;
    @NotBlank
    private String customerId;
    private String courierId;
}
