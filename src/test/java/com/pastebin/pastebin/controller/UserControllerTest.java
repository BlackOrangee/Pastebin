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

    public static final String DEFAULT_USER_USERNAME = "userName";
    public static final String NEW_USER_USERNAME = "username";
    public static final String UPDATED_USER_USERNAME = "updatedUserName";
    public static final String PASSWORD = "password";
    public static final String CONTROLLER_PATH = "/api/v1/user";
    public static final String APPLICATION_JSON = "application/json";

    @Test
    void registerTest() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .username(NEW_USER_USERNAME)
                .password(PASSWORD)
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
                    MockMvcRequestBuilders.post(CONTROLLER_PATH)
                            .contentType(APPLICATION_JSON)
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
        assertEquals(NEW_USER_USERNAME, userDTO.getUsername());
    }

    @Test
    void getUserTest() {
        MvcResult result = null;
        try {
            result = mockMvc.perform(
                    MockMvcRequestBuilders.get(CONTROLLER_PATH+ "/1")
                            .contentType(APPLICATION_JSON)
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
        assertEquals(DEFAULT_USER_USERNAME, userDTO.getUsername());
    }

    @Test
    void updateUserTest() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .username(UPDATED_USER_USERNAME)
                .password(PASSWORD)
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
                    MockMvcRequestBuilders.put(CONTROLLER_PATH + "/1")
                            .contentType(APPLICATION_JSON)
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
        assertEquals(UPDATED_USER_USERNAME, userDTO.getUsername());
    }
}
