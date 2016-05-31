package com.thecookiezen.kryoviewerfx.bussiness.schema.types;

public abstract class RootSchema extends ClassJsonSchema {

    @Override
    public boolean isPrimitive() {
        return false;
    }
}
