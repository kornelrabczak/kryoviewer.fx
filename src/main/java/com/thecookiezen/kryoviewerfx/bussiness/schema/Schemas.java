package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.thecookiezen.kryoviewerfx.bussiness.classloader.ClassLoaderFactory;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.RootSchema;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Schemas {
    private final Map<String, Class<?>> schemaName2ClassMap;

    public Schemas(SchemaDeserializer extractor, ClassLoaderFactory classLoaderFactory, Supplier<Collection<File>> findFiles) {
        schemaName2ClassMap = findFiles.get().stream()
                .peek(System.out::println)
                .map(this::loadJsonFromFile)
                .map(extractor::apply)
                .collect(Collectors.toMap(RootSchema::getName, classLoaderFactory::fromSchema));
    }

    private String loadJsonFromFile(File file) {
        try (FileInputStream input = new FileInputStream(file)) {
            return IOUtils.toString(input);
        } catch (IOException e) {
            return "{}";
        }
    }

    public Map<String, Class<?>> getSchemaName2ClassMap() {
        return schemaName2ClassMap;
    }
}
