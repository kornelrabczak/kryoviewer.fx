package com.thecookiezen.kryoviewerfx.bussiness.rest;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.BooleanSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.IntegerSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.StringSchema;

import java.util.Arrays;

public class JsonSchemaIdResolver extends TypeIdResolverBase {
    @Override
    public String idFromValue(Object value) {
        if (value instanceof ClassJsonSchema) {
            return ((ClassJsonSchema) value).getTypeString();
        }
        return null;
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> aClass) {
        return idFromValue(value);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }

    @Override
    public JavaType typeFromId(DatabindContext ctxt, String id)
    {
        JsonFormatTypes stdType = JsonFormatTypes.forValue(id);
        if (stdType != null) {
            switch (stdType) {
//                case ARRAY:
//                    return ctxt.constructType(ArraySchema.class);
                case BOOLEAN:
                    return ctxt.constructType(BooleanSchema.class);
                case INTEGER:
                    return ctxt.constructType(IntegerSchema.class);
//                case NULL:
//                    return ctxt.constructType(NullSchema.class);
//                case NUMBER:
//                    return ctxt.constructType(NumberSchema.class);
                case OBJECT:
                    return ctxt.constructType(ObjectSchema.class);
                case STRING:
                    return ctxt.constructType(StringSchema.class);
//                case ANY:
//                default:
//                    return ctxt.constructType(AnySchema.class);
            }
        }
        // Not a standard type; should use a custom sub-type impl
        throw new IllegalArgumentException("Can not resolve JsonSchema 'type' id of \""+ id
                +"\", not recognized as one of standard values: "+ Arrays.asList(JsonFormatTypes.values()));
    }
}
