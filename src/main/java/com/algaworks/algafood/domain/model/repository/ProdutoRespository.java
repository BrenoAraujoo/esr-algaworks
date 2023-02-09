package com.algaworks.algafood.domain.model.repository;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRespository extends JpaRepository<Produto, Long> {


    @Query("from Produto p where restaurante.id = :restaurante and id = :produto")
    Optional<Produto> findById(@Param("produto") Long produtoId, @Param("restaurante") Long restauranteId);

    List<Produto> findByRestaurante(Restaurante restaurante);
}
