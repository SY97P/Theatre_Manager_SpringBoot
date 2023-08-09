package com.tangerine.theatre_manager.global.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {

    private final UUID generatedId;

    public IdGenerator() {
        this.generatedId = UUID.randomUUID();
    }

    public UUID getGeneratedId() {
        return generatedId;
    }
}
