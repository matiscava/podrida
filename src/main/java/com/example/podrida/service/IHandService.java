package com.example.podrida.service;

import com.example.podrida.dto.game.GameDtoRes;
import com.example.podrida.dto.hand.HandDtoReq;
import com.example.podrida.dto.hand.HandDtoRes;
import com.example.podrida.dto.hand.HandDtoUpdate;
import com.example.podrida.dto.hand.HandSetTakeCardsDto;

import java.util.Set;

public interface IHandService extends IBaseService<HandDtoRes, HandDtoReq>{
    Long updateHand(HandDtoUpdate object);

    GameDtoRes setTakeCard(HandSetTakeCardsDto handDto);
    void getPoints(Long gameId);
    Long getHandID(int handNumber, Set<HandDtoRes> handDtoList);
    int getPredicted(GameDtoRes gameDtoRes);
    int getTaken(GameDtoRes gameDtoRes);
}
