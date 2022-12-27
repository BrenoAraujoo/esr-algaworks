package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import java.math.BigDecimal;
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
public class Produto {

    @NotNull(groups = Groups.ProdutoId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;


    private String descricao;
    @Column(nullable = false)
    private BigDecimal preco;
    @Column(nullable = false)
    private Boolean ativo;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    @Valid
    @ConvertGroup(to = Groups.RestauranteId.class)
    private Restaurante restaurante;
}
