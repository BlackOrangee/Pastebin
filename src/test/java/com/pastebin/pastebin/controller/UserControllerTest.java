package com.pastebin.pastebin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pastebin.pastebin.controller.request.dto.UserRequestDTO;
import com.pastebin.pastebin.entity.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerTest() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .username("username")
                .password("password")
                .build();

        String json = null;
        try {
            json = objectMapper.writeValueAsString(userRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MvcResult result = null;
        try {
            assert json != null;
            result = mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/v1/user")
                            .contentType("application/json")
                            .content(json)
            ).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserDTO userDTO = null;
        try {
            assert result != null;
            userDTO = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(userDTO);
        assertEquals(2L, userDTO.getId());
        assertEquals("username", userDTO.getUsername());
    }

    @Test
    void getUserTest() {
        MvcResult result = null;
        try {
            result = mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/v1/user/1")
                            .contentType("application/json")
            ).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserDTO userDTO = null;
        try {
            assert result != null;
            userDTO = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(userDTO);
        assertEquals(1L, userDTO.getId());
        assertEquals("user1", userDTO.getUsername());
    }

    @Test
    void updateUserTest() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .username("username2")
                .password("password1")
                .build();

        String json = null;
        try {
            json = objectMapper.writeValueAsString(userRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MvcResult result = null;
        try {
            assert json != null;
            result = mockMvc.perform(
                    MockMvcRequestBuilders.put("/api/v1/user/1")
                            .contentType("application/json")
                            .content(json)
            ).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserDTO userDTO = null;
        try {
            assert result != null;
            userDTO = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(userDTO);
//        assertEquals(1L, userDTO.getId());
        assertEquals("username2", userDTO.getUsername());
    }
}
