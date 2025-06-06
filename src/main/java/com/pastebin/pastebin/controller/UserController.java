package com.pastebin.pastebin.controller;

import com.pastebin.pastebin.controller.request.dto.UserRequestDTO;
import com.pastebin.pastebin.entity.dto.UserDTO;
import com.pastebin.pastebin.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDTO register(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        log.info("Register user {}", userRequestDTO.getUsername());
        return userService.createUser(userRequestDTO);
    }

    @GetMapping(value = "/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        log.info("Get user {}", id);
        return userService.getUser(id);
    }

    @PutMapping(value = "/{id}")
    public UserDTO updateUser(@PathVariable Long id,
                              @RequestBody @Valid UserRequestDTO userRequestDTO) {
        log.info("Update user {}", userRequestDTO.getUsername());
        return userService.updateUser(id, userRequestDTO);
    }
}
