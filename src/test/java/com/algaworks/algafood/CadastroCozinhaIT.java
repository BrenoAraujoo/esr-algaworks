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
    private int quantidadeCozinhas;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;
    private int tamanhoCozinha;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.port = port;
        basePath = "/cozinhas";
        String pathParam = "/{cozinhaId}";

        databaseCleaner.clearTables();

        cozinhaIndiana = ResourceUtils.getContentFromResource("/json/correto/cozinha-indiana.json");

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
    public void deveRetonarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas(){
                when()
                        .get()
                .then()
                    .statusCode(HttpStatus.OK.value())
                        .body("",hasSize(tamanhoCozinha))
                        .body("nome",hasItems("Indiana","Francesa"));

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
                .pathParam("cozinhaId",ID_COZINHA_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void teste(){
        System.out.println("Cozinha indiana ->" + cozinhaIndiana);
    }


    public void prepararDados(){
        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");
        Cozinha cozinhaFrancesa = new Cozinha();
        cozinhaFrancesa.setNome("Francesa");

        List<Cozinha> cozinaList = Arrays.asList(cozinhaTailandesa,cozinhaFrancesa);

        cozinhaRepository.saveAll(cozinaList);
        quantidadeCozinhas = (int) cozinhaRepository.count();
    }
}
