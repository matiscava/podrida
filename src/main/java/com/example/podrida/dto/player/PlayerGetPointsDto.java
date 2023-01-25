package com.example.podrida.dto.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PlayerGetPointsDto {
    private String name;
    private Long playerId;
    private int predict;
    private int take;
    private int points;
    private int playerOrder;
    private Long handId;
}