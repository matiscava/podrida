package com.example.podrida.dto.hand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HandDtoRes {
    private Long id=0L;
    private int predict;
    private int take;
    private int points;
    private Long playerId;
    private String playerName;
    private int handNumber;

    @Override
    public String toString() {
        return "HandDtoRes{" +
                "id=" + id +
                ", predict=" + predict +
                ", take=" + take +
                ", points=" + points +
                ", playerId=" + playerId +
                ", handNumber=" + handNumber +
                '}';
    }
}
