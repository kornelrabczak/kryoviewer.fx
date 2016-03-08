package com.thecookiezen.kryoviewerfx.bussiness.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;

import java.io.File;
import java.io.IOException;

public class SchemaExtractor {
    private final ObjectMapper mapper;

    public SchemaExtractor() {
        this.mapper = new ObjectMapper();
    }

    public ObjectSchema apply(File resource) {
        try {
            return mapper.readValue(resource, ObjectSchema.class);
        } catch (IOException e) {
            System.out.println("JSON schema deserialization failed for file " + resource + e);
            throw new RuntimeException("JSON schema deserialization failed");
        }
    }
}
