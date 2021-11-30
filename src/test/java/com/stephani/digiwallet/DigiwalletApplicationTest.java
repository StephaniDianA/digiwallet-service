package com.stephani.digiwallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.stephani.digiwallet.controller.UserController;
import com.stephani.digiwallet.mongo.model.User;
import com.stephani.digiwallet.mongo.repository.UserRepository;
import com.stephani.digiwallet.payload.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class DigiwalletApplicationTest {
    
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
            .role("customer").build();
    @Test
    public void findByIdtest() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        assertEquals("Anna", userController.findById(id).getBody().getUser().getName());
    }
    
    @Test
    public void saveTest() {
        User userData = this.user;
        userData.setId(null);

        UserRequest userRequest = UserRequest.builder()
                .name("Anna")
                .address("Mockito Street 7")
                .phone("123456789")
                .email("anna@mail.com")
                .role("customer").build();
        when(userRepository.save(userData)).thenReturn(this.user);
        assertEquals("Anna", userController.create(userRequest).getBody().getUser().getName());

    }

    @Test
    public void deleteTest() {
        userRepository.delete(user);
        verify(userRepository, times(1)).delete(user);
    }
}
