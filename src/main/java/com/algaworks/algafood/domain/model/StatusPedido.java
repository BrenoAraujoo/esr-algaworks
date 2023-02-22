package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public enum StatusPedido {

    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    ENTREGUE("Entregue", CONFIRMADO),
    CANCELADO("Cancelado", CRIADO);

    private final String descricao;
    private final List<StatusPedido> statusAnteriores;
    StatusPedido (String descricao, StatusPedido... statusAnteriores){
    this.descricao  = descricao;
    this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    /*
    A instancia do status atual não pode ser transitada para o novo status?
    Se o Status atual é CONFIRMADO, o novo status deve ser o ENTREGUE ou CANCELADO, pois eles contem o CONFIRMADO.
    .contains(this) -> refere-se ao status da instancia que chamou o método (status atual).
     */
    public boolean naoPodeAlterarPara(StatusPedido novoStatus){
        return !novoStatus.getStatusAnteriores().contains(this);
    }


}
