package com.algaworks.algafood.domain.model;


import com.algaworks.algafood.domain.model.exception.NegocioException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import static com.algaworks.algafood.domain.model.StatusPedido.CRIADO;
import static javax.persistence.EnumType.STRING;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {
    public static final String MSG_STATUS_INVALIDO = "O status do pedido %s não pode ser alterado de %s pra %s";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(STRING)
    private StatusPedido status = CRIADO;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;


    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {

        getItens().forEach(ItemPedido::calcularPrecoTotal);  //Calcula o preço total do ItemPedido

        this.subtotal = getItens()                          //Sub total recebe a soma do precoTotal de cada Item de pedido
                .stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);        //Valor total recebe o subtotal + taxafrete
    }

    public void confirmar(){
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
    }

    public void entregar(){
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar(){
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

    public void setStatus(StatusPedido novoStatus){
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(String.format(MSG_STATUS_INVALIDO,
                    getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao()));
        }
        this.status = novoStatus;
    }

    @PrePersist
    private void gerarCodigo(){
        setCodigo(UUID.randomUUID().toString());
    }


}
