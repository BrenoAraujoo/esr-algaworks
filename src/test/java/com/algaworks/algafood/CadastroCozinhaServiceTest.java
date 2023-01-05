package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

import javax.validation.ConstraintViolationException;
import org.hibernate.mapping.Constraint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CadastroCozinhaServiceTest {


    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Test
    public void deveFalhar_QuandoNaoEncontrarCozinha() {
        CozinhaNaoEncontradaException erroEsperado = Assertions.assertThrows
                (CozinhaNaoEncontradaException.class, () -> cozinhaService.buscarOuFalhar(1L));
    }

    @Test
    public void deveFalhar_QuandoCadastroCozinhaSemNome() {
        //Cenário
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(null);
        //Ação
        ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class,
                () -> cozinhaService.salvar(cozinha));
        //Validação

    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
        EntidadeEmUsoException erroEsperado =
                Assertions.assertThrows(EntidadeEmUsoException.class,
                        () -> cozinhaService.remover(1L));
        assertThat(erroEsperado).isNotNull();
        System.out.println("Erro -> " + erroEsperado.getClass().getSimpleName());
    }


    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {

        EntidadeNaoEncontradaException erroEsperado = Assertions.assertThrows(EntidadeNaoEncontradaException.class,
                () -> cozinhaService.remover(100L));
        assertThat(erroEsperado).isNotNull();
        System.out.println("Erro ->" + erroEsperado.getClass().getSimpleName());

    }

    @Test
    public void testarCadastroCozinhaSemNome() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(null);
        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () -> {
                    cozinhaService.salvar(cozinha);
                });
        assertThat(erroEsperado).isNotNull();
    }
}
