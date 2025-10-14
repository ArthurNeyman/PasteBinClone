package com.paste_bin_clone.config;

import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.collection.spi.PersistentBag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MapperConfig {

    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configureUserMapping(modelMapper);
        return modelMapper;
    }

    private void configureUserMapping(ModelMapper modelMapper) {
        modelMapper.addConverter((Converter<PersistentBag, List>) context -> {
            if (context.getSource() == null) {
                return null;
            }
            return new ArrayList<>(context.getSource());
        });
        modelMapper
            .typeMap(UserEntity.class, UserDTO.class)
            .addMappings(mapper -> {
                mapper.skip(UserDTO::setPassword);
            });
        modelMapper
            .typeMap(UserDTO.class, UserEntity.class)
            .addMappings(
                mapper ->{
                    mapper.using(
                        (Converter<String, String>) context ->
                            context.getSource() == null ? null : passwordEncoder.encode(context.getSource())
                    ).map(UserDTO::getPassword, UserEntity::setPassword);
            });
    }
}