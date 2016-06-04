package com.thecookiezen.kryoviewerfx.bussiness.classloader.loader;

import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

import java.util.Map;

public class ObjectLoader implements Loadable<ObjectSchema> {

    @Override
    public Class<?> loadFromSchema(ObjectSchema schema) {
        try {
            return Class.forName(schema.name);
        } catch (ClassNotFoundException ignored) {
        }

        DynamicType.Builder<Object> subclass = new ByteBuddy().subclass(Object.class).name(schema.name);

        for (Map.Entry<String, ClassJsonSchema> entry : schema.getProperties().entrySet()) {
            ClassJsonSchema value = entry.getValue();
            if (!value.isArray() && !value.isPrimitive()) {
                Class<?> generate = loadFromSchema((ObjectSchema) value);
                subclass =  subclass.defineField(entry.getKey(), generate, Visibility.PUBLIC);
            } else {
                subclass = subclass.defineField(entry.getKey(), value.getType(), Visibility.PUBLIC);
            }
        }

        return subclass.make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
    }

}
