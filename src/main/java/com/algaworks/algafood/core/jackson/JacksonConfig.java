package com.algaworks.algafood.core.jackson;


import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper(){
        JsonMapper mapper = new JsonMapper();
        JsonMapper.builder().configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        mapper.registerModule(new JavaTimeModule());

        return mapper;

    }




}
