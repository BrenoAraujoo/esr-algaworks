package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.repository.CidadeRepository;
import com.algaworks.algafood.domain.model.repository.CidadeRepositoryQueries;
import com.algaworks.algafood.infrastructure.repository.spec.CidadeSpec;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class CidadeRepositoryImpl implements CidadeRepositoryQueries {

    @Autowired @Lazy
    private CidadeRepository cidadeRepository;

    @Override
    public List<Cidade> cidadesEmSP(String estado) {
        return cidadeRepository.findAll(CidadeSpec.cidadesEmSP(estado));
    }
}
