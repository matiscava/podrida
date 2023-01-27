package com.example.podrida.service;

import com.example.podrida.dto.*;
import com.example.podrida.dto.game.*;
import com.example.podrida.dto.player.PlayerDtoRes;
import com.example.podrida.dto.player.PlayerGetPointsDto;
import com.example.podrida.dto.player.PlayerGetPredictDto;
import com.example.podrida.entity.Game;
import com.example.podrida.entity.Hand;
import com.example.podrida.entity.Player;
import com.example.podrida.exception.IdNotFoundException;
import com.example.podrida.mapper.GameMapper;
import com.example.podrida.mapper.PlayerMapper;
import com.example.podrida.repository.IGameRepository;
import com.example.podrida.repository.IHandRepository;
import com.example.podrida.repository.IPlayerRepository;
import com.example.podrida.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameService implements IGameService{
    private final IGameRepository gameRepository;
    private final IHandRepository handRepository;
    private final IPlayerRepository playerRepository;
    public GameService(IGameRepository gameRepository, IHandRepository handRepository, IPlayerRepository playerRepository){
        this.gameRepository = gameRepository;
        this.handRepository = handRepository;
        this.playerRepository = playerRepository;
    }
    @Override
    public List<GameDtoRes> getAll() {
        List<Game> gameList = gameRepository.findAll();
        List<GameDtoRes> gameDtoList = new ArrayList<>();
        gameList.forEach(
                g -> gameDtoList.add(GameMapper.convertEntityToDtoRes(g))
        );
        return gameDtoList;
    }

    @Override
    public GameDtoRes create(GameDtoReq gameDto) {
        Game g = GameMapper.convertReqDtoToEntity(gameDto);
        Game gSaved = gameRepository.save(g);
        return GameMapper.convertEntityToDtoRes(gSaved);
    }

    @Override
    public GameDtoRes getById(Long id) {
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        return GameMapper.convertEntityToDtoRes(g.get());
    }

    @Override
    public GameDtoRes update(GameDtoReq object) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        gameRepository.deleteById(id);
    }

    @Override
    public void setViewName(Long id, String viewName) {
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        g.get().setViewName(viewName);
        Game gSaved = gameRepository.save(g.get());
        GameMapper.convertEntityToDtoRes(gSaved);
    }

    @Override
    public List<PlayerDtoRes> getPlayers(Long id) {
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        List<PlayerDtoRes> playerDtoList = new ArrayList<>();
        List<Player> playerList = new ArrayList<>(g.get().getPlayerList());
        playerList.sort(Comparator.comparing(Player::getId));
        playerList.forEach(
                p -> playerDtoList.add(PlayerMapper.convertEntityToResDto(p))
        );

        return playerDtoList;
    }

    @Override
    public GameDtoRes setFirstPlayer(GameSetNextPlayerDto gameSetNextPlayer) {
        Optional<Game> g = gameRepository.findById(gameSetNextPlayer.getGameId());
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+ gameSetNextPlayer.getGameId());
        int order = 0;
        List<Player> playerList = new ArrayList<>(g.get().getPlayerList());
        playerList.sort(Comparator.comparing(Player::getId));
        for (int i = gameSetNextPlayer.getNextPlayer(); i < 7; i++){
            playerList.get(i).setPlayerOrder(order);
            playerRepository.save(playerList.get(i));
            order++;
        }
        for (int i = 0; i< gameSetNextPlayer.getNextPlayer(); i++){
            playerList.get(i).setPlayerOrder(order);
            playerRepository.save(playerList.get(i));
            order++;
        }
        g.get().setNextPlayer(0);
        g.get().setViewName("predict");
        Game gSaved = gameRepository.save(g.get());
        return GameMapper.convertEntityToDtoRes(gSaved);
    }

    @Override
    public PlayerDtoRes getPlayerTurn(Long id) {
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        int nextPlayer = g.get().getNextPlayer();
        if (nextPlayer>=7) nextPlayer=0;
        int finalNextPlayer = nextPlayer;
        Player p = g.get().getPlayerList().stream().filter(player -> player.getPlayerOrder() == finalNextPlayer).findFirst().orElse(null);
        if (p == null) throw new IdNotFoundException("El Usuario no exixte");
        return PlayerMapper.convertEntityToResDto(p);
    }

    @Override
    public int getAlreadyPredict(Long id) {
        AtomicInteger predicted = new AtomicInteger(0);
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        g.get().getPlayerList().forEach(
                p ->{
                    List<Hand> handList = p.getPlayerHands().stream().toList();
                    handList.forEach(
                            h->{
                                if (h.getHandNumber() == g.get().getHandNumber()){
                                    predicted.addAndGet(h.getPredict());
                                }
                            });
                }
        );
        return predicted.intValue();
    }

    @Override
    public int getCantPredictCardNumber(Long id) {
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        int predicted = this.getAlreadyPredict(id);
        int cardLimit = Utils.getLimitCard(g.get().getHandNumber());
        return (cardLimit< predicted) ? 22 : cardLimit - predicted;
    }

    @Override
    public int getCardLimit(Long id) {
        AtomicInteger taken = new AtomicInteger(0);
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        g.get().getPlayerList().forEach(
                p ->{
                    List<Hand> handList = p.getPlayerHands().stream().toList();
                    handList.forEach(
                            h->{
                                if (h.getHandNumber() == g.get().getHandNumber()){
                                    taken.addAndGet(h.getTake());
                                }
                            });
                }
        );
        int cardLimit = Utils.getLimitCard(g.get().getHandNumber());
        return cardLimit - taken.intValue();
    }

    @Override
    public List<PlayerGetPredictDto> getPredictHands(Long id) {
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        List<PlayerGetPredictDto> playerDtoList = new ArrayList<>();
        g.get().getPlayerList().forEach(
                p-> playerDtoList.add(PlayerMapper.convertEntityToGetHandDto(p))
        );

        playerDtoList.sort(Comparator.comparingInt(PlayerGetPredictDto::getPlayerOrder));
        return playerDtoList;
    }

    @Override
    public List<PlayerGetPointsDto> getEndHandDto(Long id) {
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        int handNumber = g.get().getHandNumber()-1;
        List<PlayerGetPointsDto> playerList = new ArrayList<>();
        g.get().getPlayerList().forEach( p -> playerList.add( PlayerMapper.convertEntityToPlayerDtoPointDto(p,handNumber) ) );
        playerList.sort(Comparator.comparingInt(PlayerGetPointsDto::getPlayerOrder));
        return playerList;
    }

    @Override
    public GameDtoPoints getGamePoints(Long id) {
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        return GameMapper.convertEntityToDtoPoints(g.get());
    }

    @Override
    public void goBack(Long id) {
        Optional<Game> g = gameRepository.findById(id);
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+id);
        int nextPlayer = g.get().getNextPlayer();
        String viewName = g.get().getViewName();
        int handNumber = g.get().getHandNumber();
        switch (viewName) {
            case "predict" -> {
                if (nextPlayer == 0) {
                    g.get().setViewName("endTaken");
                } else {
                    g.get().setNextPlayer(nextPlayer-1);
                }
            }
            case "lastPlayer" -> {
                g.get().setViewName("predict");
                g.get().setNextPlayer(nextPlayer-1);
            }
            case "endPredict" -> {
                g.get().setViewName("predict");
                g.get().setNextPlayer(6);
            }
            case "taken" -> {
                if (nextPlayer == 0) {
                    g.get().setViewName("endPredict");
                } else {
                    g.get().setNextPlayer(nextPlayer-1);
                }
            }
            case "endTaken" -> {
                g.get().setViewName("taken");
                g.get().setHandNumber(handNumber-1);
                g.get().setNextPlayer(6);
            }

        }
        gameRepository.save(g.get());
    }

    @Override
    public GameDtoViewName getViewNameDto(Long id) {
        Optional<Hand> h = handRepository.findById(id);
        if (h.isEmpty()) throw new IdNotFoundException("No existe la mano con el ID: "+id);
        Optional<Game> g = gameRepository.findById(h.get().getPlayer().getGame().getId());
        if(g.isEmpty()) throw new IdNotFoundException("No se encontró el juego con el ID: "+h.get().getPlayer().getGame().getId());
        return GameMapper.convertEntityToGameDtoViewName(g.get());
    }

    @Override
    public List<GameDtoGetAll> getGameList() {
        List<Game> gameList = gameRepository.findAll();
        List<GameDtoGetAll> gameDtoLists = new ArrayList<>();
        gameList.forEach(g -> gameDtoLists.add( GameMapper.convertEntityToGameDtoGetAll(g) ) );

        return gameDtoLists;
    }
}
