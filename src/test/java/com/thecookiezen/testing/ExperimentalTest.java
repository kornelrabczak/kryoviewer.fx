package com.thecookiezen.testing;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.Lists;
import com.thecookiezen.kryoviewerfx.bussiness.kryo.KryoWrapper;
import com.thecookiezen.kryoviewerfx.bussiness.rest.SchemaExtractor;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.ClassGenerator;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

public class ExperimentalTest {

    @Test
    public void test() throws IOException, IllegalAccessException {
//        Kryo kryo = new KryoWrapper().getKryo();
//
//        Output output = new Output(new FileOutputStream("file.bin"));
//        TestClass testClass = new TestClass(false, 123, "qwe");
//        kryo.writeObject(output, testClass);
//        output.close();
//
//        final List<ObjectSchema> schemas = new SchemaExtractor().getSchemas();
//
//        final ClassGenerator classGenerator = new ClassGenerator();
//        final Map<String, Class<?>> stringClassMap = classGenerator.loadClasses(schemas);
//        final Class<?> aClass = stringClassMap.entrySet().stream().findFirst().get().getValue();
//
//        Input input = new Input(new FileInputStream("file.bin"));
//        final Object o = kryo.readObject(input, aClass);
//        input.close();
//
//        Field[] fields = aClass.getDeclaredFields();
//        System.out.println(o);
//        System.out.printf("%d fields:%n", fields.length);
//        for (Field field : fields) {
//            System.out.printf("%s %s %s %s%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName(), field.get(o));
//        }



        Kryo kryo = new KryoWrapper().getKryo();

        Output output = new Output(new FileOutputStream("file2.bin"));
        kryo.writeObject(output, Lists.newArrayList("test1", "test2", "test3"));
        output.close();

        ArraySchema schema = new ArraySchema();
        schema.name = "testing";
        schema.type = "array";
        Class<?> aClass = new ClassGenerator().fromSchema(schema);

        Input input = new Input(new FileInputStream("file.bin"));
        Object o = kryo.readObject(input, aClass);
        input.close();

        System.out.println(o);

        Field[] fields = aClass.getDeclaredFields();
        System.out.println(o);
        System.out.printf("%d fields:%n", fields.length);
        for (Field field : fields) {
            System.out.printf("%s %s %s %s%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName(), field.get(o));
        }
    }
}
