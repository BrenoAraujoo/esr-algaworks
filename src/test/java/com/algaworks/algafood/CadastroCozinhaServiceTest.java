package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CadastroCozinhaServiceTest {


    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Test
    public void testarCadastroCozinhaComsucesso() {
        //Cenário
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Chinesa");
        //Ação
        cozinha = cozinhaService.salvar(cozinha);
        //Validação
        assertThat(cozinha).isNotNull();
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
