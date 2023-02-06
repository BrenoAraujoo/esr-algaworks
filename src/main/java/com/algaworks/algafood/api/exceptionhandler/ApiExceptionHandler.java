package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.model.exception.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String MSG_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. "
            + "Tente novamente e se o problema persistir, entre em contato "
            + "com o administrador do sistema.";

    @Autowired
    private MessageSource messageSource;

    //Trata todas as exceções não tratadas, de forma genérica.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_MENSAGEM_ILEGIVEL;
        String detail = MSG_GENERICA_USUARIO_FINAL;
        ex.printStackTrace();

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(MSG_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(SenhaAtualIncorretaException.class)
    public ResponseEntity<Object> handleSenhaIncorreta(SenhaAtualIncorretaException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_SENHA_INCORRETA;

        Problem problem = createProblemBuilder(status,problemType,ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex,problem,new HttpHeaders(),status,request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);
//        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.ERRO_MENSAGEM_ILEGIVEL;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(MSG_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch
            (TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch
                    ((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada
            (EntidadeNaoEncontradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = ex.getMessage();
        ProblemType problemType = ProblemType.ERRO_RECURSO_NAO_ENCONTRADO;

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),
                status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),
                status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal
            (Exception ex, Object body, HttpHeaders headers,
             HttpStatus status, WebRequest request) {
        // se o body é null, usamos no titulo da mensagem o ReasonPhrase do status
        if (body == null) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .userMessage(MSG_GENERICA_USUARIO_FINAL)
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .userMessage(MSG_GENERICA_USUARIO_FINAL)
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);

    }


    private ResponseEntity<Object> handleInvalidFormat
            (InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        //Implementação Algaworks
        //String path = joinPath(ex.getPath());
        String path = joinPath(ex);

        ProblemType problemType = ProblemType.ERRO_MENSAGEM_ILEGIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é do tipo inválido" +
                ". Corrija e informe um valor compatível com o tipo %s.", path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(MSG_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding
            (PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        //Implementação Algaworks
        //String path = joinPath(ex.getPath());

        String path = joinPath(ex);
        ProblemType problemType = ProblemType.ERRO_MENSAGEM_ILEGIVEL;
        String detail = String.format("A propriedade '%s' não existe. Corrija ou remova e tente novamente", path);
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(MSG_GENERICA_USUARIO_FINAL)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

//    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        String path = ex.getPropertyName();
//        ProblemType problemType = ProblemType.ERRO_PARAMETRO_INVALIDO;
//        String detail = String.format("O parâmetro de URL '%s' recebeu um valor '%s', que é de um tipo inválido" +
//                ". Corrija e informe um valor compatível com '%s'.", path, ex.getValue(),ex.getRequiredType().getSimpleName());
//        Problem problem = createProblemBuilder(status,problemType,detail).build();
//        return handleExceptionInternal(ex, problem, headers, status, request);
//    }

    //     Criei o método joinPath para reaproveitar em todos os métodos que precisam concatenar os nomes das propriedades (separando por ".")
    //Implementação Algaworks

//    private String joinPath(List<Reference> references) { // o ex.getPath() retorna um List<Reference>
//        return references
//                .stream()
//                .map(ref -> ref.getFieldName())
//                .collect(Collectors.joining("."));
//    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException
            (NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.ERRO_RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.", ex.getRequestURL());
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(MSG_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch
            (MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


        ProblemType problemType = ProblemType.ERRO_PARAMETRO_INVALIDO;
        String detail = String.format("O parâmetro de URL '%s' recebeu um valor '%s' que é de um tipo inválido" +
                "Corrija e informe um valor compatível com o tipo %s.", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();

        List<Problem.Field> problemFields = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError ->{
                        String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                        return Problem.Field.builder()
                        .name(fieldError.getField())
                        .userMessage(message)
                        .build();
                        })
                .toList();

        String fieldErrors = joinField(problemFields);

        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        ProblemType problemType = ProblemType.ERRO_DADOS_INVALIDOS;

        Problem problem = createProblemBuilder(status, problemType, detail)
                .fields(problemFields)
                .userMessage("Um ou mais campos estão inválidos." +
                        " Por favor, corrija e tente novamente")
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    //Minha implementação
    private String joinPath(JsonMappingException ex) {

        var references = ex.getPath();
        return references
                .stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    private String joinField(List<Problem.Field> list){
        return list.stream()
                .map(Problem.Field::getName)
                .collect(Collectors.joining(" e "));

    }

    private Problem.ProblemBuilder createProblemBuilder
            (HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .title(problemType.getTitle())
                .type(problemType.getUri())
                .detail(detail);
    }

}
