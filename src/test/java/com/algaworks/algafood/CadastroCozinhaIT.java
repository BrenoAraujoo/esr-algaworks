package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Restaurante;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import java.net.http.WebSocketHandshakeException;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.restart.RestartApplicationListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;
    @BeforeEach
    public void setUp(){
        enableLoggingOfRequestAndResponseIfValidationFails();

       enableLoggingOfRequestAndResponseIfValidationFails();
       RestAssured.port = port;
        basePath = "/cozinhas";
        flyway.migrate();
    }
    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
            given()
                    .port(port)
                    .basePath(basePath)
                    .accept(ContentType.JSON)
                    .when()
                        .get()
                    .then()
                        .statusCode(HttpStatus.OK.value());
    }
    @Test
    public void deveContar5Cozinhas_QuandoConsultarCozinhas(){
                when()
                        .get()
                .then()
                    .statusCode(HttpStatus.OK.value())
                        .body("",hasSize(5))
                        .body("nome",hasItems("Indiana","Francesa"));

    }

    @Test
    public void testRetornarStatus201_QuandoCadastrarCozinha(){
        given()
                    .body("{\"nome\": \"Nova cozinha\"}")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }
}
