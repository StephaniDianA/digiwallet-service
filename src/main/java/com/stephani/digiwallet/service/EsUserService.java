package com.stephani.digiwallet.service;

import com.stephani.digiwallet.elasticsearch.model.EsUser;
import com.stephani.digiwallet.elasticsearch.repository.EsUserRepository;
import com.stephani.digiwallet.exception.DataNotFound;
import com.stephani.digiwallet.payload.response.BaseResponse;
import com.stephani.digiwallet.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EsUserService {

    @Autowired
    private EsUserRepository esUserRepository;

    public BaseResponse deleteDocs(String id) {

        Optional<EsUser> esUserOptional = esUserRepository.findById(id);

        if (esUserOptional.isEmpty())
            throw new DataNotFound("User id-" + id + " not found");
        esUserOptional.ifPresent(esUser -> esUserRepository.delete(esUser));
        return new BaseResponse(ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);

    }
}
