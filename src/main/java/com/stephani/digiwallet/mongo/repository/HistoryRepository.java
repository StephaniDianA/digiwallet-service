package com.stephani.digiwallet.mongo.repository;

import com.stephani.digiwallet.mongo.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends MongoRepository<History, String> {
}
