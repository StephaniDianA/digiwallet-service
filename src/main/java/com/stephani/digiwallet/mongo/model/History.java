package com.stephani.digiwallet.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "history")
public class History {

    @Id
    private String primaryId;
    private String primaryCode;
    private String oldValue;
    private String newValue;
    private String action;
    private Long generateDate;
}
