package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassGenerator {

    public Map<String, Class<?>> loadClasses(final List<ObjectSchema> schemas) {
        return schemas.stream().collect(Collectors.toMap(ClassJsonSchema::getName, this::fromSchema));
    }

    private Class<?> fromSchema(final ObjectSchema schema) {
        DynamicType.Builder<Object> subclass = new ByteBuddy().subclass(Object.class).name(schema.name);

        for (Map.Entry<String, ClassJsonSchema> entry : schema.getProperties().entrySet()) {
            subclass = subclass.defineField(entry.getKey(), entry.getValue().getType(), Visibility.PUBLIC);
        }

        return subclass.make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
    }

}
