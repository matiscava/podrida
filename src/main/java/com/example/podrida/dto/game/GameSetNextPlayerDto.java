package com.example.podrida.dto.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameSetNextPlayerDto {
    private Long gameId;
    private int nextPlayer;
    private int handNumber=1;

    @Override
    public String toString() {
        return "GameSetNextPlayerDto{" +
                "gameId=" + gameId +
                ", nextPlayer=" + nextPlayer +
                ", handNumber=" + handNumber +
                '}';
    }
}
