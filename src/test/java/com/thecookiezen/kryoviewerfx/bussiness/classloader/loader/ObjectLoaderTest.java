package com.thecookiezen.kryoviewerfx.bussiness.classloader.loader;

import com.thecookiezen.kryoviewerfx.bussiness.schema.types.BooleanSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.IntegerSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.StringSchema;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectLoaderTest {

    Loadable<ObjectSchema> sut = new ObjectLoader();

    @Test
    public void shouldLoadSimpleType() {
        // given
        ObjectSchema schema = new ObjectSchema();
        schema.name = "TestClass";
        schema.type = "object";

        schema.properties.put("field_a", new BooleanSchema());

        // when
        Class<?> loaded = sut.loadFromSchema(schema);

        // then
        Field[] fields = loaded.getDeclaredFields();

        assertThat(fields).are(new Condition<>((Predicate<Field>) o -> o.getType().equals(boolean.class), "boolean"));
    }

    @Test
    public void shouldLoadSimpleTypeWithBooleanIntegerAndStringFields() {
        // given
        ObjectSchema schema = new ObjectSchema();
        schema.name = "TestClass2";
        schema.type = "object";

        schema.properties.put("field_a", new BooleanSchema());
        schema.properties.put("field_b", new IntegerSchema());
        schema.properties.put("field_c", new StringSchema());

        // when
        Class<?> loaded = sut.loadFromSchema(schema);

        // then
        Field[] fields = loaded.getDeclaredFields();

        assertThat(fields).areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(boolean.class), "boolean"))
                .areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(String.class), "string"))
                .areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(int.class), "int"));
    }

    @Test
    public void shouldLoadComplexTypeWithObjectBooleanIntegerAndStringFields() {
        // given
        ObjectSchema schema = new ObjectSchema();
        schema.name = "TestClass3";
        schema.type = "object";

        ObjectSchema schema2 = new ObjectSchema();
        schema2.name = "TestClass4";
        schema2.type = "object";

        schema2.properties.put("field_a", new BooleanSchema());
        schema2.properties.put("field_b", new IntegerSchema());
        schema2.properties.put("field_c", new StringSchema());

        schema.properties.put("field_x", schema2);

        // when
        Class<?> loaded = sut.loadFromSchema(schema);

        // then
        Field[] fields = loaded.getDeclaredFields();
        assertThat(fields[0].getType().getDeclaredFields()).areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(boolean.class), "boolean"))
                .areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(String.class), "string"))
                .areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(int.class), "int"));
    }
}