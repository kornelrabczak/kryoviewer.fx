package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.thecookiezen.kryoviewerfx.bussiness.rest.SchemaExtractor;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ClassJsonSchema;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.apache.commons.io.FileUtils.listFiles;

public class Schemas {
    private final Map<String, Class<?>> schemas;

    Supplier<Collection<File>> findFiles = () -> {
        File directory = new File("./classes/schemas");
        return listFiles(directory, new String[]{"json"}, true);
    };

    public Schemas(SchemaExtractor extractor, ClassGenerator classGenerator) {
        schemas = findFiles.get().stream()
                .peek(System.out::println)
                .map(this::loadJsonFromFile)
                .map(extractor::apply)
                .collect(Collectors.toMap(ClassJsonSchema::getName, classGenerator::fromSchema));
    }

    private String loadJsonFromFile(File file) {
        try (FileInputStream input = new FileInputStream(file)) {
            return IOUtils.toString(input);
        } catch (IOException e) {
            return "{}";
        }
    }

    public Map<String, Class<?>> getSchemas() {
        return schemas;
    }
}
