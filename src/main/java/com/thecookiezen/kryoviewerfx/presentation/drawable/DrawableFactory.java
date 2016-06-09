package com.thecookiezen.kryoviewerfx.presentation.drawable;

import com.thecookiezen.kryoviewerfx.bussiness.schema.Schema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;

public class DrawableFactory {

    public TableDrawable createDrawable(Schema schema) {
        if (schema.getSchema().isArray()) {
            return createArrayDrawable((ArraySchema) schema.getSchema());
        } else {
            return new ObjectDrawable(schema.getSchemaClass());
        }
    }

    private TableDrawable createArrayDrawable(ArraySchema schema) {
        if (schema.isItemPrimitive()) {
            return new PrimitiveDrawable();
        } else {
            return new ArrayDrawable(schema);
        }
    }
}
