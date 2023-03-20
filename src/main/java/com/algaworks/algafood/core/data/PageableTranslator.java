package com.algaworks.algafood.core.data;

import com.algaworks.algafood.domain.model.exception.NegocioException;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {

    private final static String MSG_FILTRO_INVALIDO = "O filtro '%s' é inválido.";

    public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {

        var orders = pageable.getSort()
                .stream()
                .filter(order -> {
                            if (!fieldsMapping.containsKey(order.getProperty()))
                                throw new NegocioException(String.format(MSG_FILTRO_INVALIDO, order.getProperty()));
                            return true;
                        }
                )
                .map(order -> new Sort.Order(order.getDirection(), fieldsMapping.get(order.getProperty())))
                .toList();

        return PageRequest.of
                (pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));

    }
}
