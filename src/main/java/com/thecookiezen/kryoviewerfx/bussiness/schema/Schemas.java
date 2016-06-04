package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.thecookiezen.kryoviewerfx.bussiness.schema.types.RootSchema;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Schemas {
    private final Map<String, Schema> schemas = new HashMap<>();

    public Schemas(SchemaDeserializer extractor, Supplier<Collection<File>> findFiles) {
        findFiles.get().forEach(f -> {
            System.out.println(f);
            String json = loadJsonFromFile(f);
            RootSchema schema = extractor.apply(json);
            int pos = f.getName().lastIndexOf('.');
            String fileName = pos > 0 ? f.getName().substring(0, pos) : f.getName();
            schemas.put(fileName, new Schema(schema));
        });
    }

    private String loadJsonFromFile(File file) {
        try (FileInputStream input = new FileInputStream(file)) {
            return IOUtils.toString(input);
        } catch (IOException e) {
            return "{}";
        }
    }

    public Map<String, Schema> getSchemas() {
        return schemas;
    }
}
