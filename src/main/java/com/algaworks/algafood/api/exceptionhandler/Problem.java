package com.algaworks.algafood.api.exceptionhandler;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class Problem {

    private Integer status;
    private String type;
    private String title;
    private String detail;

    private String userMessage;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime timestamp;

    private List<Field> fields;


    @Getter
    @Builder
    public static class Field{
        private String name;
        private String userMessage;
    }

}
