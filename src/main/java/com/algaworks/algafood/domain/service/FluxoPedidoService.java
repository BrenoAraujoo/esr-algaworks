package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirmarPedido(String pedidoCodigo) {
        var pedido = emissaoPedidoService.buscarOuFalhar(pedidoCodigo);
        pedido.confirmar();
    }

    @Transactional
    public void entregarPedido(String pedidoCodigo) {
        var pedido = emissaoPedidoService.buscarOuFalhar(pedidoCodigo);
        pedido.entregar();
    }

    @Transactional
    public void cancelarPedido(String pedidoCodigo) {
        var pedido = emissaoPedidoService.buscarOuFalhar(pedidoCodigo);
        pedido.cancelar();
    }



}
