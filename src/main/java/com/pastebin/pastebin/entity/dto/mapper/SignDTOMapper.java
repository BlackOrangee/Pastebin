package com.pastebin.pastebin.entity.dto.mapper;

import com.pastebin.pastebin.entity.Sign;
import com.pastebin.pastebin.entity.dto.SignDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SignDTOMapper implements Function<Sign, SignDTO> {
    @Override
    public SignDTO apply(Sign sign) {
        return SignDTO.builder()
                .id(sign.getId())
                .user(new UserDTOMapper().apply(sign.getUser()))
                .boardId(sign.getBoard().getId())
                .lastChange(sign.getLastChange())
                .rowStart(sign.getRowStart())
                .rowEnd(sign.getRowEnd())
                .build();
    }
}
