package com.example.podrida.service;

import com.example.podrida.dto.hand.HandSetTakeCardsDto;
import com.example.podrida.dto.player.PlayerDtoChangeName;
import com.example.podrida.dto.player.PlayerDtoGetMistake;
import com.example.podrida.dto.player.PlayerDtoReq;
import com.example.podrida.dto.player.PlayerDtoRes;

import java.util.List;


public interface IPlayerService extends IBaseService<PlayerDtoRes, PlayerDtoReq>{
    HandSetTakeCardsDto getCurrentHandDto(Long id);
    List<PlayerDtoGetMistake> getPlayersMistake(Long gameId);
    Long changeName(PlayerDtoChangeName pDto);
}
