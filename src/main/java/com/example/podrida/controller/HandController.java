package com.example.podrida.controller;

import com.example.podrida.dto.game.GameDtoRes;
import com.example.podrida.dto.hand.HandDtoReq;
import com.example.podrida.dto.hand.HandDtoRes;
import com.example.podrida.dto.hand.HandDtoUpdate;
import com.example.podrida.dto.hand.HandSetTakeCardsDto;
import com.example.podrida.service.*;
import com.example.podrida.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hand")
public class HandController {
    private final IHandService handService;
    private final IGameService gameService;

    public HandController(HandService handService, GameService gameService){
        this.handService = handService;
        this.gameService = gameService;

    }
    @PostMapping("/new")
    public ModelAndView create(@ModelAttribute HandDtoReq hDto){
        handService.create(hDto);
        GameDtoRes gameDtoRes = gameService.getById(hDto.getGameId());
        if(gameDtoRes.getNextPlayer() == 0) gameService.setViewName(hDto.getGameId(), "endPredict");
        return new ModelAndView("redirect:/game/"+hDto.getGameId()+"/hand");
    }
    @PostMapping("/take")
    public ModelAndView setTake(@ModelAttribute HandSetTakeCardsDto handDto){
        GameDtoRes gameDtoRes = handService.setTakeCard(handDto);
        if(gameDtoRes.getNextPlayer() == 0) return new ModelAndView("redirect:/hand/setPoints?id=" + gameDtoRes.getId());
        gameService.setViewName(handDto.getGameId(), "taken");
        return new ModelAndView("redirect:/game/"+handDto.getGameId()+"/hand");
    }
    @GetMapping("/setPoints")
    public ModelAndView getPoints(
            @RequestParam Long id
    ){
        handService.getPoints(id);
        gameService.setViewName(id, "endTaken");
        return new ModelAndView("redirect:/game/"+id+"/hand");
    }
    @GetMapping("/edit/{id}")
    public ModelAndView getUpdateHand( @PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("handUpdate");
        HandDtoRes hDto = handService.getById(id);

        modelAndView.addObject("hand", hDto);
        modelAndView.addObject("handDto", new HandDtoUpdate());
        modelAndView.addObject("gameDto", gameService.getViewNameDto(id));
        modelAndView.addObject("cardLimit", Utils.getLimitCard(hDto.getHandNumber()));
        return modelAndView;
    }
    @PostMapping("/update")
    public ModelAndView updateHand(@ModelAttribute HandDtoUpdate hDto){
        Long gameId = handService.updateHand(hDto);
        return new ModelAndView("redirect:/game/"+gameId);
    }
}
