package com.example.podrida.dto.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PlayerGetPredictDto {
    private String name;
    private Long userId;
    private int predict;
    private int playerOrder;
    private Long handId;

    @Override
    public String toString() {
        return "UserGetPredictDto{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", predict=" + predict +
                ", handId=" + handId +
                '}';
    }
}
