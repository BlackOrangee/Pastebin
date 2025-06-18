package com.pastebin.pastebin.service.impl;

import com.pastebin.pastebin.controller.request.dto.UserRequestDTO;
import com.pastebin.pastebin.entity.User;
import com.pastebin.pastebin.entity.dto.UserDTO;
import com.pastebin.pastebin.entity.dto.mapper.UserDTOMapper;
import com.pastebin.pastebin.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private static final Long USER_ID = 1L;

    private static final String USERNAME = "username";

    public static final String PASSWORD = "password";


    private static final User USER = User.builder()
            .id(USER_ID)
            .username(USERNAME)
            .password(PASSWORD)
            .build();

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(
                userRepository,
                new UserDTOMapper()
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void createUserTest() {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        UserDTO expectedUserDTO = UserDTO.builder()
                .id(1L)
                .username(USERNAME)
                .build();

        UserDTO userDTO = userService.createUser(userRequestDTO);

        assertEquals(expectedUserDTO.getUsername(), userDTO.getUsername());
    }

    @Test
    void getUserTest() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        UserDTO userDTO = userService.getUser(USER_ID);
        assertEquals(USER_ID, userDTO.getId());
        assertEquals(USERNAME, userDTO.getUsername());
    }

    @Test
    void updateUserTest() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();

        UserDTO userDTO = userService.updateUser(USER_ID, userRequestDTO);
        assertEquals(USER_ID, userDTO.getId());
        assertEquals(USERNAME, userDTO.getUsername());
    }

}
