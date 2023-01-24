package com.algaworks.algafood;


import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

    private static final String ERRO_DADOS_INVALIDOS = "Dados inválidos";
    private static final Long ID_RESTAURANTE_INEXISTENTE = 100L;
    @Autowired
    DatabaseCleaner databaseCleaner;

    @LocalServerPort
    private int port;

    String pathParam;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private Restaurante burgerTopRestaurante;

    private String restauranteCorreto;
    private String restauranteSemFrete;
    private String restauranteSemNome;



    @BeforeEach
    public void setup(){

        enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.port = port;
        basePath = "/restaurantes";
        pathParam = "/{restauranteId}";

        databaseCleaner.clearTables();

        prepararDados();

        restauranteCorreto = ResourceUtils.getContentFromResource("/jsons/correto/restaurante-correto.json");
        restauranteSemFrete = ResourceUtils.getContentFromResource("/jsons/incorreto/restaurante-incorreto-sem-frete.json");
        restauranteSemNome = ResourceUtils.getContentFromResource("/jsons/incorreto/restaurante-incorreto-sem-nome.json");

    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes(){
        given()
                .pathParam("restauranteId",1)
                .accept(ContentType.JSON)
                .with()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestauranteExistente(){
        given()
                .pathParam("restauranteId",1L)
                .when()
                    .get(pathParam)
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("nome",equalTo(burgerTopRestaurante.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente(){
        given()
                .pathParam("restauranteId",ID_RESTAURANTE_INEXISTENTE)
                .when()
                    .get(pathParam)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarRestauranteCorreto(){
    given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(restauranteCorreto)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());
        System.out.println(restauranteCorreto);

    }


    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemFrete(){
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(restauranteSemFrete)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("title", equalTo(ERRO_DADOS_INVALIDOS));

    }

    @Test
    public void deveRetornarStatus400_QuandoCadastroRestauranteSemNome(){
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(restauranteSemNome)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("title", equalTo(ERRO_DADOS_INVALIDOS));
    }
    public void prepararDados(){

        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setCozinha(cozinhaAmericana);
        restauranteRepository.save(burgerTopRestaurante);

        Restaurante comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);
    }
}
