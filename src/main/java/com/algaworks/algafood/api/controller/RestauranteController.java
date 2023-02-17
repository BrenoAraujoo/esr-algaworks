package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.restaurante.RestauranteAssembler;
import com.algaworks.algafood.api.assembler.restaurante.RestauranteDisassembler;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.api.model.dtoinput.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.*;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurantes",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {
    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteAssembler restauranteAssembler;

    @Autowired
    private RestauranteDisassembler restauranteDisassembler;

    @GetMapping
    public List<RestauranteDTO> listar() {
        return restauranteAssembler.toCollectionDTO(restauranteRepository.findAll());
    }


    @GetMapping("/{id}")
    public RestauranteDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);
        return restauranteAssembler.toDTO(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            var restaurante = restauranteDisassembler.toDomainObject(restauranteInput);
            return restauranteAssembler.toDTO(restauranteService.salvar(restaurante));

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
        var restauranteAtual = restauranteService.buscarOuFalhar(id);

        restauranteDisassembler.copyFromInputToDomainObject(restauranteInput, restauranteAtual);
        try {
            return restauranteAssembler.toDTO(restauranteService.salvar(restauranteAtual));

        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        restauranteService.remover(id);

    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RestauranteDTO ativar(@PathVariable Long id) {
        var restauranteAtul = restauranteService.buscarOuFalhar(id);
        restauranteService.inativar(id);
        return restauranteAssembler.toDTO(restauranteAtul);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds){
        try {
            restauranteService.ativar(restaurantesIds);
        }catch (RestauranteNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(),ex);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds){
        try {
            restauranteService.inativar(restaurantesIds);
        }catch (RestauranteNaoEncontradoException ex){
            throw new NegocioException(ex.getMessage(),ex);
        }
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RestauranteDTO inativar(@PathVariable Long id) {
        var restauranteAtual = restauranteService.buscarOuFalhar(id);
        restauranteService.inativar(id);
        return restauranteAssembler.toDTO(restauranteAtual);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
    }


}
