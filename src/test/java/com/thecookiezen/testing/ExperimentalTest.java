package com.thecookiezen.testing;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.Lists;
import com.thecookiezen.kryoviewerfx.bussiness.classloader.ClassLoaderFactory;
import com.thecookiezen.kryoviewerfx.bussiness.classloader.KryoWrapper;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ExperimentalTest {

//    @Test
    public void test() throws IOException, IllegalAccessException {
//        Kryo kryo = new KryoWrapper().getKryo();
//
//        Output output = new Output(new FileOutputStream("file.bin"));
//        TestClass testClass = new TestClass(false, 123, "qwe");
//        kryo.writeObject(output, testClass);
//        output.close();
//
//        final List<ObjectSchema> schemas = new SchemaDeserializer().getSchemaName2ClassMap();
//
//        final ClassLoaderFactory classGenerator = new ClassLoaderFactory();
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
        schema.type = "array";
        Class<?> aClass = new ClassLoaderFactory().fromSchema(schema).getClass();

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
