package com.algaworks.algafood.api.disassembler;


import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDisassembler {

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        var restaurante = new Restaurante();
        var cozinha = new Cozinha();

        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        return restaurante;
    }
}
