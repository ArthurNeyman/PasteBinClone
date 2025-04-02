package com.paste_bin_clone.services;

import org.springframework.stereotype.Service;

@Service
@Deprecated
public interface IMapperService {

    <T, S> S toEntity(T model, Class<S> toEntityClass);

    <T> Object toDTO(T entity);
}
