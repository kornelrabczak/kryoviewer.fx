package com.thecookiezen.kryoviewerfx.bussiness.classloader;

import com.thecookiezen.kryoviewerfx.bussiness.classloader.loader.ArrayLoader;
import com.thecookiezen.kryoviewerfx.bussiness.classloader.loader.ObjectLoader;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.RootSchema;

public class ClassLoaderFactory {

    public Class<?> fromSchema(final RootSchema schema) {
        if (schema.isArray()) {
            return new ArrayLoader().loadFromSchema((ArraySchema) schema);
        }

        if (!schema.isPrimitive()) {
            return new ObjectLoader().loadFromSchema((ObjectSchema) schema);
        }

        throw new RuntimeException("Unknown schema type");
    }
}
