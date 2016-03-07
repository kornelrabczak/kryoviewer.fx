package com.thecookiezen.kryoviewerfx.bussiness.rest.types;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

public class BooleanSchema extends ClassJsonSchema {

    @Override
    public Class<Boolean> getType() {
        return boolean.class;
    }

    @Override
    public String getTypeString() {
        return JsonFormatTypes.BOOLEAN.value();
    }
}
