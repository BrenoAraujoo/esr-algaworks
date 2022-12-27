package com.algaworks.algafood.core.validation;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.PositiveOrZero;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@PositiveOrZero
public @interface TaxaFrete {

    String message() default "{javax.validation.constraints.PositiveOrZero.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };


}
