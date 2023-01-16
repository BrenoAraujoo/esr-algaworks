package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteAssembler;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
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
            var restaurante = toDomainObject(restauranteInput);
            return restauranteAssembler.toDTO(restauranteService.salvar(restaurante));

        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
        Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);
        Restaurante restaurante = toDomainObject(restauranteInput);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        try {
            return restauranteAssembler.toDTO(restauranteService.salvar(restauranteAtual));

        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        restauranteService.remover(id);

    }


    private Restaurante toDomainObject(RestauranteInput restauranteInput) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);
        return restaurante;
    }

}
