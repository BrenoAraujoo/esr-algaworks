package com.algaworks.algafood.domain.model.repository;

import com.algaworks.algafood.domain.model.Cidade;
import java.util.List;

public interface CidadeRepositoryQueries {

    List<Cidade> cidadesEmSP(String estado);
}
