package com.good_three.services;

import org.springframework.stereotype.Service;

@Service
public interface IMapperService {

    <T> Object toEntity(T model);

    <T> Object toDTO(T entity);
}
