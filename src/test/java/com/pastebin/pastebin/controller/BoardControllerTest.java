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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static final String BOARD_NAME = "boardName";
    public static final String DEFAULT_BOARD_NAME = "default board name";
    public static final String CONTENT = "content";
    public static final String EXPIRES = "30-06-2025";
    public static final String CONTROLLER_PATH = "/api/v1/board";
    public static final String USER_PATH_PARAM = "userId=";
    public static final String APPLICATION_JSON = "application/json";
    public static final String DEFAULT_USER_NAME = "userName";


    @Test
    void createBoardTest() {
        long userId = 1L;
        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
                .name(BOARD_NAME)
                .content(CONTENT)
                .expires(EXPIRES)
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
                    MockMvcRequestBuilders.post(CONTROLLER_PATH + "?" + USER_PATH_PARAM + userId)
                            .contentType(APPLICATION_JSON)
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
        assertEquals(BOARD_NAME, boardDTO.getName());
        assertEquals(CONTENT, boardDTO.getContent());
    }

    @Test
    void getBoardTest() {
        long boardId = 1L;

        MvcResult result = null;
        try {
            result = mockMvc.perform(
                    MockMvcRequestBuilders.get(CONTROLLER_PATH + "/" + boardId)
                            .contentType(APPLICATION_JSON)
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
                .username(DEFAULT_USER_NAME)
                .build();

        assertNotNull(boardDTO);
        assertEquals(DEFAULT_BOARD_NAME, boardDTO.getName());
        assertEquals(CONTENT, boardDTO.getContent());
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
                    MockMvcRequestBuilders.put(CONTROLLER_PATH + "/" + boardId + "?" + USER_PATH_PARAM + userId)
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
                .username(DEFAULT_USER_NAME)
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
                .name(DEFAULT_BOARD_NAME)
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
