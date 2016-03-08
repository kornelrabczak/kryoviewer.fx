package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.google.common.collect.Lists;
import com.thecookiezen.kryoviewerfx.bussiness.rest.SchemaExtractor;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ClassJsonSchema;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Map;
import java.util.stream.Collectors;

public class Schemas {
    private final static String path = "./schemas";

    private final static FilenameFilter filter = (dir, name) -> name.toLowerCase().endsWith(".json");

    private final Map<String, Class<?>> schemas;

    public Schemas(SchemaExtractor extractor, ClassGenerator classGenerator) {
        File folder = new File(path);
        schemas = Lists.newArrayList(folder.listFiles(filter)).stream()
                .map(extractor::apply)
                .collect(Collectors.toMap(ClassJsonSchema::getName, classGenerator::fromSchema));
    }

    public Map<String, Class<?>> getSchemas() {
        return schemas;
    }
}
