package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Arrays;
import java.util.List;
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

    private static final long ID_COZINHA_INEXISTENTE = 100L;
    private String cozinhaIndiana;

    private Cozinha cozinhaFrancesa;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;
    private int tamanhoCozinha;

    private String pathParam;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.port = port;
        basePath = "/cozinhas";
        pathParam = "/{cozinhaId}";

        databaseCleaner.clearTables();

        cozinhaIndiana = ResourceUtils.getContentFromResource("/jsons/correto/cozinha-indiana.json");

        prepararDados();
    }
    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
            given()
                    .accept(ContentType.JSON)
                    .when()
                        .get()
                    .then()
                        .statusCode(HttpStatus.OK.value());
    }
    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha(){
        given()
                .body(cozinhaIndiana)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
        System.out.println(cozinhaIndiana);
    }


    @Test
    public void deveRetonarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas(){
                when()
                        .get()
                .then()
                    .statusCode(HttpStatus.OK.value())
                        .body("",hasSize(tamanhoCozinha))
                        .body("nome",hasItems("Tailandesa","Francesa"));


        System.out.println("tamanho -> " + tamanhoCozinha);

    }


    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente(){
        given()
                .pathParam("cozinhaId", 1)
                .accept(ContentType.JSON)
                .when()
                    .get(pathParam)
                .then()
                    .statusCode(HttpStatus.OK.value()) // Validação de status
                    .body("nome", equalTo(cozinhaFrancesa.getNome())); // Validação de corpo

    }
    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente(){
        given()
                .pathParam("cozinhaId",ID_COZINHA_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }


    public void prepararDados(){
        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");

        cozinhaFrancesa = new Cozinha();
        cozinhaFrancesa.setNome("Francesa");

        List<Cozinha> cozinaList = Arrays.asList(cozinhaFrancesa,cozinhaTailandesa);

        cozinhaRepository.saveAll(cozinaList);
        tamanhoCozinha = (int) cozinhaRepository.count();
    }
}
