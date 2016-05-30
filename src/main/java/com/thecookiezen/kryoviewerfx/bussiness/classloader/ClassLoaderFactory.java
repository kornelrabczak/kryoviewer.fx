package com.thecookiezen.kryoviewerfx.bussiness.classloader;

import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.classloader.loader.ArrayLoader;
import com.thecookiezen.kryoviewerfx.bussiness.classloader.loader.ObjectLoader;

public class ClassLoaderFactory {

    public Class<?> fromSchema(final ClassJsonSchema schema) {
        if (schema.isArray()) {
            return new ArrayLoader().loadFromSchema((ArraySchema) schema);
        }

        if (!schema.isPrimitive()) {
            return new ObjectLoader().loadFromSchema((ObjectSchema) schema);
        }

        if (schema.isPrimitive()) {
            return schema.getType();
        }

        throw new RuntimeException("Unknown schema type");
    }
}
