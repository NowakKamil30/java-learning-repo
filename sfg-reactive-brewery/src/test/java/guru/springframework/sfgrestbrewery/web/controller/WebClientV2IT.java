package guru.springframework.sfgrestbrewery.web.controller;

import guru.springframework.sfgrestbrewery.services.bootstrap.BeerLoader;
import guru.springframework.sfgrestbrewery.web.model.BeerDto;
import guru.springframework.sfgrestbrewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WebClientV2IT {

    static final String BASE_URL = "http://localhost:8080";
    static final String BEER_V2_PATH = "/api/v2/beer";

    WebClient webClient;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();
    }

    @Test
    void getBeerById() throws InterruptedException {
        final var countDownLatch = new CountDownLatch(1);

        final var beerToMono = webClient.get()
                .uri(BEER_V2_PATH + "/" + 1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BeerDto.class);

        beerToMono.subscribe(beerDto -> {
            assertThat(beerDto).isNotNull();
            assertThat(beerDto.getBeerName()).isNotNull();

            countDownLatch.countDown();
        });

        countDownLatch.await();
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }


    @Test
    void getBeerByIdNotFound() throws InterruptedException {
        final var countDownLatch = new CountDownLatch(1);

        final var beerToMono = webClient.get()
                .uri(BEER_V2_PATH + "/" + 1123213)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BeerDto.class);

        beerToMono.subscribe(
                beerDto -> {},
                throwable -> countDownLatch.countDown());

        countDownLatch.await();
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }


    @Test
    void getBeerByUpc() throws InterruptedException {
        final var countDownLatch = new CountDownLatch(1);
        webClient.get()
                .uri(BEER_V2_PATH + "/upc/" + BeerLoader.BEER_2_UPC)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BeerDto.class)
                .subscribe(beerDto -> {
                    assertThat(beerDto).isNotNull();
                    assertThat(beerDto.getBeerName()).isNotNull();

                    countDownLatch.countDown();
                });

        countDownLatch.await();
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Test
    void getBeerByUpcNotFound() throws InterruptedException {
        final var countDownLatch = new CountDownLatch(1);
        webClient.get()
                .uri(BEER_V2_PATH + "/upc/not_exist")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BeerDto.class)
                .subscribe(
                        beerDto -> {},
                        throwable ->  countDownLatch.countDown());

        countDownLatch.await();
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Test
    void createBeer() throws InterruptedException {
        final var countDownLatch = new CountDownLatch(1);

        final var beerDto = BeerDto.builder()
                .beerName("Jtsd")
                .upc("23423")
                .beerStyle(BeerStyleEnum.SAISON.name())
                .price(BigDecimal.valueOf(34))
                .build();

        webClient.post()
                .uri(BEER_V2_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(beerDto))
                .retrieve()
                .toBodilessEntity()
                .publishOn(Schedulers.parallel())
                .subscribe(voidResponseEntity -> {
                    assertThat(voidResponseEntity.getStatusCode().is2xxSuccessful()).isTrue();
                    countDownLatch.countDown();
                });

        countDownLatch.await();
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Test
    void createBeerBadRequest() throws InterruptedException {
        final var countDownLatch = new CountDownLatch(1);

        final var beerDto = BeerDto.builder()
                .price(BigDecimal.valueOf(34))
                .build();

        webClient.post()
                .uri(BEER_V2_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(beerDto))
                .retrieve()
                .toBodilessEntity()
                .publishOn(Schedulers.parallel())
                .subscribe(
                        voidResponseEntity -> {},
                        throwable -> countDownLatch.countDown());

        countDownLatch.await();
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Test
    void updateBeer() throws InterruptedException {
        final var newBeerName = "new name";
        final var beerId = 1;
        final var countDownLatch = new CountDownLatch(2);

        webClient.put()
                .uri(BEER_V2_PATH + "/" + beerId)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(BeerDto.builder()
                        .beerName(newBeerName)
                        .upc("324324234")
                        .beerStyle(BeerStyleEnum.SAISON.name())
                        .price(new BigDecimal("12.33"))
                        .build()))
                .retrieve()
                .toBodilessEntity()
                .subscribe(voidResponseEntity -> {
                    assertThat(voidResponseEntity.getStatusCode().is2xxSuccessful());
                    countDownLatch.countDown();
                });


        countDownLatch.await(500, TimeUnit.MILLISECONDS);

        webClient.get()
                .uri(BEER_V2_PATH + "/" + beerId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BeerDto.class)
                .subscribe(beerDto -> {
                    assertThat(beerDto).isNotNull();
                    assertThat(beerDto.getBeerName()).isEqualTo(newBeerName);

                    countDownLatch.countDown();
                });


        countDownLatch.await();
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Test
    void updateBeerNotFound() throws InterruptedException {
        final var newBeerName = "new name";
        final var beerId = 234532432;
        final var countDownLatch = new CountDownLatch(1);

        webClient.put()
                .uri(BEER_V2_PATH + "/" + beerId)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(BeerDto.builder()
                        .beerName(newBeerName)
                        .upc("324324234")
                        .beerStyle(BeerStyleEnum.SAISON.name())
                        .price(new BigDecimal("12.33"))
                        .build()))
                .retrieve()
                .toBodilessEntity()
                .subscribe(
                        voidResponseEntity -> {},
                        throwable -> {
                            countDownLatch.countDown();
                        });

        countDownLatch.await();
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Test
    void deleteBeer() throws InterruptedException {
        final var beerId = 2;
        final var countDownLatch = new CountDownLatch(1);

        webClient.delete()
                .uri(BEER_V2_PATH + "/" + beerId)
                .retrieve()
                .toBodilessEntity()
                .flatMap(voidResponseEntity -> {
                    countDownLatch.countDown();

                    return webClient.get()
                            .uri(BEER_V2_PATH + "/" + beerId)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(BeerDto.class);
                }).subscribe(
                        beerDto -> {},
                        throwable -> countDownLatch.countDown());

        countDownLatch.await();
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Test
    void deleteBeerNotFound() {
        final var beerId = 21321;

        assertThrows(WebClientResponseException.NotFound.class, () -> {
           webClient.delete()
                   .uri(BEER_V2_PATH + "/" + beerId)
                   .retrieve()
                   .toBodilessEntity()
                   .block();
        });
    }

}
