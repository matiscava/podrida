package com.example.podrida.controller;


import com.example.podrida.dto.game.GameDtoReq;
import com.example.podrida.dto.game.GameDtoRes;
import com.example.podrida.dto.game.GameSetNextPlayerDto;
import com.example.podrida.dto.hand.HandDtoReq;
import com.example.podrida.dto.hand.HandSetTakeCardsDto;
import com.example.podrida.dto.mistakesMade.MistakesMadeDtoReq;
import com.example.podrida.dto.player.PlayerDtoReq;
import com.example.podrida.dto.player.PlayerDtoRes;
import com.example.podrida.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/game")
public class GameController {
    private final IGameService gameService;
    private final IPlayerService playerService;
    private final IMistakeService mistakeService;
    private final IHandService handService;

    public GameController(GameService gameService, PlayerService playerService, MistakeService mistakeService, HandService handService){
        this.gameService = gameService;
        this.playerService = playerService;
        this.mistakeService = mistakeService;
        this.handService = handService;
    }

    @GetMapping("/getAll")
    public ModelAndView getAll(){
        ModelAndView modelAndView = new ModelAndView("gameList");
        modelAndView.addObject( "gamesList",gameService.getAll());
        return modelAndView;
    }
    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        GameDtoRes gameDto = gameService.getById(id);
        if (gameDto.getPlayerList().size()<7){
            int number = gameDto.getPlayerList().size()+1;
            modelAndView.setViewName("redirect:/game/"+id+"/addPlayer?number="+number);
            return modelAndView;
        }
        if (gameDto.getNextPlayer() == -1){
            modelAndView.setViewName("redirect:/game/"+id+"/setFirst");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/game/"+id+"/hand");
        return modelAndView;

    }
    @GetMapping("/{id}/addPlayer")
    public ModelAndView addPlayer(
            @PathVariable Long id,
            @RequestParam int number
    ){
        ModelAndView modelAndView = new ModelAndView();
        if(number>7) {
            modelAndView.setViewName("redirect:/game/"+id+"/setFirst");
            return modelAndView;
        }
        PlayerDtoReq playerDto = new PlayerDtoReq();
        playerDto.setGameId(id);
        playerDto.setNumber(number);
        modelAndView.setViewName("playerConfig");
        modelAndView.addObject("player", playerDto);

        return modelAndView;
    }
    @GetMapping("/{id}/setFirst")
    public ModelAndView setFirst(
            @PathVariable Long id
    ){
        List<PlayerDtoRes> userDtoRes = gameService.getPlayers(id);
        ModelAndView modelAndView = new ModelAndView("setFirstPlayer");
        modelAndView.addObject("gameSetNextPlayer",new GameSetNextPlayerDto());
        modelAndView.addObject("playerList", userDtoRes);
        return modelAndView;
    }

    @GetMapping("/{id}/hand")
    public ModelAndView handConfig(
            @PathVariable Long id
    ){
        ModelAndView modelAndView = new ModelAndView("handConfig");
        GameDtoRes gameDto = gameService.getById(id);
        String viewName = gameDto.getViewName().equals("")||gameDto.getViewName().isEmpty() ? "predict" : gameDto.getViewName();
        int cardLimit = gameService.getCardLimit(id);
        modelAndView.addObject("game",gameDto);
        switch (viewName) {
            case "predict" -> {
                PlayerDtoRes p = gameService.getPlayerTurn(id);
                Long handId = 0L;
                if (p.getHands().size() == gameDto.getHandNumber()) {
                    handId = handService.getHandID(gameDto.getHandNumber(),p.getHands());
                }
                modelAndView.addObject("handId", handId);
                modelAndView.addObject("player", p);
                modelAndView.addObject("predicted", gameService.getAlreadyPredict(id));
                if (gameDto.getNextPlayer() == 6) {
                    modelAndView.addObject("cannotPredict", gameService.getCantPredictCardNumber(id));
                    viewName = "lastPlayer";
                }
                modelAndView.addObject("handDto", new HandDtoReq());
            }
            case "endPredict" -> {
                Long handId = 0L;
                PlayerDtoRes p = gameService.getPlayerTurn(id);
                if (p.getHands().size() == gameDto.getHandNumber()) {
                    handId = handService.getHandID(gameDto.getHandNumber(),p.getHands());
                }
                modelAndView.addObject("predicted", handService.getPredicted(gameDto));
                modelAndView.addObject("handId", handId);
                modelAndView.addObject("playerList", gameService.getPredictHands(id));
            }
            case "taken" -> {
                HandSetTakeCardsDto handDto = playerService.getCurrentHandDto(gameService.getPlayerTurn(id).getId());
                modelAndView.addObject("hand", handDto);
            }
            case "endTaken" -> {
                modelAndView.addObject("predicted", handService.getPredicted(gameDto));
                modelAndView.addObject("taken", handService.getTaken(gameDto));
                modelAndView.addObject("playerList", gameService.getEndHandDto(id));
                --cardLimit;
            }
            case "endGame" -> {
                return new ModelAndView("redirect:/game/"+id+"/points");
            }
        }
        modelAndView.addObject("cardLimit",cardLimit);
        modelAndView.addObject("viewName",viewName);

        return modelAndView;
    }
    @GetMapping("/{id}/points")
    public ModelAndView getPoints(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("tablePoints");
        modelAndView.addObject("game", gameService.getGamePoints(id));
        return modelAndView;
    }

    @GetMapping("/{id}/mistake")
    public ModelAndView getMistake(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("mistake");
        modelAndView.addObject("playerList", gameService.getPlayers(id));
        modelAndView.addObject("mistakeList", mistakeService.getAll());
        MistakesMadeDtoReq mistakeDto = new MistakesMadeDtoReq();
        mistakeDto.setGameId(id);
        mistakeDto.setHandNumber(gameService.getById(id).getHandNumber());
        modelAndView.addObject("mistakeDto",mistakeDto);

        return modelAndView;
    }
    @GetMapping("/{id}/mistakeList")
    public ModelAndView getMistakeList(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("mistakeGameList");
        modelAndView.addObject("mistakeMade", playerService.getPlayersMistake(id));
        modelAndView.addObject("gameId",id);
        return modelAndView;
    }


    @PostMapping("/new")
    public ModelAndView create (@ModelAttribute GameDtoReq g){
        GameDtoRes gCreated = gameService.create(g);
        return new ModelAndView("redirect:/game/"+gCreated.getId());
    }

    @PostMapping("/setFirstPlayer")
    public ModelAndView setNextUser (@ModelAttribute GameSetNextPlayerDto gameSetNextPlayer){
        GameDtoRes g = gameService.setFirstPlayer(gameSetNextPlayer);
        return new ModelAndView("redirect:/game/"+g.getId()+"/hand");
    }

    @PostMapping("/setViewName")
    public ModelAndView setViewName (String viewName, @RequestParam Long id)
    {
        gameService.setViewName(id,viewName);
        return new ModelAndView("redirect:/game/"+id+"/hand");
    }

    @PostMapping("/{id}/goBack")
    public ModelAndView goBack (@PathVariable Long id){
        gameService.goBack(id);
        return new ModelAndView("redirect:/game/"+id+"/hand");
    }

    @DeleteMapping("/delete")
    public ModelAndView deleteById(@RequestParam Long id){
        gameService.deleteById(id);
        return new ModelAndView("redirect:/game/getAll");
    }

}
