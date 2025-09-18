package nandoshop.shop.user_service.config;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon2PasswordEncoderBuilder {

    private int saltLength;
    private int hashLength;
    private int parallelism;
    private int memory;
    private int iterations;

    private Argon2PasswordEncoderBuilder() {}

    public static Argon2PasswordEncoderBuilder builder() {
        return new Argon2PasswordEncoderBuilder();
    }

    public Argon2PasswordEncoderBuilder saltLength(int saltLength) {
        this.saltLength = saltLength;
        return this;
    }

    public Argon2PasswordEncoderBuilder hashLength(int hashLength) {
        this.hashLength = hashLength;
        return this;
    }

    public Argon2PasswordEncoderBuilder parallelism(int parallelism) {
        this.parallelism = parallelism;
        return this;
    }

    public Argon2PasswordEncoderBuilder memory(int memory) {
        this.memory = memory;
        return this;
    }

    public Argon2PasswordEncoderBuilder iterations(int iterations) {
        this.iterations = iterations;
        return this;
    }

    public Argon2PasswordEncoder build() {
        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }
}

