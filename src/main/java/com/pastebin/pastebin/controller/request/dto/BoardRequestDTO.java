package com.pastebin.pastebin.controller.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRequestDTO {

    private String name;

    private String content;

    private String expires;
}
