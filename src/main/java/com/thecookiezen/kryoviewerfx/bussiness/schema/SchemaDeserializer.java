package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.RootSchema;

import java.io.IOException;

public class SchemaDeserializer {
    private final ObjectMapper mapper;

    public SchemaDeserializer() {
        this.mapper = new ObjectMapper();
    }

    public RootSchema apply(String schema) {
        try {
            return mapper.readValue(schema, RootSchema.class);
        } catch (Exception e) {
            System.out.println("JSON schema deserialization failed for schema: " + schema + e);
            throw new IllegalStateException("JSON schema deserialization failed", e);
        }
    }
}
