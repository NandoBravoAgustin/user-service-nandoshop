package nandoshop.shop.user_service.infrastructure.config;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "security.password.argon2")
public class Argon2Properties {

    @Min(value = 8, message = "{argon2.saltLength.min}")
    private final int saltLength;

    @Min(value = 16, message = "{argon2.hashLength.min}")
    private final int hashLength;

    @Min(value = 1, message = "{argon2.parallelism.min}")
    private final int parallelism;

    @Min(value = 16384, message = "{argon2.memory.min}")
    private final int memory;

    @Min(value = 2, message = "{argon2.iterations.min}")
    private final int iterations;

    public Argon2Properties(int saltLength, int hashLength, int parallelism, int memory, int iterations) {
        this.saltLength = saltLength;
        this.hashLength = hashLength;
        this.parallelism = parallelism;
        this.memory = memory;
        this.iterations = iterations;
    }

    public int getSaltLength() { return saltLength; }
    public int getHashLength() { return hashLength; }
    public int getParallelism() { return parallelism; }
    public int getMemory() { return memory; }
    public int getIterations() { return iterations; }

    // Salt < Hash
    @AssertTrue(message = "{argon2.hashLength.gt.saltLength}")
    public boolean isHashLengthValid() {
        return hashLength > saltLength;
    }

    // Memoria * iteraciones <= 1GB
    @AssertTrue(message = "{argon2.memory.usage.limit}")
    public boolean isMemoryUsageValid() {
        return ((long) memory * iterations) <= 1_048_576L;
    }

    // Paralelismo <= núcleos disponibles
    @AssertTrue(message = "{argon2.parallelism.max}")
    public boolean isParallelismValid() {
        return parallelism <= Runtime.getRuntime().availableProcessors();
    }

    // Memoria máxima absoluta 512MB
    @AssertTrue(message = "{argon2.memory.max}")
    public boolean isMemoryMaxValid() {
        return memory <= 524_288;
    }
}
