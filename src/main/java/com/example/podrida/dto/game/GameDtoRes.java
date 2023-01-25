package com.example.podrida.dto.game;

import com.example.podrida.dto.player.PlayerDtoRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDtoRes {
    private Long id=0L;
    private List<PlayerDtoRes> playerList = new ArrayList<>();
    private LocalDate timestamp = LocalDate.now();
    private int handNumber=1;
    private int nextPlayer;
    private String viewName;
}
