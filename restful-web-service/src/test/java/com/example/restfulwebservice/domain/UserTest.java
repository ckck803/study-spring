package com.example.restfulwebservice.domain;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    @Test
    public void User_생성_테스트(){
        Date date = new Date();
        User user = new User(1, "test", date);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getName()).isEqualTo("test");
        assertThat(user.getJoinDate()).isEqualTo(date);
    }
}