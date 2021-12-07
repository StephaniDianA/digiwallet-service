package com.stephani.digiwallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stephani.digiwallet.controller.UserController;
import com.stephani.digiwallet.mongo.model.User;
import com.stephani.digiwallet.mongo.repository.UserRepository;
import com.stephani.digiwallet.payload.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@SpringBootTest
public class DigiwalletApplicationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {

    }

    @Test
    void applicationContextTest() { DigiwalletApplication.main(new String[]{});}
}
