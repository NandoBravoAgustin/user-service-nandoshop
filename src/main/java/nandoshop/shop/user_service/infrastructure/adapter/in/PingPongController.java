package nandoshop.shop.user_service.infrastructure.adapter.in;

import nandoshop.shop.user_service.application.port.in.useCase.PingPongProductServiceUserCase;
import nandoshop.shop.user_service.application.service.pingpong.PingPongProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PingPongController {
    private final PingPongProductServiceUserCase pingPongProductServiceUserCase;

    public PingPongController(PingPongProductService pingPongProductService) {
        this.pingPongProductServiceUserCase = pingPongProductService;
    }

    @GetMapping("/pingpong/product-service")
    public String pingPong() {
        return pingPongProductServiceUserCase.pingPong();
    }
}
