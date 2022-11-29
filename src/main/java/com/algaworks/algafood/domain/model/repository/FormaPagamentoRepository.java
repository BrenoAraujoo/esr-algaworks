package com.algaworks.algafood.domain.model.repository;

import com.algaworks.algafood.domain.model.FormaPagamento;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
