package com.stephani.digiwallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stephani.digiwallet.config.SwaggerConfig;
import com.stephani.digiwallet.payload.request.UserRequest;
import com.stephani.digiwallet.payload.response.BaseResponse;
import com.stephani.digiwallet.payload.response.DetailUserResponse;
import com.stephani.digiwallet.service.UserService;
import com.stephani.digiwallet.util.UrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = UrlUtil.V1.USERS)
@Api(tags = {SwaggerConfig.USER_CONTROLLER})
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Return detail of user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<DetailUserResponse> findById(@PathVariable String id) {
        DetailUserResponse response = userService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Return detail of created user")
    @PostMapping
    public ResponseEntity<DetailUserResponse> create(@Valid @RequestBody UserRequest request) throws JsonProcessingException {
        DetailUserResponse response = userService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Return detail of updated user")
    @PutMapping("/{id}")
    public ResponseEntity<DetailUserResponse> update(@PathVariable String id, @RequestBody UserRequest request) {
        DetailUserResponse response = userService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Return response of user deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable String id) {
        BaseResponse response = userService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
