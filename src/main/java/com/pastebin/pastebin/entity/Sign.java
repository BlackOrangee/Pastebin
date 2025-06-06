package com.pastebin.pastebin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Signs")
public class Sign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "last_change", nullable = false)
    private Date lastChange;

    @Column(name = "row_start", nullable = false)
    private Integer rowStart;

    @Column(name = "row_end", nullable = false)
    private Integer rowEnd;
}
