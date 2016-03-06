package com.thecookiezen.testing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "class")
public class ClassJsonSchema {

    @JsonProperty
    private String id;

    @JsonProperty
    private Map<String, ClassJsonSchema> properties;

    protected ClassJsonSchema() {
        properties = new LinkedHashMap<>();
    }

    public String getId() {
        return id;
    }

    public Map<String, ClassJsonSchema> getProperties() {
        return properties;
    }

//    public JsonFormatTypes getType() {
//        return JsonFormatTypes.OBJECT;
//    }

    public abstract JsonFormatTypes getType();

    public static Optional minimalForFormat(JsonFormatTypes format)
    {
        if (format != null) {
            switch (format) {
                case ARRAY:
                    return null;
                case OBJECT:
                    return null;
                case BOOLEAN:
                    return null;
                case INTEGER:
                    return null;
                case NUMBER:
                    return null;
                case STRING:
                    return null;
                case NULL:
                    return null;
                case ANY:
                default:
            }
        }
        return Optional.empty();
    }
}
