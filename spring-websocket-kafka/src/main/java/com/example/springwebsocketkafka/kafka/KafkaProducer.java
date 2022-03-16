package com.example.springwebsocketkafka.kafka;

import com.example.springwebsocketkafka.domain.dto.ChatMessage;
import com.example.springwebsocketkafka.repository.ChatRoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "chat";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(ChatMessage chatMessage) throws JsonProcessingException {
        chatMessage.setUserCount(chatRoomRepository.getUserCount(chatMessage.getRoomId()));
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에 입장했습니다.");
            chatMessage.setSender("[알림]");
        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            chatMessage.setSender("[알림]");
        }

        String message = objectMapper.writeValueAsString(chatMessage);
        log.info("Send Message = {}", message);
        kafkaTemplate.send(TOPIC, message);
    }
}
