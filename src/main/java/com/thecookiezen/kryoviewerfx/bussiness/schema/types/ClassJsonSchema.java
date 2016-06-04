package com.thecookiezen.kryoviewerfx.bussiness.schema.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.thecookiezen.kryoviewerfx.bussiness.schema.JsonSchemaIdResolver;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeIdResolver(JsonSchemaIdResolver.class)
public abstract class ClassJsonSchema {

    @JsonProperty
    public String type;

    @JsonIgnore
    public abstract Class<?> getType();

    @JsonIgnore
    public abstract String getTypeString();

    @JsonIgnore
    public boolean isArray() {
        return false;
    }

    @JsonIgnore
    public abstract boolean isPrimitive();
}
