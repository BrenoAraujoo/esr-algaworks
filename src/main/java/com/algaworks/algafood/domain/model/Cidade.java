package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.api.Groups;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Groups.CidadeId.class)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @Valid
    @ConvertGroup(to = Groups.EstadoId.class)
    @ManyToOne
    private Estado estado;

}
