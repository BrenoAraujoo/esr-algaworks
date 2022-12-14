package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    ERRO_RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO ("/entidade-em-uso","Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio","Violação de regra de negócio"),
    ERRO_MENSAGEM_ILEGIVEL("/erro-mensagem-ilegivel","Mensagem ilegivel"),
    ERRO_PARAMETRO_INVALIDO("/erro-parametro-invalido","Parâmetro inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema","Erro no Sistema" ),
    DADOS_INVALIDOS("/dados-invalidos","Dados inválidos");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.title = title;
        this.uri = "https://algafood.com.br" + path;
    }
}
