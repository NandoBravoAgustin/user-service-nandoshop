package nandoshop.shop.user_service.application.service.pingpong;

import nandoshop.shop.user_service.application.port.in.useCase.PingPongProductServiceUserCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PingPongProductService implements PingPongProductServiceUserCase {


    private final WebClient webClient;

    public PingPongProductService(WebClient.Builder webClientBuilder, @Value("${product.service.url}") String productMC) {
        this.webClient = webClientBuilder
                .baseUrl(productMC) // URL de Product Service
                .build();
    }

    public String pingPong() {
        return webClient.get()
                .uri("/api/v1/pingpong")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
