package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.dto.CozinhaDTO;
import com.algaworks.algafood.api.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public List<RestauranteDTO> listar() {
            return toCollectionDTO(restauranteRepository.findAll());
    }


    @GetMapping("/{id}")
    public RestauranteDTO buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);
        return toModel(restaurante);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid Restaurante restaurante) {
        try {
            return toModel(restauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(),e);
        }

    }

    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid Restaurante restaurante) {
        Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        try {
            return toModel(restauranteService.salvar(restauranteAtual));

        }catch (CozinhaNaoEncontradaException  e){
            throw new NegocioException(e.getMessage(), e);
        }

}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
            restauranteService.remover(id);

    }

    private RestauranteDTO toModel(Restaurante restaurante) {
        RestauranteDTO restauranteDTO = new RestauranteDTO();
        CozinhaDTO cozinhaDTO = new CozinhaDTO();

        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setCozinha(cozinhaDTO);
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());

        return restauranteDTO;
    }

    private List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModel(restaurante))
                .toList();
    }


}
