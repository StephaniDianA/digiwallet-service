package com.stephani.digiwallet.controller;

import com.stephani.digiwallet.config.SwaggerConfig;
import com.stephani.digiwallet.payload.request.OrderRequest;
import com.stephani.digiwallet.payload.response.DetailOrderResponse;
import com.stephani.digiwallet.service.OrderService;
import com.stephani.digiwallet.util.UrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UrlUtil.V1.ORDERS)
@Api(tags = {SwaggerConfig.ORDER_CONTROLLER})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Return detail of order by Reff Number")
    @GetMapping("/{reffNum}")
    public ResponseEntity<DetailOrderResponse> findByReffNum(@PathVariable String reffNum) {
        DetailOrderResponse response = orderService.findByReffNum(reffNum);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Return detail of created order")
    @PostMapping
    public ResponseEntity<DetailOrderResponse> create(@Valid @RequestBody OrderRequest request) {
        DetailOrderResponse response = orderService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Return detail of updated order")
    @PutMapping("/{id}")
    public ResponseEntity<DetailOrderResponse> update(@PathVariable String id, @RequestBody OrderRequest request) {
        DetailOrderResponse response = orderService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
