package com.brinkcommerce.api.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

public class OptionalInstantDeserializer extends JsonDeserializer<Optional<Instant>> {

    @Override
    public Optional<Instant> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.isTextual()) {
            return Optional.of(Instant.parse(node.asText()));
        }

        return Optional.empty();
    }
}
