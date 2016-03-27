package com.thecookiezen.kryoviewerfx.bussiness.rest.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@ToString
public class ObjectSchema extends ClassJsonSchema {

    @JsonProperty
    public Map<String, ClassJsonSchema> properties = new LinkedHashMap<>();

    @JsonProperty
    public String name;

    public ObjectSchema() {
        properties = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, ClassJsonSchema> getProperties() {
        return properties;
    }

    @Override
    public Class<Object> getType() {
        return Object.class;
    }

    @Override
    public String getTypeString() {
        return JsonFormatTypes.OBJECT.value();
    }
}
