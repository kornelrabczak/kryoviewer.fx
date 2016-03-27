package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.generator.ObjectTypeGenerator;

public class ClassGenerator {

    public Class<?> fromSchema(final ClassJsonSchema schema) {
        if (schema instanceof ObjectSchema) {
            return new ObjectTypeGenerator().generate((ObjectSchema) schema);
        }

        throw new RuntimeException("Unknown schema type");
    }

}
