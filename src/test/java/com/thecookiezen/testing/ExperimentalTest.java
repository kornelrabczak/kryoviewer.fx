package com.thecookiezen.testing;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ClassJsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Map;

public class ExperimentalTest {

    @Test
    public void test() throws IOException, IllegalAccessException {
        Kryo kryo = new Kryo();

        Output output = new Output(new FileOutputStream("file.bin"));
        TestClass testClass = new TestClass(false, 123, "qwe");
        kryo.writeObject(output, testClass);
        output.close();

        ObjectMapper mapper = new ObjectMapper();

        final URL resource = this.getClass().getClassLoader().getResource("example-schema.json");

        final ObjectSchema classJsonSchema = mapper.readValue(resource, ObjectSchema.class);

        DynamicType.Builder<Object> subclass = new ByteBuddy().subclass(Object.class).name(classJsonSchema.name);

        for (Map.Entry<String, ClassJsonSchema> entry : classJsonSchema.getProperties().entrySet()) {
            subclass = subclass.defineField(entry.getKey(), entry.getValue().getType(), Visibility.PUBLIC);
        }

        final Class<?> loaded = subclass.make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Input input = new Input(new FileInputStream("file.bin"));
        final Object o = kryo.readObject(input, loaded);
        input.close();

        Field[] fields = loaded.getDeclaredFields();
        System.out.println(o);
        System.out.printf("%d fields:%n", fields.length);
        for (Field field : fields) {
            System.out.printf("%s %s %s %s%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName(), field.get(o));
        }
    }
}
