package com.stephani.digiwallet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stephani.digiwallet.elasticsearch.model.EsUser;
import com.stephani.digiwallet.elasticsearch.repository.EsUserRepository;
import com.stephani.digiwallet.entity.dto.UserDto;
import com.stephani.digiwallet.exception.DataNotFound;
import com.stephani.digiwallet.mongo.model.History;
import com.stephani.digiwallet.mongo.model.User;
import com.stephani.digiwallet.mongo.repository.UserRepository;
import com.stephani.digiwallet.payload.request.UserRequest;
import com.stephani.digiwallet.payload.response.BaseResponse;
import com.stephani.digiwallet.payload.response.DetailUserResponse;
import com.stephani.digiwallet.redis.repository.RedisUserRepository;
import com.stephani.digiwallet.util.Constant;
import com.stephani.digiwallet.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Optional;

@Service
@PropertySource("classpath:kafka.properties")
public class UserService {

    @Value(value = "${kafka.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisUserRepository redisUserRepository;
    @Autowired
    private EsUserRepository esUserRepository;

    public DetailUserResponse create(UserRequest request) throws JsonProcessingException {
        User user = User.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .role(request.getRole().toUpperCase())
                .digicash(0L).build();

        User userRepo = userRepository.save(user);

        long millis = System.currentTimeMillis();
        History history = History.builder()
                .primaryId(userRepo.getId())
                .primaryCode(Constant.PRIMARY_CODE_USER)
                .oldValue(new ObjectMapper().writeValueAsString(userRepo))
                .newValue(null)
                .action(Constant.ACTION_CREATE)
                .generateDate(millis).build();

        publishHistory(new ObjectMapper().writeValueAsString(history));
//        publishHistory(history.toString());

        UserDto userDto = UserDto.builder()
                .id(userRepo.getId())
                .name(userRepo.getName())
                .address(userRepo.getAddress())
                .phone(userRepo.getPhone())
                .email(userRepo.getEmail())
                .role(userRepo.getRole())
                .digicash(userRepo.getDigicash()).build();

        redisUserRepository.save(userDto);

        EsUser esUser = new EsUser();
        esUser.setId(userRepo.getId());
        esUser.setName(userRepo.getName());
        esUser.setAddress(userRepo.getAddress());
        esUser.setRole(userRepo.getRole());

        esUserRepository.save(esUser);

        return new DetailUserResponse(userDto, ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }

    public DetailUserResponse update(String id, UserRequest request) {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty())
            throw new DataNotFound("User id-" + id + " not found");

        User user = userOptional.get();
        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole().toUpperCase());
        user.setDigicash(request.getDigicash());
        User userRepo = userRepository.save(user);

        UserDto userDto = UserDto.builder()
                .id(userRepo.getId())
                .name(userRepo.getName())
                .address(userRepo.getAddress())
                .phone(userRepo.getPhone())
                .email(userRepo.getEmail())
                .role(userRepo.getRole())
                .digicash(userRepo.getDigicash()).build();

        redisUserRepository.save(userDto);

        EsUser esUser = new EsUser();
        esUser.setId(userRepo.getId());
        esUser.setName(userRepo.getName());
        esUser.setAddress(userRepo.getAddress());
        esUser.setRole(userRepo.getRole());

        esUserRepository.save(esUser);

        return new DetailUserResponse(userDto, ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }

    public BaseResponse delete(String id) {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty())
            throw new DataNotFound("User id-" + id + " not found");

        userRepository.delete(userOptional.get());

        Optional<UserDto> userDtoCache = redisUserRepository.findById(id);

        userDtoCache.ifPresent(userDto -> redisUserRepository.delete(userDto));

        Optional<EsUser> esUserDocs = esUserRepository.findById(id);

        esUserDocs.ifPresent(esUser -> esUserRepository.delete(esUser));

        return new BaseResponse(ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }

    public DetailUserResponse findById(String id) {
        UserDto response;

        Optional<UserDto> userDtoCache = redisUserRepository.findById(id);
        if(userDtoCache.isEmpty()) {
            Optional<User> userOptional = userRepository.findById(id);

            if(userOptional.isEmpty())
                throw new DataNotFound("User id-" + id + " not found");

            User user = userOptional.get();
            response = UserDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .address(user.getAddress())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .digicash(user.getDigicash()).build();

            redisUserRepository.save(response);
        } else {
            response = userDtoCache.get();
        }

        return new DetailUserResponse(response, ResponseUtil.Message.SUCCESS, ResponseUtil.Code.SUCCESS);
    }

    private void publishHistory(String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Unable to send message=[" + message + "] due to: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                System.out.println("Sent message=[" + message + "] with offset=[" + stringStringSendResult.getRecordMetadata().offset() + "]");

            }
        });
    }
}
