package com.stephani.digiwallet.redis.repository;

import com.stephani.digiwallet.entity.dto.UserDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisUserRepository extends CrudRepository<UserDto, String> {
}
