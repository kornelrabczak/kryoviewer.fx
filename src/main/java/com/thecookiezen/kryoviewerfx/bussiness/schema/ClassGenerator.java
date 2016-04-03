package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.generator.ArrayTypeGenerator;
import com.thecookiezen.kryoviewerfx.bussiness.schema.generator.ObjectTypeGenerator;

public class ClassGenerator {

    public Class<?> fromSchema(final ClassJsonSchema schema) {
        if (schema.isArray()) {
            return new ArrayTypeGenerator().generate((ArraySchema) schema);
        }

        if (!schema.isPrimitive()) {
            return new ObjectTypeGenerator().generate((ObjectSchema) schema);
        }

        throw new RuntimeException("Unknown schema type");
    }

}
