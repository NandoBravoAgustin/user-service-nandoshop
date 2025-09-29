package nandoshop.shop.user_service.infrastructure.adapter.in.client;

import org.springframework.web.bind.annotation.GetMapping;


public interface ProductClient {
    @GetMapping("/api/v1/pingpong")
    String pingPong();
}
