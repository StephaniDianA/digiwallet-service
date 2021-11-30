package com.stephani.digiwallet.controller;

import com.stephani.digiwallet.config.SwaggerConfig;
import com.stephani.digiwallet.elasticsearch.model.EsUser;
import com.stephani.digiwallet.elasticsearch.repository.EsUserRepository;
import com.stephani.digiwallet.payload.response.ListUserResponse;
import com.stephani.digiwallet.util.ResponseUtil;
import com.stephani.digiwallet.util.UrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = UrlUtil.V1.USERS)
@Api(tags = {SwaggerConfig.ELASTIC_CONTROLLER})
public class ElasticUserController {

    @Autowired
    private EsUserRepository repository;

    @ApiOperation(value = "Return list of users with Elasticsearch")
    @GetMapping
    public ResponseEntity<ListUserResponse> findAllWithelastic(){
        List<EsUser> userList = repository.findAll().getContent();

        return new ResponseEntity<>(
                new ListUserResponse(userList, ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS), HttpStatus.OK
        );

    }
}
