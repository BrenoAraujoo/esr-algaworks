package com.algaworks.algafood.domain.model.repository;


import com.algaworks.algafood.domain.model.Restaurante;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

    //Essa Query  nao retorna a lista ordenada ordenado ...
//    @Query("Select distinct r from Restaurante r join fetch " +
//            "r.cozinha left join fetch r.formasPagamento ")
//    List<Restaurante> findAll();



}
