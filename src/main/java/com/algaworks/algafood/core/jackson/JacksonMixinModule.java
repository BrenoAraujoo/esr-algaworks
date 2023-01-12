package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.model.mixin.RestauranteMixin;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.Serial;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    @Serial
    private static final long serialVersionUID = 7197240732870772798L;

    public JacksonMixinModule(){
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }
}
