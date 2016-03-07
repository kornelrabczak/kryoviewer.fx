package com.thecookiezen.kryoviewerfx.bussiness.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@Log4j
public class SchemaExtractor {
    private final ObjectMapper mapper;

    public SchemaExtractor() {
        this.mapper = new ObjectMapper();
    }

    public List<ObjectSchema> getSchemas() {
        final URL resource = this.getClass().getClassLoader().getResource("example-schema.json");
        try {
            final ObjectSchema classJsonSchema = mapper.readValue(resource, ObjectSchema.class);
            return Lists.newArrayList(classJsonSchema);
        } catch (IOException e) {
            log.error("JSON schema deserialization failed for file " + resource, e);
        }
        return Collections.emptyList();
    }
}
