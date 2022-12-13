package guru.springframework.sfgrestbrewery.web.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BeerRouterConfig {

    public static final String BASE_BEER_V2_PATH = "/api/v2/beer";

    @Bean
    public RouterFunction<ServerResponse> beerRoutesV2(final BeerHandleV2 handleV2) {
        return route()
                .GET(BASE_BEER_V2_PATH + "/{beerId}", accept(APPLICATION_JSON), handleV2::getBeerById)
                .GET(BASE_BEER_V2_PATH + "/upc/{upc}", accept(APPLICATION_JSON), handleV2::getBeerByUpc)
                .POST(BASE_BEER_V2_PATH, accept(APPLICATION_JSON), handleV2::saveNewBeer)
                .PUT(BASE_BEER_V2_PATH + "/{beerId}", accept(APPLICATION_JSON), handleV2::updateBeer)
                .DELETE(BASE_BEER_V2_PATH + "/{beerId}", accept(APPLICATION_JSON), handleV2::deleteBeer)
                .build();

    }
}
