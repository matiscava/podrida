package com.example.podrida.controller;

import com.example.podrida.service.IMistakesMadeService;
import com.example.podrida.service.MistakesMadeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mistakesMade")
public class MistakesMadeController {
    private final IMistakesMadeService mistakesMadeService;
    public MistakesMadeController (MistakesMadeService mistakesMadeService){
        this.mistakesMadeService = mistakesMadeService;
    }
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteById(@PathVariable Long id) {
        long gameId = mistakesMadeService.getGameId(id);
        mistakesMadeService.deleteById(id);
        return new ModelAndView("redirect:/game/"+gameId+"/mistakeList");
    }

}
