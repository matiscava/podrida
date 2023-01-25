package com.example.podrida.dto.hand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HandSetTakeCardsDto {
    private Long id;
    private int predict;
    private int take;
    private Long playerId;
    private int handNumber;
    private String playerName;
    private Long gameId;
}
