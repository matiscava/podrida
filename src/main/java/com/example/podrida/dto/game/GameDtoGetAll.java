package com.example.podrida.dto.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameDtoGetAll {
    private Long id;
    private String playerList;
    private LocalDate timestamp = LocalDate.now();
}
