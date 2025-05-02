package com.paste_bin_clone.config;

import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.entities.UserEntity;
import org.hibernate.collection.internal.PersistentBag;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper getMapper() {

        ModelMapper modelMapper = new ModelMapper();

        configureUserMapping(modelMapper);

        return modelMapper;
    }

    private void configureUserMapping(ModelMapper modelMapper) {
        modelMapper.addConverter(new AbstractConverter<PersistentBag, List>() {
            @Override
            protected List convert(PersistentBag source) {
                return source == null ? null : new ArrayList<>(source);
            }
        });
        modelMapper
                .typeMap(UserEntity.class, UserDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(UserDTO::setPassword);
//                    mapper.skip(UserDTO::setUserId);
                });
    }
}