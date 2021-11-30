package com.stephani.digiwallet.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "User", timeToLive = 60)
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String role;
}
