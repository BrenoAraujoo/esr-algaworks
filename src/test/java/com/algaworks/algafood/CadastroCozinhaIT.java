package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        basePath = "/cozinhas";
        String pathParam = "/{cozinhaId}";
        databaseCleaner.clearTables();
        prepararDados();
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
    public void deveContar2Cozinhas_QuandoConsultarCozinhas(){
                when()
                        .get()
                .then()
                    .statusCode(HttpStatus.OK.value())
                        .body("",hasSize(2))
                        .body("nome",hasItems("Indiana","Francesa"));

    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha(){
        given()
                    .body("{\"nome\": \"Nova cozinha\"}")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente(){

        given()
                .pathParam("cozinhaId", 1)
                .accept(ContentType.JSON)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.OK.value()) // Validação de status
                    .body("nome", equalTo("Indiana")); // Validação de corpo

    }
    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente(){
        given()
                .pathParam("cozinhaId",100)
                .accept(ContentType.JSON)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    public void prepararDados(){
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Indiana");
        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Francesa");
        List<Cozinha> cozinaList = Arrays.asList(cozinha1,cozinha2);

        cozinhaRepository.saveAll(cozinaList);
    }
}
