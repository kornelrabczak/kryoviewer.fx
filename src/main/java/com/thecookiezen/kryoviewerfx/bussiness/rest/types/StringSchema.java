package com.thecookiezen.kryoviewerfx.bussiness.rest.types;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

public class StringSchema extends PrimitiveSchema {

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String getTypeString() {
        return JsonFormatTypes.STRING.value();
    }
}
