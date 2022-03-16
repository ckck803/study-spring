package com.example.springwebsocketkafka.controller;

import com.example.springwebsocketkafka.domain.dto.ChatMessage;
import com.example.springwebsocketkafka.kafka.KafkaProducer;
import com.example.springwebsocketkafka.provider.JwtTokenProvider;
import com.example.springwebsocketkafka.repository.ChatRoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final KafkaProducer producer;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("token") String token) throws JsonProcessingException {
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);

        log.info("message" + message.toString());
        // 채팅방 인원수 세팅
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));

        producer.sendMessage(message);
    }
}
