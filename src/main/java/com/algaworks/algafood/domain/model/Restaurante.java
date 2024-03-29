package com.algaworks.algafood.domain.model;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


//@ValorZeroIncluiDescricao
//        (valorField = "taxaFrete",message = "Mensagem padrao",descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)

    private String nome;

    @Column(nullable = false)

    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false,columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
        joinColumns = @JoinColumn(name = "restaurante_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    private Boolean ativo = Boolean.TRUE;

    private Boolean aberto = Boolean.FALSE;
    public void ativar(){
        setAtivo(true);
    }
    public void inativar(){
        setAtivo(false);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento){
        return formasPagamento.add(formaPagamento);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento){
        return formasPagamento.remove(formaPagamento);
    }

    public boolean adicionarResponsavel(Usuario usuario){
        return getResponsaveis().add(usuario);
    }
    public boolean removerResponsavel(Usuario usuario){
        return getResponsaveis().remove(usuario);
    }

    public void abrir(){
        setAtivo(true);
    }
    public void fechar(){
        setAberto(false);
    }


    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento){
        return getFormasPagamento().contains(formaPagamento);
    }
    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento){
        return !getFormasPagamento().contains(formaPagamento);
    }


}
