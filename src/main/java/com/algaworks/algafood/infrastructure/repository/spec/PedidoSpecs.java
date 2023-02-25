package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoFilter;
import java.util.ArrayList;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class PedidoSpecs {


    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
        return (root, query, builder) -> {
            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");

        var predicates = new ArrayList<Predicate>();

        if(filtro.getClienteId() != null){
            predicates.add(builder.equal(root.get("cliente"),filtro.getClienteId()));
        }

        if(filtro.getRestauranteId() !=null){
            predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
        }
        if(filtro.getDataCriacaoFim() != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
        }

        if(filtro.getDataCriacaoFim() != null){
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }
        return builder.and(predicates.toArray(new Predicate[0])); // converte o arraylist para um vetor de predicates

        };
    }

}
