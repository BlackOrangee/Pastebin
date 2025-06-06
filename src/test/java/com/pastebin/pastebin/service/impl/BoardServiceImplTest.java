package com.pastebin.pastebin.service.impl;

import com.pastebin.pastebin.DateFormatter;
import com.pastebin.pastebin.controller.request.dto.BoardRequestDTO;
import com.pastebin.pastebin.entity.Board;
import com.pastebin.pastebin.entity.Sign;
import com.pastebin.pastebin.entity.User;
import com.pastebin.pastebin.entity.dto.BoardDTO;
import com.pastebin.pastebin.entity.dto.SignDTO;
import com.pastebin.pastebin.entity.dto.UserDTO;
import com.pastebin.pastebin.entity.dto.mapper.BoardDTOMapper;
import com.pastebin.pastebin.repository.BoardRepository;
import com.pastebin.pastebin.repository.SignRepository;
import com.pastebin.pastebin.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class BoardServiceImplTest {

    private BoardServiceImpl boardService;

    @Mock
    private BoardRepository boardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SignRepository signRepository;

    private AutoCloseable closeable;

    private static final Long USER_ID = 1L;

    private static final Long BOARD_ID = 1L;

    private static final String NAME = "name";

    private static final String USERNAME = "username";

    private static final String CONTENT = "content";

    private static final String CHANGED_CONTENT = "changed " + CONTENT;

    private static final Date CREATED_FORMATED_DATE = DateFormatter.format("30-05-2025");

    private static final Date EXPIRES_FORMATED_DATE = DateFormatter.format("31-05-2025");

    private static final String EXPIRES_DATE_STRING = "30-05-2025";

    private static final User USER = User.builder()
            .id(USER_ID)
            .username(USERNAME)
            .password("password")
            .build();

    private static final UserDTO USER_DTO = UserDTO.builder()
            .id(USER_ID)
            .username(USERNAME)
            .build();

    private static final Board BOARD = Board.builder()
            .id(BOARD_ID)
            .name(NAME)
            .content(CONTENT)
            .creator(USER)
            .created(CREATED_FORMATED_DATE)
            .lastChange(CREATED_FORMATED_DATE)
            .expires(EXPIRES_FORMATED_DATE)
            .build();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        boardService = new BoardServiceImpl(
                boardRepository,
                userRepository,
                signRepository,
                new BoardDTOMapper()
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void createBoardTest() {

        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
                .name(NAME)
                .content(CONTENT)
                .expires(EXPIRES_DATE_STRING)
                .build();


        BoardDTO expectedBoardDTO = BoardDTO.builder()
                .name(NAME)
                .content(CONTENT)
                .creator(USER_DTO)
                .created(null)
                .lastChange(null)
                .expires(CREATED_FORMATED_DATE)
                .build();


        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        Mockito.when(boardRepository.save(Mockito.any(Board.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        BoardDTO actualBoardDTO = boardService.createBoard(USER_ID, boardRequestDTO);

        assertEquals(expectedBoardDTO.getName(), actualBoardDTO.getName());
        assertEquals(expectedBoardDTO.getContent(), actualBoardDTO.getContent());
        assertEquals(expectedBoardDTO.getCreator(), actualBoardDTO.getCreator());
        assertEquals(expectedBoardDTO.getExpires(), actualBoardDTO.getExpires());
    }

    @Test
    void getBoardTest() {
        BoardDTO expectedBoardDTO = BoardDTO.builder()
                .name(NAME)
                .content(CONTENT)
                .creator(USER_DTO)
                .created(CREATED_FORMATED_DATE)
                .lastChange(CREATED_FORMATED_DATE)
                .expires(EXPIRES_FORMATED_DATE)
                .build();

        Mockito.when(boardRepository.findById(BOARD_ID)).thenReturn(Optional.of(BOARD));

        BoardDTO boardDTO = boardService.getBoard(BOARD_ID);

        assertEquals(expectedBoardDTO.getName(), boardDTO.getName());
        assertEquals(expectedBoardDTO.getContent(), boardDTO.getContent());
        assertEquals(expectedBoardDTO.getCreator(), boardDTO.getCreator());
    }

    @Test
    void updateBoardTest() {

        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
                .name(NAME)
                .content(CHANGED_CONTENT)
                .expires(EXPIRES_DATE_STRING)
                .build();

        Mockito.when(boardRepository.findById(BOARD_ID)).thenReturn(Optional.of(BOARD));
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        Mockito.when(signRepository.save(Mockito.any(Sign.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        Mockito.when(boardRepository.save(Mockito.any(Board.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Set<SignDTO> signs = new HashSet<>();
        signs.add(SignDTO.builder()
                .id(null)
                .user(USER_DTO)
                .boardId(BOARD_ID)
                .rowStart(0)
                .rowEnd(0)
                .lastChange(null)
                .build());

        BoardDTO expectedBoardDTO = BoardDTO.builder()
                .name(NAME)
                .content(CHANGED_CONTENT)
                .creator(USER_DTO)
                .signs(signs)
                .created(CREATED_FORMATED_DATE)
                .lastChange(CREATED_FORMATED_DATE)
                .expires(EXPIRES_FORMATED_DATE)
                .build();


        BoardDTO actualBoardDTO = boardService.updateBoard(USER_ID, BOARD_ID, boardRequestDTO);


        Optional<SignDTO> expectedSignOptional = expectedBoardDTO.getSigns().stream().findFirst();
        Optional<SignDTO> actualSign = actualBoardDTO.getSigns().stream().findFirst();

        assertTrue(expectedSignOptional.isPresent());
        assertTrue(actualSign.isPresent());

        SignDTO expectedSign = expectedSignOptional.get();
        SignDTO actualSignDTO = actualSign.get();

        assertEquals(expectedBoardDTO.getName(), actualBoardDTO.getName());
        assertEquals(expectedBoardDTO.getContent(), actualBoardDTO.getContent());
        assertEquals(expectedBoardDTO.getCreator(), actualBoardDTO.getCreator());
        assertEquals(expectedSign.getId(), actualSignDTO.getId());
        assertEquals(expectedSign.getUser(), actualSignDTO.getUser());
        assertEquals(expectedSign.getBoardId(), actualSignDTO.getBoardId());
        assertEquals(expectedSign.getRowStart(), actualSignDTO.getRowStart());
        assertEquals(expectedSign.getRowEnd(), actualSignDTO.getRowEnd());
    }
}
