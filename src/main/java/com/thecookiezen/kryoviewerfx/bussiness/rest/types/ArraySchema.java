package com.thecookiezen.kryoviewerfx.bussiness.rest.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import java.util.ArrayList;
import java.util.List;

public class ArraySchema extends ObjectSchema {

    @JsonProperty
    private List values = new ArrayList<>();

//    protected ArraySchema() {
//        values = new ArrayList<>();
//    }

    public List getValues() {
        return values;
    }

    @Override
    public Class getType() {
        return ArrayList.class;
    }

    @Override
    public String getTypeString() {
        return JsonFormatTypes.ARRAY.value();
    }

    @Override
    public boolean isArray() {
        return true;
    }
}