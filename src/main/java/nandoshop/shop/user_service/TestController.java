package nandoshop.shop.user_service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "Logs", description = "Endpoints de prueba para validar logs y niveles de logging")
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Operation(
            summary = "Prueba de logs",
            description = "Genera logs en diferentes niveles (DEBUG, INFO, WARN, ERROR) y retorna un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Logs generados exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @GetMapping("/logtest")
    public String logTest() {
        log.debug("DEBUG log");
        log.info("INFO log");
        log.warn("WARN log");
        log.error("ERROR log");
        return "Check logs";
    }

}
