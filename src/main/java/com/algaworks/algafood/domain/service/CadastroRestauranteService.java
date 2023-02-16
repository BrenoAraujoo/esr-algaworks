package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {
    private static final String MSG_RESTAURANTE_EM_USO = "Restaurante com id %d está em uso";
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CadastroCidadeService cidadeService;

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

//        restaurante.setCozinha(cozinha);
        BeanUtils.copyProperties(cozinha,restaurante.getCozinha());
        BeanUtils.copyProperties(cidade,restaurante.getEndereco().getCidade());
        return restauranteRepository.save(restaurante);
    }




    @Transactional
    public void remover(Long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_RESTAURANTE_EM_USO, id)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(id);
        }
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        var restaurante = buscarOuFalhar(restauranteId);
        var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        var restaurante = buscarOuFalhar(restauranteId);
        var formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id).orElseThrow(()->new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public void ativar(Long id){
        var restauranteAtual = buscarOuFalhar(id);
        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long id){
        var restauranteAtual = buscarOuFalhar(id);
        restauranteAtual.inativar();
    }

    @Transactional
    public void fechar(Long id){
        var restauranteAtual = buscarOuFalhar(id);
        restauranteAtual.fechar();
    }

    @Transactional
    public void abrir(Long id){
        var restauranteAtual = buscarOuFalhar(id);
        restauranteAtual.abrir();
    }
}
