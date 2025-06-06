package com.pastebin.pastebin.entity.dto.mapper;

import com.pastebin.pastebin.entity.Board;
import com.pastebin.pastebin.entity.dto.BoardDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BoardDTOMapper implements Function<Board, BoardDTO> {

    @Override
    public BoardDTO apply(Board board) {
        return BoardDTO.builder()
                .id(board.getId())
                .name(board.getName())
                .content(board.getContent())
                .creator(new UserDTOMapper().apply(board.getCreator()))
                .signs(board.getSigns().stream().map(new SignDTOMapper()).collect(Collectors.toSet()))
                .created(board.getCreated())
                .lastChange(board.getLastChange())
                .expires(board.getExpires())
                .build();
    }
}
