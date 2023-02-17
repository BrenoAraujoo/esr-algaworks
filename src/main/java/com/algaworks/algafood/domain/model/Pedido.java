package com.algaworks.algafood.domain.model;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(STRING)
    private StatusPedido status = CRIADO;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataCancelamento;

    private OffsetDateTime dataEntrega;


    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        this.subtotal =
                getItens()
                        .stream()
                        .map(ItemPedido::getPrecoTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void definirFrete(){
        setTaxaFrete( restaurante.getTaxaFrete());
    }
    public void atribuirPedidoAosItens(){
        this.itens.forEach(item -> item.setPedido(this)); // Algaworks
//        this.itens.forEach(item -> item.setPedido(item.getPedido()));
    }

}
