package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.api.Groups;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.internal.engine.groups.Group;


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
