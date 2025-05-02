package com.paste_bin_clone.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommonService {

    @Autowired
    protected ModelMapper modelMapper;


    public <T, S> S convertTo(T fromObject, Class<S> toObject) {
        return fromObject != null ? modelMapper.map(fromObject, toObject) : null;
    }

    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }
}
