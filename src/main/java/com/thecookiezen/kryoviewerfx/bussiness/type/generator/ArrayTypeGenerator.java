package com.thecookiezen.kryoviewerfx.bussiness.type.generator;

import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;

public class ArrayTypeGenerator implements TypeGenerator<ArraySchema> {

    @Override
    public Class<?> generate(ArraySchema schema) {
        if (schema.getItemsSchema().isArray()) {
            throw new IllegalStateException("Arrays cannot be nested");
        }

        if (schema.getItemsSchema().isPrimitive()) {
            return schema.getItemsSchema().getType();
        }

        return new ObjectTypeGenerator().generate((ObjectSchema) schema.getItemsSchema());
    }
}
