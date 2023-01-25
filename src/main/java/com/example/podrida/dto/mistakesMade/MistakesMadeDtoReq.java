package com.example.podrida.dto.mistakesMade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MistakesMadeDtoReq {
    private Long playerId = 0L;
    private Long mistakeId = 0L;
    private Long gameId = 0L;
    private int handNumber = 0;
    private boolean replayHand = false;

    @Override
    public String toString() {
        return "MistakeMadeDtoReq{" +
                "playerId=" + playerId +
                ", mistakeId=" + mistakeId +
                ", gameId=" + gameId +
                ", handNumber=" + handNumber +
                ", replayHand=" + replayHand +
                '}';
    }
}
