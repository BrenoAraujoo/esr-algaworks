package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.api.Groups;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.ConvertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome da cidade não pode ser vazio")
    private String nome;

    @Valid
    @ConvertGroup(to = Groups.EstadoId.class)
    @ManyToOne
    private Estado estado;

}
