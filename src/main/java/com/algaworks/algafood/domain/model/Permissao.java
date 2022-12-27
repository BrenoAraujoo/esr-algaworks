package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups= Groups.PermissaoId.class)
    private Long id;

    @Column
    @NotBlank
    private String nome;

    @Column
    private String descricao;

}
