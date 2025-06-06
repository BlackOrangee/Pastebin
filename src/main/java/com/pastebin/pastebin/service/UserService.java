package com.pastebin.pastebin.service;

import com.pastebin.pastebin.controller.request.dto.UserRequestDTO;
import com.pastebin.pastebin.entity.dto.UserDTO;

public interface UserService {

    UserDTO createUser(UserRequestDTO userRequestDTO);

    UserDTO getUser(Long id);

    UserDTO updateUser(Long id, UserRequestDTO userRequestDTO);


}
