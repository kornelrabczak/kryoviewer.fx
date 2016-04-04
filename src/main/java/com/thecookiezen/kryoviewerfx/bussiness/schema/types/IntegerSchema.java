package com.thecookiezen.kryoviewerfx.bussiness.schema.types;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

public class IntegerSchema extends PrimitiveSchema {

    @Override
    public Class<Integer> getType() {
        return int.class;
    }

    @Override
    public String getTypeString() {
        return JsonFormatTypes.INTEGER.value();
    }
}
