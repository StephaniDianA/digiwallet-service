package com.stephani.digiwallet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stephani.digiwallet.mongo.model.User;
import com.stephani.digiwallet.mongo.repository.UserRepository;
import com.stephani.digiwallet.payload.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;
    @MockBean
    private UserRepository userRepository;

    private String id = "123";
    private User user = User.builder()
            .id("123")
            .name("Anna")
            .address("Mockito Street 7")
            .phone("1234567890")
            .email("anna@mail.com")
            .digicash(1000L)
            .role("customer").build();
    @Test
    public void findByIdTest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        assertEquals("Anna", userController.findById(id).getBody().getUser().getName());
    }

//    @Test
//    public void createUserTest() throws JsonProcessingException {
//        User userData = this.user;
//        userData.setId(null);
//
//        UserRequest userRequest = UserRequest.builder()
//                .name("Anna")
//                .address("Mockito Street 7")
//                .phone("1234567890")
//                .email("anna@mail.com")
//                .role("customer").build();
//        when(userRepository.save(userData)).thenReturn(this.user);
//        assertEquals("Anna", Objects.requireNonNull(userController.create(userRequest).getBody()).getUser().getName());
//
//    }

    @Test
    public void deleteTest() {
        userRepository.delete(user);
        verify(userRepository, times(1)).delete(user);
    }
}
