package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Cidade;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CidadeSpec implements Specification<Cidade> {


    private static final long serialVersionUID = -2029559533258578545L;

    public static Specification<Cidade> cidadesEmSP(String estado){
        return new Specification<Cidade>() {
            @Override
            public Predicate toPredicate(Root<Cidade> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get("estado").get("nome"),estado);
            }
        };
    }
    @Override
    public Predicate toPredicate(Root<Cidade> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return null;
    }
}
