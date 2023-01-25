package com.example.podrida.dto.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDtoReq {
    private String name = "";
    private Long id = 0L;
    private Long gameId;
    private int number;
}
