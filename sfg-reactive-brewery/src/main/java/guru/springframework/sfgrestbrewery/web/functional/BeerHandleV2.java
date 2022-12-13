package guru.springframework.sfgrestbrewery.web.functional;

import guru.springframework.sfgrestbrewery.services.BeerService;
import guru.springframework.sfgrestbrewery.web.controller.NotFoundException;
import guru.springframework.sfgrestbrewery.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerHandleV2 {
    private final BeerService beerService;
    private final Validator validator;

    public Mono<ServerResponse> getBeerById(final ServerRequest request) {
        final var beerId = Integer.valueOf(request.pathVariable("beerId"));
        final var showInventory = Boolean.valueOf(request.queryParam("showInventory")
                .orElse("false"));

        return beerService.getById(beerId, showInventory)
                .flatMap(beerDto -> ServerResponse.ok().bodyValue(beerDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getBeerByUpc(final ServerRequest request) {
        final var upc = String.valueOf(request.pathVariable("upc"));

        return beerService.getByUpc(upc)
                .flatMap(beerDto -> ServerResponse.ok().bodyValue(beerDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateBeer (final ServerRequest serverRequest) {
        final var id = Integer.valueOf(serverRequest.pathVariable("beerId"));
        return serverRequest.bodyToMono(BeerDto.class).doOnNext(this::validate)
                .flatMap(beerDto -> beerService.updateBeer(id, beerDto))
                .flatMap(beerDto -> {
                    if (Objects.isNull(beerDto.getId())) {
                        return ServerResponse.notFound().build();
                    }
                    return ServerResponse.noContent().build();
                });
    }

    public Mono<ServerResponse> saveNewBeer(final ServerRequest serverRequest) {
        final var beerDtoMono = serverRequest.bodyToMono(BeerDto.class)
                .doOnNext(this::validate);

        return beerService.saveNewBeerByMono(beerDtoMono)
                .flatMap(beerDto -> ServerResponse.ok()
                        .header("location", BeerRouterConfig.BASE_BEER_V2_PATH + "/" + beerDto.getId())
                        .build());
    }

    private void validate(final BeerDto beerDto) {
        final var erros = new BeanPropertyBindingResult(beerDto, "beerDto");
        validator.validate(beerDto, erros);

        if (erros.hasErrors()) {
            throw new ServerWebInputException(erros.toString());
        }
    }

    public Mono<ServerResponse> deleteBeer(final ServerRequest serverRequest) {
        final var id = Integer.valueOf(serverRequest.pathVariable("beerId"));
        return beerService.reactiveDeleteBeerById(id)
                .flatMap(voidMono -> ServerResponse.noContent().build())
                .onErrorResume(e -> e instanceof NotFoundException, e -> ServerResponse.notFound().build());
    }
}
