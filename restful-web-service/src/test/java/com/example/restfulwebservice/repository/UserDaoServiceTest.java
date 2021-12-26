package com.example.restfulwebservice.repository;

import com.example.restfulwebservice.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoServiceTest {


    @Test
    public void UserDaoService_객체_성성_테스트() {
        UserDaoService userDaoService = new UserDaoService();

        List<User> lists = userDaoService.findAll();

        assertThat(lists).isNotNull();
    }

    @Test
    public void UserDaoService_findAll_테스트() {
        UserDaoService userDaoService = new UserDaoService();
        List<User> lists = userDaoService.findAll();

        assertThat(lists.get(0).getId()).isEqualTo(1);
        assertThat(lists.get(0).getName()).isEqualTo("Kenneth");

        assertThat(lists.get(1).getId()).isEqualTo(2);
        assertThat(lists.get(1).getName()).isEqualTo("Alice");

        assertThat(lists.get(2).getId()).isEqualTo(3);
        assertThat(lists.get(2).getName()).isEqualTo("Elena");
    }

    @Test
    public void UserDaoService_save_테스트(){
        UserDaoService userDaoService = new UserDaoService();

        User user = new User();
        String name = "dongwoo";
        Date date = new Date();

        user.setName(name);
        user.setJoinDate(date);
        User savedUser = userDaoService.save(user);

        assertThat(savedUser.getId()).isEqualTo(4);
        assertThat(savedUser.getName()).isEqualTo(name);
        assertThat(savedUser.getJoinDate()).isEqualTo(date);
    }

    @Test
    public void User_삭제_기능_테스트(){
        UserDaoService userDaoService = new UserDaoService();

        User deletedUser = userDaoService.deleteById(1);

        assertThat(deletedUser.getId()).isEqualTo(1);
        assertThat(deletedUser.getName()).isEqualTo("Kenneth");
    }
}