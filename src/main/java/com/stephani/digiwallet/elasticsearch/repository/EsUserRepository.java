package com.stephani.digiwallet.elasticsearch.repository;

import com.stephani.digiwallet.elasticsearch.model.EsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsUserRepository extends ElasticsearchRepository<EsUser, String> {

    @Override
    Page<EsUser> findAll();
}
