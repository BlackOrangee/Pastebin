package com.pastebin.pastebin.entity.dto.mapper;

import com.pastebin.pastebin.entity.User;
import com.pastebin.pastebin.entity.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper  implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
