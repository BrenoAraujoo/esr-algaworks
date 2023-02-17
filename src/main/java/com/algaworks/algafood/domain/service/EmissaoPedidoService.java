package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.PedidoNaoEncontradaException;
import com.algaworks.algafood.domain.model.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    private final String MSG_PEDIDO_EM_USO = "Pedido com id %d está em uso e não pode ser removido";


    @Autowired
    private PedidoRepository pedidoRepository;


    public Pedido salvar(Pedido pedido){
     return pedidoRepository.save(pedido);
    }

    public void remover(Long id){
        try {
            pedidoRepository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new EntidadeEmUsoException(String.format(MSG_PEDIDO_EM_USO,id));
        }catch (EmptyResultDataAccessException ex){
            throw new PedidoNaoEncontradaException(id);
        }
    }

    public Pedido buscarOuFalhar(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradaException(id));
    }
}
