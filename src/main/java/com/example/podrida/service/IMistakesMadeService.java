package com.example.podrida.service;

import com.example.podrida.dto.mistakesMade.MistakesMadeDtoReq;
import com.example.podrida.dto.mistakesMade.MistakesMadeDtoRes;

public interface IMistakesMadeService extends IBaseService<MistakesMadeDtoRes, MistakesMadeDtoReq>{
    Long getGameId(long id);
}
