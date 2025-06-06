package com.pastebin.pastebin.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignDTO {

    private Long id;

    private UserDTO user;

    private Long boardId;

    private Date lastChange;

    private Integer rowStart;

    private Integer rowEnd;
}
