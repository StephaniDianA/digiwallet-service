package com.stephani.digiwallet.service;

import com.stephani.digiwallet.entity.dto.UserDto;
import com.stephani.digiwallet.exception.UserNotFoundException;
import com.stephani.digiwallet.mongo.model.User;
import com.stephani.digiwallet.mongo.repository.UserRepository;
import com.stephani.digiwallet.payload.request.UserRequest;
import com.stephani.digiwallet.payload.response.BaseResponse;
import com.stephani.digiwallet.payload.response.DetailUserResponse;
import com.stephani.digiwallet.redis.repository.RedisUserRepository;
import com.stephani.digiwallet.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@PropertySource("classpath:kafka.properties")
public class UserService {

    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisUserRepository redisUserRepository;

    public DetailUserResponse create(UserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .role(request.getRole()).build();

        User userRepo = userRepository.save(user);

        UserDto userDto = UserDto.builder()
                .id(userRepo.getId())
                .name(userRepo.getName())
                .address(userRepo.getAddress())
                .phone(userRepo.getPhone())
                .email(userRepo.getEmail())
                .role(userRepo.getRole()).build();

        redisUserRepository.save(userDto);

        return new DetailUserResponse(userDto, ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }

    public DetailUserResponse update(String id, UserRequest request) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty())
            throw new UserNotFoundException("User id-" + id + " not found");

        User user = userOptional.get();
        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        User userRepo = userRepository.save(user);

        UserDto userDto = UserDto.builder()
                .id(userRepo.getId())
                .name(userRepo.getName())
                .address(userRepo.getAddress())
                .phone(userRepo.getPhone())
                .email(userRepo.getEmail())
                .role(userRepo.getRole()).build();

        redisUserRepository.save(userDto);

        return new DetailUserResponse(userDto, ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }

    public BaseResponse delete(String id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty())
            throw  new UserNotFoundException("User id-"+ id +" not found");

        userRepository.delete(userOptional.get());

        Optional<UserDto> userDtoCache = redisUserRepository.findById(id);

        userDtoCache.ifPresent(userDto -> redisUserRepository.delete(userDto));

        return new BaseResponse(ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }

    public DetailUserResponse findById(String id) throws UserNotFoundException {
        UserDto response = new UserDto();

        Optional<UserDto> userDtoCache = redisUserRepository.findById(id);
        if(userDtoCache.isEmpty()) {
            Optional<User> userOptional = userRepository.findById(id);

            if(userOptional.isEmpty())
                throw new UserNotFoundException("User Id-"+ id +" not found");

            User user = userOptional.get();
            response = UserDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .address(user.getAddress())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .role(user.getRole()).build();

            redisUserRepository.save(response);
        } else {
            response = userDtoCache.get();
        }

        return new DetailUserResponse(response, ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }
}
