package com.stephani.digiwallet.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "digiwallet.users")
public class EsUser {
    @Id
    private String id;
    private String name;
    private String address;
    private String role;
}
