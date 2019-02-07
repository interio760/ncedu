package com.edunetcracker.startreker.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void shouldReturnValidationError() throws Exception {
        mockMvc.perform(get("/api/test"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("inputname")));
    }

    @Test
    @WithMockUser
    public void shouldBeOk() throws Exception {
        mockMvc.perform(get("/api/test?number=123&text=test"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("123")))
                .andExpect(content().string(containsString("test")));
    }
}
