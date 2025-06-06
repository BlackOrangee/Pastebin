package com.pastebin.pastebin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pastebin.pastebin.controller.request.dto.BoardRequestDTO;
import com.pastebin.pastebin.entity.dto.BoardDTO;
import com.pastebin.pastebin.entity.dto.SignDTO;
import com.pastebin.pastebin.entity.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void createBoardTest() {
        long userId = 1L;
        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
                .name("name")
                .content("content")
                .expires("30-05-2025")
                .build();

        String json = null;
        try {
            json = objectMapper.writeValueAsString(boardRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MvcResult result = null;
        try {
            assert json != null;
            result = mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/v1/board?userId=" + userId)
                            .contentType("application/json")
                            .content(json)
            ).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BoardDTO boardDTO = null;
        try {
            assert result != null;
            boardDTO = objectMapper.readValue(result.getResponse().getContentAsString(), BoardDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(boardDTO);
        assertEquals("name", boardDTO.getName());
        assertEquals("content", boardDTO.getContent());
    }

    @Test
    void getBoardTest() {
        long boardId = 1L;

        MvcResult result = null;
        try {
            result = mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/v1/board/" + boardId)
                            .contentType("application/json")
            ).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BoardDTO boardDTO = null;
        try {
            assert result != null;
            boardDTO = objectMapper.readValue(result.getResponse().getContentAsString(), BoardDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserDTO expectedCreator = UserDTO.builder()
                .id(1L)
                .username("user1")
                .build();

        assertNotNull(boardDTO);
        assertEquals("name", boardDTO.getName());
        assertEquals("content", boardDTO.getContent());
        assertEquals(expectedCreator, boardDTO.getCreator());
    }

    @Test
    void updateBoardTest() {
        long boardId = 1L;
        long userId = 1L;

        String text = "text";

        MvcResult result = null;
        try {
            result = mockMvc.perform(
                    MockMvcRequestBuilders.put("/api/v1/board/" + boardId + "?userId=" + userId)
                            .contentType("text/plain")
                            .content(text)
            ).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BoardDTO boardDTO = null;
        try {
            assert result != null;
            boardDTO = objectMapper.readValue(result.getResponse().getContentAsString(), BoardDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserDTO userDTO = UserDTO.builder()
                .id(userId)
                .username("user1")
                .build();

        SignDTO expectedSignDTO = SignDTO.builder()
                .id(1L)
                .user(userDTO)
                .boardId(boardId)
                .rowStart(0)
                .rowEnd(0)
                .lastChange(null)
                .build();

        BoardDTO expectedBoardDTO = BoardDTO.builder()
                .name("name")
                .content("text")
                .creator(userDTO)
                .signs(null)
                .created(null)
                .lastChange(null)
                .expires(null)
                .build();

        assertNotNull(boardDTO);

        UserDTO actualUserDTO = boardDTO.getCreator();
        SignDTO actualSignDTO = boardDTO.getSigns().iterator().next();

        assertEquals(expectedBoardDTO.getName(), boardDTO.getName());
        assertEquals(expectedBoardDTO.getContent(), boardDTO.getContent());

        assertNotNull(actualUserDTO);
        assertEquals(userDTO.getId(), actualUserDTO.getId());
        assertEquals(userDTO.getUsername(), actualUserDTO.getUsername());

        assertNotNull(actualSignDTO);
        assertEquals(expectedSignDTO.getId(), actualSignDTO.getId());
        assertEquals(expectedSignDTO.getUser(), actualSignDTO.getUser());
        assertEquals(expectedSignDTO.getBoardId(), actualSignDTO.getBoardId());
        assertEquals(expectedSignDTO.getRowStart(), actualSignDTO.getRowStart());
        assertEquals(expectedSignDTO.getRowEnd(), actualSignDTO.getRowEnd());

    }
}
