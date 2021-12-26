package com.example.sampleredis;

import com.example.sampleredis.domain.Point;
import com.example.sampleredis.repository.PointRedisRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisTest {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @AfterEach
    public void tearDown() throws Exception {
        pointRedisRepository.deleteAll();
    }

    @Test
    @DisplayName("기본 등록 조회 기능")
    public void retrieve(){
        String id = "ckck803";
        LocalDateTime refreshTime = LocalDateTime.of(2021,3,16,0,0);

        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        pointRedisRepository.save(point);

        Point savedPoint = pointRedisRepository.findById(id).get();
        assertThat(savedPoint.getAmount()).isEqualTo(1000L);
        assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
    }

    @Test
    @DisplayName("수정 기능")
    public void revise(){
        String id = "ckck803";
        LocalDateTime refreshTime = LocalDateTime.of(2021,3,16,0,0);

        pointRedisRepository.save(Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build());

        Point savedPoint = pointRedisRepository.findById(id).get();
        savedPoint.refresh(2000L, LocalDateTime.of(2021,3,17,0,0));
        pointRedisRepository.save(savedPoint);

        Point refreshPoint = pointRedisRepository.findById(id).get();
        assertThat(refreshPoint.getAmount()).isEqualTo(2000L);
    }
}
