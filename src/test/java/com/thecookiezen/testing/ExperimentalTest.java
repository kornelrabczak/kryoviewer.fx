package com.thecookiezen.testing;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ExperimentalTest {

    @Test
    public void test() throws FileNotFoundException, IllegalAccessException {
        Kryo kryo = new Kryo();

        Output output = new Output(new FileOutputStream("file.bin"));
        TestClass testClass = new TestClass(false, 123, "qwe");
        kryo.writeObject(output, testClass);
        output.close();


        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .defineField("a", boolean.class, Visibility.PUBLIC)
                .defineField("b", int.class, Visibility.PUBLIC)
                .defineField("c", String.class, Visibility.PUBLIC)
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();


        Input input = new Input(new FileInputStream("file.bin"));
        final Object o = kryo.readObject(input, dynamicType);
        input.close();

        Field[] fields = dynamicType.getDeclaredFields();
        System.out.printf("%d fields:%n", fields.length);
        for (Field field : fields) {
            System.out.printf("%s %s %s %s%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName(), field.get(o));
        }
    }

}
