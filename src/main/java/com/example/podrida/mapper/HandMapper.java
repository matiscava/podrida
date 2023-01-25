package com.example.podrida.mapper;

import com.example.podrida.dto.hand.HandDtoReq;
import com.example.podrida.dto.hand.HandDtoRes;
import com.example.podrida.entity.Hand;

public class HandMapper {
    public static Hand convertReqDtoToEntity(HandDtoReq playerHandDto){
        Hand h = new Hand();
        h.setId(playerHandDto.getId());
        h.setTake(playerHandDto.getTake());
        h.setPredict(playerHandDto.getPredict());
        h.setHandNumber(h.getHandNumber());
        return h;
    }
    public static HandDtoRes convertEntityToResDto(Hand h){
        HandDtoRes handDto = new HandDtoRes();
        handDto.setId(h.getId());
        handDto.setPredict(h.getPredict());
        handDto.setTake(h.getTake());
        handDto.setPoints(h.getPoints());
        handDto.setHandNumber(h.getHandNumber());
        handDto.setPlayerId(h.getPlayer().getId());
        handDto.setPlayerName(h.getPlayer().getName());
        return handDto;
    }
}
