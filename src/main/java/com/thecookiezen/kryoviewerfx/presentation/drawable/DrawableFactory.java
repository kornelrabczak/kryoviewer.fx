package com.thecookiezen.kryoviewerfx.presentation.drawable;

import com.thecookiezen.kryoviewerfx.bussiness.schema.Schema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;

public class DrawableFactory {

    public static TableDrawable createDrawable(Schema schema) {
        if (schema.getSchema().isArray()) {
            if (((ArraySchema) schema.getSchema()).getItemsSchema().isPrimitive()) {
                return new PrimitiveDrawable();
            } else {
                try {
                    return new ArrayDrawable(schema);
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException("Class not found");
                }
            }
        } else {
            return new ObjectDrawable(schema.getSchemaClass());
        }
    }
}
