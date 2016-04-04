package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;

import java.io.IOException;

public class SchemaExtractor {
    private final ObjectMapper mapper;

    public SchemaExtractor() {
        this.mapper = new ObjectMapper();
    }

    public ObjectSchema apply(String schema) {
        try {
            return mapper.readValue(schema, ObjectSchema.class);
        } catch (IOException e) {
            System.out.println("JSON schema deserialization failed for schema: " + schema + e);
            throw new RuntimeException("JSON schema deserialization failed");
        }
    }
}
