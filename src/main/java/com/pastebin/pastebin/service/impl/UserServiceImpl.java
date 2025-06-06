package com.pastebin.pastebin.service.impl;

import com.pastebin.pastebin.controller.request.dto.UserRequestDTO;
import com.pastebin.pastebin.entity.User;
import com.pastebin.pastebin.entity.dto.UserDTO;
import com.pastebin.pastebin.entity.dto.mapper.UserDTOMapper;
import com.pastebin.pastebin.exeption.ErrorList;
import com.pastebin.pastebin.exeption.ServerException;
import com.pastebin.pastebin.repository.UserRepository;
import com.pastebin.pastebin.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserDTOMapper userDTOMapper;

    @Override
    @Transactional
    public UserDTO createUser(UserRequestDTO userRequestDTO) {
        log.debug("Create user {}", userRequestDTO);

        Optional<User> userOptional = userRepository.findByUsername(userRequestDTO.getUsername());
        if (userOptional.isPresent()) {
            throw new ServerException("User already exists", ErrorList.USER_ALREADY_EXIST);
        }
        User user = User.builder()
                .username(userRequestDTO.getUsername())
                .password(userRequestDTO.getPassword())
                .build();
        return userDTOMapper.apply(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDTO getUser(Long id) {
        log.debug("Get user {}", id);
        User user = userRepository.findById(id).orElseThrow(() ->
                new ServerException("User not found", ErrorList.USER_NOT_FOUND));
        return userDTOMapper.apply(user);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        log.debug("Update user {}", id);
        User user = userRepository.findById(id).orElseThrow(() ->
                new ServerException("User not found", ErrorList.USER_NOT_FOUND));

        if (!userRequestDTO.getPassword().equals(user.getPassword())) {
            throw new ServerException("Wrong password", ErrorList.WRONG_PASSWORD);
        }

        user.setUsername(userRequestDTO.getUsername());
        return userDTOMapper.apply(userRepository.save(user));
    }
}
