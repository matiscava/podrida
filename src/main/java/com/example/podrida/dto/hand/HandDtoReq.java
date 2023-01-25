package com.example.podrida.dto.hand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HandDtoReq {
    private int predict;
    private int take;
    private Long id = 0L;
    private Long playerId;
    private Long gameId;
    private int handNumber;

    @Override
    public String toString() {
        return "HandDtoReq{" +
                "predict=" + predict +
                ", take=" + take +
                ", id=" + id +
                ", playerId=" + playerId +
                ", gameId=" + gameId +
                ", handNumber=" + handNumber +
                '}';
    }
}
