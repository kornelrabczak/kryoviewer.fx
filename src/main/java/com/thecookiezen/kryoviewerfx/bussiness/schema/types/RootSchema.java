package com.thecookiezen.kryoviewerfx.bussiness.schema.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class RootSchema extends ClassJsonSchema {

    @JsonProperty
    public String name;

    public String getName() {
        return name;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }
}
