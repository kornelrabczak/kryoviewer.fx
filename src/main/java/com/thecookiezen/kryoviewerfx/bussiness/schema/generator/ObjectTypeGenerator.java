package com.thecookiezen.kryoviewerfx.bussiness.schema.generator;

import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

import java.util.Map;

public class ObjectTypeGenerator implements TypeGenerator<ObjectSchema> {

    @Override
    public Class<?> generate(ObjectSchema schema) {
        DynamicType.Builder<Object> subclass = new ByteBuddy().subclass(Object.class).name(schema.name);

        for (Map.Entry<String, ClassJsonSchema> entry : schema.getProperties().entrySet()) {
            if (entry.getValue() instanceof ObjectSchema) {
                Class<?> generate = generate((ObjectSchema) entry.getValue());
                subclass =  subclass.defineField(entry.getKey(), generate, Visibility.PUBLIC);
            } else {
                subclass = subclass.defineField(entry.getKey(), entry.getValue().getType(), Visibility.PUBLIC);
            }
        }

        return subclass.make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
    }

}
