package com.algaworks.algafood.core.validation;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    private BigDecimal valorValidado;
    private String nomeValidado;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraint) {
        this.valorField = constraint.valorField();
        this.descricaoField = constraint.descricaoField();
        this.descricaoObrigatoria = constraint.descricaoObrigatoria();
    }


    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
        boolean valido = false;
        Method[] methods = objetoValidacao.getClass().getDeclaredMethods();
        try {
            var descricao = objetoValidacao.getClass().getAnnotation(ValorZeroIncluiDescricao.class)
                    .descricaoField();

            for (Method method : methods) {
                if (method.getName().equals("getTaxaFrete")) {
                    valorValidado = (BigDecimal) method.invoke(objetoValidacao);
                }
                if (method.getName().equals("getNome")) {
                    nomeValidado = (String) method.invoke(objetoValidacao);
                }
            }

            if(valorValidado != null && BigDecimal.ZERO.compareTo(valorValidado) == 0 && nomeValidado != null){
                valido = nomeValidado.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }
            return valido;

        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }


/*

// Minha implementação com reflection
    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
        boolean valido = true;
        Method[] metodos = objetoValidacao.getClass().getDeclaredMethods();
        try {
            String descricao = objetoValidacao.getClass().getAnnotation(ValorZeroIncluiDescricao.class).descricaoField();

            for (Method method : metodos) {
                if (method.getName().equals("getTaxaFrete")) {
                    valorValidado = (BigDecimal) method.invoke(objetoValidacao);
                }
                if (method.getName().equals("getNome")) {
                    nomeValidado = (String) method.invoke(objetoValidacao);
                }
            }
            if (valorValidado != null && BigDecimal.ZERO.compareTo(valorValidado) == 0 && nomeValidado != null) {
                valido = nomeValidado.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }
            return valido;

        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
*/

}
