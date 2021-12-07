package com.stephani.digiwallet.redis.repository;

import com.stephani.digiwallet.entity.dto.OrderDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisOrderRepository extends CrudRepository<OrderDto, String> {
    Optional<OrderDto> findByReferenceNumber(String reffNum);
}
