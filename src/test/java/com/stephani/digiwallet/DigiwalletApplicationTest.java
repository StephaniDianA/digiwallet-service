package com.stephani.digiwallet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@SpringBootTest
class DigiwalletApplicationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {

    }

    @Test
    void applicationContextTest() { DigiwalletApplication.main(new String[]{});}
}
