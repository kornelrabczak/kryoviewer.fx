package com.thecookiezen.kryoviewerfx.presentation.drawable;

import com.thecookiezen.kryoviewerfx.bussiness.deserializer.ObjectProvider;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;

public class ArrayDrawable extends ObjectDrawable {
    public ArrayDrawable(ClassJsonSchema schema) {
        super(getClazz(schema), new ObjectProvider());
    }

    private static Class<?> getClazz(ClassJsonSchema schema) {
        try {
            return Class.forName(((ObjectSchema) ((ArraySchema) schema).getItemsSchema()).name);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class not found");
        }
    }
}
