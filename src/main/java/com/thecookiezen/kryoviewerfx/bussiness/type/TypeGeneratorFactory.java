package com.thecookiezen.kryoviewerfx.bussiness.type;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.type.generator.ArrayTypeGenerator;
import com.thecookiezen.kryoviewerfx.bussiness.type.generator.ObjectTypeGenerator;

import java.util.List;

public class TypeGeneratorFactory {

    private final static TypeFactory TYPE_FACTORY = TypeFactory.defaultInstance();

    public ResolvedType fromSchema(final ClassJsonSchema schema) {
        if (schema.isArray()) {
            final Class<?> generate = new ArrayTypeGenerator().generate((ArraySchema) schema);
            return TYPE_FACTORY.constructCollectionType(List.class, generate);
        }

        if (!schema.isPrimitive()) {
            final Class<?> generate = new ObjectTypeGenerator().generate((ObjectSchema) schema);
            return TYPE_FACTORY.constructType(generate);
        }

        throw new RuntimeException("Unknown schema type");
    }
}
