package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Restaurante;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;

public class RestauranteSpecs {

    private static final long serialVersionUID = 2341539938045162706L;

    public static Specification<Restaurante> comFreteGratis() {
            return (root, query, builder) ->
            builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return (root, query, builder) ->
                builder.like(root.get("nome"), "%" + nome + "%");

    }
}

