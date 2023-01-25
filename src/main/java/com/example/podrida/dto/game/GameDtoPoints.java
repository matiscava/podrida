package com.example.podrida.dto.game;

import com.example.podrida.dto.player.PlayerDtoGamePoints;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDtoPoints {
    private Long id;
    private int handNumber;
    private List<PlayerDtoGamePoints> playerList = new ArrayList<>();
}
