package com.stephani.digiwallet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stephani.digiwallet.mongo.model.History;
import com.stephani.digiwallet.mongo.repository.HistoryRepository;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:kafka.properties")
public class ListenerService {

//    @Value(value = "${kafka.topic.name}")
//    private String topicName;
    private final ObjectMapper mapper;
    private final HistoryRepository historyRepository;

    public ListenerService(ObjectMapper mapper, HistoryRepository historyRepository) {
        this.mapper = mapper;
        this.historyRepository = historyRepository;
    }

    @KafkaListener(topics = "${kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory")
    public void listenAction(String message) {
        try {
            History history = this.mapper.readValue(message, History.class);

//            historyRepository.save(history);

            History historyDto = History.builder()
                    .primaryId(history.getPrimaryId())
                    .primaryCode(history.getPrimaryCode())
                    .oldValue(history.getOldValue())
                    .newValue(history.getNewValue())
                    .action(history.getAction())
                    .generateDate(history.getGenerateDate()).build();

            historyRepository.save(historyDto);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
