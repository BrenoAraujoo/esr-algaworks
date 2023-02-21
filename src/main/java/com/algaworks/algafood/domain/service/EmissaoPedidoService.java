package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.exception.PedidoNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {

    private final String MSG_PEDIDO_EM_USO = "Pedido com id %d está em uso e não pode ser removido";


    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService restauranteService;
    @Autowired
    private CadastroUsuarioService clienteService;

    @Autowired
    private CadastroProdutoService produtoService;
    @Autowired
    private CadastroCidadeService cidadeService;
    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Transactional
    public Pedido emitir(Pedido pedido) {

        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }

    public void remover(Long id) {
        try {
            pedidoRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException(String.format(MSG_PEDIDO_EM_USO, id));
        } catch (EmptyResultDataAccessException ex) {
            throw new PedidoNaoEncontradaException(id);
        }
    }

    public void validarPedido(Pedido pedido) {

        var restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        var cliente = clienteService.buscarOuFalhar(pedido.getCliente().getId());
        var formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
        var cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }

    }

    public void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {

            Long restauranteId = pedido.getRestaurante().getId();
            Long produtoId = item.getProduto().getId();

            var produto = produtoService.buscarOuFalhar(
                    produtoId, restauranteId);


            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }



    public Pedido buscarOuFalhar(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradaException(id));
    }


}
