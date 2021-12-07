package com.stephani.digiwallet.mongo.repository;

import com.stephani.digiwallet.mongo.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByReferenceNumber(String reffNum);
}
