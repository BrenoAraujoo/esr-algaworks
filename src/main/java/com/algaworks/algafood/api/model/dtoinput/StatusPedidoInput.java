package com.algaworks.algafood.api.model.dtoinput;

import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusPedidoInput {

    @NotNull
    private String status;
}
