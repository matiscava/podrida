package com.example.podrida.service;

import java.util.List;

public interface IBaseService <T,Y>{
    List<T> getAll();
    T create(Y object);
    T getById(Long id);
    T update(Y object);
    void deleteById(Long id);
}
