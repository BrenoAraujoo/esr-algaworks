package com.algaworks.algafood.domain.model.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException{


    private static final long serialVersionUID = -2499185889411525319L;

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradaException(Long id) {
    this(String.format("Forma de pagamento com id %d não encontrada",id));
    }
}
