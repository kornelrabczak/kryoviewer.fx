package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

import java.util.ArrayList;
import java.util.Map;

public class ClassGenerator {

    public Class<?> fromSchema(final ObjectSchema schema) {
        if (schema.isArray()) {
            return ArrayList.class;
        }

        DynamicType.Builder<Object> subclass = new ByteBuddy().subclass(Object.class).name(schema.name);

        for (Map.Entry<String, ClassJsonSchema> entry : schema.getProperties().entrySet()) {
            subclass = subclass.defineField(entry.getKey(), entry.getValue().getType(), Visibility.PUBLIC);
        }

        return subclass.make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
    }

}
