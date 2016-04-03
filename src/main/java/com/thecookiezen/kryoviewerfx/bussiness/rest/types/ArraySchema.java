package com.thecookiezen.kryoviewerfx.bussiness.rest.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import java.util.ArrayList;
import java.util.LinkedList;

public class ArraySchema extends ClassJsonSchema {

    @JsonProperty
    public ClassJsonSchema itemsSchema;

    @Override
    public Class getType() {
        return LinkedList.class;
    }

    @Override
    public String getTypeString() {
        return JsonFormatTypes.ARRAY.value();
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    public ClassJsonSchema getItemsSchema() {
        return itemsSchema;
    }
}