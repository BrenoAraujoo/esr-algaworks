package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    private final String MSG_STATUS_INCORRETO = "Status dos pedido %d não pode ser alterado de '%s' para '%s'";
    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirmarPedido(Long pedidoId) {
        var pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        var statusPedidoAtual = emissaoPedidoService.buscarOuFalhar(pedidoId).getStatus();
        if (!statusPedidoAtual.equals(StatusPedido.CRIADO)) {
            throw new NegocioException(String.format(
                    MSG_STATUS_INCORRETO, pedidoId, statusPedidoAtual.getDescricao(), StatusPedido.CONFIRMADO.getDescricao()));
        }
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Transactional
    public void entregarPedido(Long pedidoId) {
        var pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        var statusPedidoAtual = pedido.getStatus();
        if (!statusPedidoAtual.equals(StatusPedido.CONFIRMADO)) {
            throw new NegocioException(
                    String.format(MSG_STATUS_INCORRETO, pedidoId, statusPedidoAtual.getDescricao(), StatusPedido.ENTREGUE.getDescricao()));
        }
        pedido.setStatus(StatusPedido.ENTREGUE);
    }

    @Transactional
    public void cancelarPedido(Long pedidoId) {
        var pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        var statusPedidoAtual = pedido.getStatus();
        if (!statusPedidoAtual.equals(StatusPedido.CRIADO)) {
            throw new NegocioException(String.format(
                    MSG_STATUS_INCORRETO, pedidoId, statusPedidoAtual.getDescricao(), StatusPedido.CANCELADO.getDescricao()));
        }
        pedido.setStatus(StatusPedido.CANCELADO);
    }
}
