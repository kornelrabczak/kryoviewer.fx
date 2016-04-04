package com.thecookiezen.kryoviewerfx.bussiness.schema.types;

public abstract class PrimitiveSchema extends ClassJsonSchema {
    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public boolean isArray() {
        return false;
    }
}
