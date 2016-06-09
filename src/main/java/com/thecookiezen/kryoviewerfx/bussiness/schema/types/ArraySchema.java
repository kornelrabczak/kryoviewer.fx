package com.thecookiezen.kryoviewerfx.bussiness.schema.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import java.util.LinkedList;

public class ArraySchema extends RootSchema {

    @JsonProperty
    private ClassJsonSchema itemsSchema;

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

    public ClassJsonSchema getItemsSchema() {
        return itemsSchema;
    }

    public void setItemsSchema(ClassJsonSchema itemsSchema) {
        this.itemsSchema = itemsSchema;
    }

    @JsonIgnore
    public boolean isItemPrimitive() {
        return itemsSchema.isPrimitive();
    }
}