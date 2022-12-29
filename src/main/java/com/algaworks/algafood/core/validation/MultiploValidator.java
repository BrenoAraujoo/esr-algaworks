package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
//Classe para definir a lógica de validação
public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    //Variável de instancia recebendo o valor passado na anotação, pois vamos precisar desse valor para ser usado no método isValid.
    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean valido = true;


        if(value != null){
            var valorDecimal = BigDecimal.valueOf(value.doubleValue()); // Valor do atributo que vai ser validado na entidade convertido em BigDecimal
            var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);// Valor definido na antoação
            var resto = valorDecimal.remainder(multiploDecimal); // Resto de divisão
            valido = BigDecimal.ZERO.compareTo(resto) == 0; // boolean valido recebe true ou false de acordo com essa expressão.
        }

        return valido;
    }
}
