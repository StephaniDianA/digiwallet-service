package com.stephani.digiwallet;

import com.stephani.digiwallet.controller.ElasticUserController;
import com.stephani.digiwallet.elasticsearch.model.EsUser;
import com.stephani.digiwallet.elasticsearch.repository.EsUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ElasticsearchTest {

    @Autowired
    private ElasticUserController elasticUserController;

    @MockBean
    private EsUserRepository elasticUserRepository;

    @Test
    public void findAllProductTest() {
        when(elasticUserRepository.findAll()).thenReturn(
                new PageImpl<EsUser>(Stream.of(
                        new EsUser("123","Anna","Mockito Street 7","customer"), new EsUser("456","Bob","Springboot Street 9","courier")

                ).collect(Collectors.toList()))
        );
        assertEquals(2, elasticUserController.findAllWithelastic().getBody().getUsers().size());
    }
}
