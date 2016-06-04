package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecookiezen.kryoviewerfx.bussiness.classloader.ClassLoaderFactory;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.RootSchema;

public class Schema {

    private static final ClassLoaderFactory CLASS_LOADER_FACTORY = new ClassLoaderFactory();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Class<?> clazz;
    private ClassJsonSchema schema;

    public Schema(RootSchema schema) {
        this.schema = schema;
        clazz = CLASS_LOADER_FACTORY.fromSchema(schema);
    }

    public Class<?> getSchemaClass() {
        return clazz;
    }

    public ClassJsonSchema getSchema() {
        return schema;
    }

    public String getPrettyPrint() {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
