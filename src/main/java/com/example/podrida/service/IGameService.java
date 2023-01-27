package com.example.podrida.service;

import com.example.podrida.dto.game.*;
import com.example.podrida.dto.player.PlayerDtoRes;
import com.example.podrida.dto.player.PlayerGetPointsDto;
import com.example.podrida.dto.player.PlayerGetPredictDto;

import java.util.List;


public interface IGameService extends IBaseService<GameDtoRes, GameDtoReq>{
    void setViewName(Long id, String viewName);
    List<PlayerDtoRes> getPlayers(Long id);
    GameDtoRes setFirstPlayer(GameSetNextPlayerDto gameSetNextUser);
    PlayerDtoRes getPlayerTurn(Long id);
    int getAlreadyPredict(Long id);
    int getCardLimit(Long id);
    int getCantPredictCardNumber(Long id);
    List<PlayerGetPredictDto> getPredictHands(Long id);
    List<PlayerGetPointsDto> getEndHandDto(Long id);
    GameDtoPoints getGamePoints(Long id);
    void goBack(Long id);
    GameDtoViewName getViewNameDto(Long id);
    List<GameDtoGetAll> getGameList();


}
