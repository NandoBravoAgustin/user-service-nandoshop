package nandoshop.shop.user_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
@EnableConfigurationProperties(Argon2Properties.class)
public class PasswordEncoderConfig {

    private static final Logger log = LoggerFactory.getLogger(PasswordEncoderConfig.class);

    private final Argon2Properties props;

    public PasswordEncoderConfig(Argon2Properties props) {
        this.props = props;
    }
    /**
     * Bean de Argon2PasswordEncoder para toda la aplicación.
     * Este encoder se usa para:
     *   - Hashear contraseñas al registrar usuarios
     *   - Verificar contraseñas en login
     *
     * Elegimos Argon2 porque es resistente a ataques de GPU y ASIC,
     * y permite ajustar memoria, iteraciones y paralelismo.
     *
     * Parámetros:
     * - saltLength: longitud del salt aleatorio en bytes (protege contra rainbow tables)
     * - hashLength: longitud del hash resultante en bytes (256 bits es seguro)
     * - parallelism: número de hilos que utiliza el algoritmo (mayor = más rápido, más memoria)
     * - memory: memoria utilizada en KB (protege contra ataques de hardware especializado)
     * - iterations: número de iteraciones (aumenta el tiempo de cómputo, más seguro)
     */
    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        if (log.isDebugEnabled()) {
            log.debug("Inicializando Argon2 con saltLength={}, hashLength={}, parallelism={}, memory={}, iterations={}",
                    props.getSaltLength(), props.getHashLength(), props.getParallelism(),
                    props.getMemory(), props.getIterations()
            );
        }
        return new Argon2PasswordEncoder(
                props.getSaltLength(),
                props.getHashLength(),
                props.getParallelism(),
                props.getMemory(),
                props.getIterations()
        );
    }

    private Argon2PasswordEncoder buildArgon2Encoder() {
        return Argon2PasswordEncoderBuilder.builder()
                .saltLength(props.getSaltLength())
                .hashLength(props.getHashLength())
                .parallelism(props.getParallelism())
                .memory(props.getMemory())
                .iterations(props.getIterations())
                .build();
    }

}
