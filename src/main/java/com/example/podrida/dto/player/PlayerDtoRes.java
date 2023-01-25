package com.example.podrida.dto.player;

import com.example.podrida.dto.hand.HandDtoRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerDtoRes {
    private Long id = 0L;
    private String name;
    private Set<HandDtoRes> hands = new HashSet<>();
    private int points;
    private int order;
    private List<Long> mistakeList = new ArrayList<>();
    private Long gameId;

    @Override
    public String toString() {
        return "PlayerDtoRes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hands=" + hands +
                ", points=" + points +
                ", order=" + order +
                ", mistakeList=" + mistakeList +
                ", gameId=" + gameId +
                '}';
    }
}
