package com.stephani.digiwallet.payload.response;

import com.stephani.digiwallet.entity.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderResponse extends BaseResponse {

    private OrderDto order;

    public DetailOrderResponse(String message, String code) { super(message, code);}

    public DetailOrderResponse(OrderDto order, String message, String code) {
        super(message, code);
        this.order = order;
    }
}
