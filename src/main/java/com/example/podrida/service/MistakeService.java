package com.example.podrida.service;

import com.example.podrida.dto.mistake.MistakeDtoReq;
import com.example.podrida.dto.mistake.MistakeDtoRes;
import com.example.podrida.entity.Mistake;
import com.example.podrida.exception.IdNotFoundException;
import com.example.podrida.mapper.MistakeMapper;
import com.example.podrida.repository.IMistakeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class MistakeService implements IMistakeService{
    final private IMistakeRepository mistakeRepository;
    public MistakeService (IMistakeRepository mistakeRepository){
        this.mistakeRepository = mistakeRepository;
    }
    @Override
    public List<MistakeDtoRes> getAll() {
        List<Mistake> mistakeList = mistakeRepository.findAll();
        List<MistakeDtoRes> mistakeDtoList = new ArrayList<>();
        mistakeList.forEach(
                m-> mistakeDtoList.add(MistakeMapper.convertEntityToResDto(m))
        );
        return mistakeDtoList;
    }

    @Override
    public MistakeDtoRes create(MistakeDtoReq mistakeDto) {
        Mistake m = MistakeMapper.convertReqDtoToEntity(mistakeDto);
        Mistake mSaved = mistakeRepository.save(m);
        return MistakeMapper.convertEntityToResDto(mSaved);
    }

    @Override
    public MistakeDtoRes getById(Long id) {
        Optional<Mistake> m = mistakeRepository.findById(id);
        if(m.isEmpty()) throw new IdNotFoundException("No existe el mistake ID: "+id+", verifique sus datos.");
        return MistakeMapper.convertEntityToResDto(m.get());
    }

    @Override
    public MistakeDtoRes update(MistakeDtoReq object) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Mistake> m = mistakeRepository.findById(id);
        if(m.isPresent()) throw new IdNotFoundException("No existe el mistake ID: "+id+", verifique sus datos.");
        mistakeRepository.deleteById(id);
    }
}
