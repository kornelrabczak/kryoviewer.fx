package com.thecookiezen.kryoviewerfx.bussiness.classloader.loader;

import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;

public class ArrayLoader implements Loadable<ArraySchema> {

    @Override
    public Class<?> loadFromSchema(ArraySchema schema) {
        if (schema.getItemsSchema().isArray()) {
            throw new IllegalStateException("Arrays cannot be nested");
        }

        if (!schema.getItemsSchema().isPrimitive()) {
            new ObjectLoader().loadFromSchema((ObjectSchema) schema.getItemsSchema());
        }

        return schema.getType();
    }
}
