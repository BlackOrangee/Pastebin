package com.pastebin.pastebin.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long id;

    private String name;

    private String content;

    private UserDTO creator;

    private Set<SignDTO> signs = new HashSet<>();

    private Date created;

    private Date lastChange;

    private Date expires;
}
