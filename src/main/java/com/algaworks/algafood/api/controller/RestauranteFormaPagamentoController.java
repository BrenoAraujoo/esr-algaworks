package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.formaPagamento.FormaPagamentoAssembler;
import com.algaworks.algafood.api.model.dto.FormaPagamentoDTO;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController {
    @Autowired
    private CadastroRestauranteService restauranteService;
    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;


    @GetMapping
    public List<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {

        var restaurante = restauranteService.buscarOuFalhar(restauranteId);
        var formasPagamento = restaurante.getFormasPagamento();

        return formaPagamentoAssembler.toCollectionDTO(formasPagamento);

    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        restauranteService.desassociarFormaPagamento(restauranteId,formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        restauranteService.associarFormaPagamento(restauranteId,formaPagamentoId);
    }




}
