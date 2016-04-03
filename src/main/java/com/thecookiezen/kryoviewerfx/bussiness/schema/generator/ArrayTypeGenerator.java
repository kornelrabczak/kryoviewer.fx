package com.thecookiezen.kryoviewerfx.bussiness.schema.generator;

import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.generator.ObjectTypeGenerator;
import com.thecookiezen.kryoviewerfx.bussiness.schema.generator.TypeGenerator;

public class ArrayTypeGenerator implements TypeGenerator<ArraySchema> {

    @Override
    public Class<?> generate(ArraySchema schema) {
        if (schema.getItemsSchema().isArray()) {
            throw new IllegalStateException("Arrays cannot be nested");
        }

        if (schema.getItemsSchema().isPrimitive()) {
            return schema.getType();
        }

        Class<?> generate = new ObjectTypeGenerator().generate((ObjectSchema) schema.getItemsSchema());

        return schema.getType();
    }
}
