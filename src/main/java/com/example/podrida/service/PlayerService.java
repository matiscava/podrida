package com.example.podrida.service;

import com.example.podrida.dto.SuccessfullyMessageDto;
import com.example.podrida.dto.hand.HandSetTakeCardsDto;
import com.example.podrida.dto.player.PlayerDtoChangeName;
import com.example.podrida.dto.player.PlayerDtoGetMistake;
import com.example.podrida.dto.player.PlayerDtoReq;
import com.example.podrida.dto.player.PlayerDtoRes;
import com.example.podrida.entity.Game;
import com.example.podrida.entity.Player;
import com.example.podrida.exception.IdNotFoundException;
import com.example.podrida.exception.InvalidValueException;
import com.example.podrida.mapper.PlayerMapper;
import com.example.podrida.repository.IGameRepository;
import com.example.podrida.repository.IPlayerRepository;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class PlayerService implements IPlayerService {
    final private IPlayerRepository playerRepository;
    final private IGameRepository gameRepository;
    public PlayerService(IPlayerRepository playerRepository, IGameRepository gameRepository){
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public List<PlayerDtoRes> getAll() {
        List<Player> userList = playerRepository.findAll();
        List<PlayerDtoRes> playerDtoList = new ArrayList<>();
        userList.forEach(
                p -> playerDtoList.add(PlayerMapper.convertEntityToResDto(p))
        );
        return playerDtoList;
    }

    @Override
    public PlayerDtoRes create(PlayerDtoReq userDto) {
        Optional<Game> game = gameRepository.findById(userDto.getGameId());
        if(game.isEmpty()) throw new IdNotFoundException("No se ha encontrado el juego con ese ID");
        if (game.get().getPlayerList().size() == 7) throw new InvalidValueException("No se pueden agregar más usuarios a este juego.");
        Player p = PlayerMapper.convertReqDtoToEntity(userDto,game.get());
        Player pSaved = playerRepository.save(p);
        return PlayerMapper.convertEntityToResDto(pSaved);
    }

    @Override
    public PlayerDtoRes getById(Long id) {
        Optional<Player> p = playerRepository.findById(id);
        if (p.isEmpty()) throw new IdNotFoundException("No existe el Jugador con ID "
                +id+", modifique los datos.");
        return PlayerMapper.convertEntityToResDto(p.get());
    }

    @Override
    public PlayerDtoRes update(PlayerDtoReq objcet) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Player> p = playerRepository.findById(id);
        if (p.isEmpty()) throw new IdNotFoundException("No existe el Jugador con ID "
                +id+", modifique los datos.");
        playerRepository.deleteById(id);
    }

    @Override
    public HandSetTakeCardsDto getCurrentHandDto(Long id) {
        Optional<Player> p = playerRepository.findById(id);
        if(p.isEmpty()) throw new IdNotFoundException("No se encontró el usuario con el ID: "+id);
        return PlayerMapper.convertEntityToHandGetCardDto(p.get());
    }

    @Override
    public List<PlayerDtoGetMistake> getPlayersMistake(Long gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if(game.isEmpty()) throw new IdNotFoundException("No se ha encontrado el juego con ese ID");
        List<PlayerDtoGetMistake> playerDtoList = new ArrayList<>();
        List<Player> playerList = game.get().getPlayerList().stream().toList();
        playerList.forEach(
                p -> {
                    if (p.getMistakesMadeList().size() > 0 ){
                        playerDtoList.add(PlayerMapper.convertEntityToPlayerDtoGetMistake(p));
                    }
                }
        );
        playerDtoList.sort(Comparator.comparing(PlayerDtoGetMistake::getName));

        return playerDtoList;
    }

    @Override
    public Long changeName(PlayerDtoChangeName pDto) {
        Optional<Player> p = playerRepository.findById(pDto.getId());
        if(p.isEmpty()) throw new IdNotFoundException("No se encontró el usuario con el ID: "+pDto.getId());
        p.get().setName(pDto.getName());
        playerRepository.save(p.get());
        return p.get().getGame().getId();
    }
}
