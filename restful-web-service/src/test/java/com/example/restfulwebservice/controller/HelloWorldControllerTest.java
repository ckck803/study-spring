package com.example.restfulwebservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Hello_World가_리턴된다() throws Exception{
        ResultActions actions = mockMvc.perform(get("/hello-world"));

        actions
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print())
        ;
    }

    @Test
    public void Hello_World_Bean_test() throws Exception{
        ResultActions action = mockMvc.perform(get("/hello-world-bean"));

        action
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Hello World"))
                .andDo(print());
    }

    @Test
    public void Hello_World_Bean_Name_test() throws Exception{
        ResultActions actions = mockMvc.perform(get("/hello-world-bean/path-variable/dongwoo"));

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Hello World, dongwoo"))
                .andDo(print());
    }
}