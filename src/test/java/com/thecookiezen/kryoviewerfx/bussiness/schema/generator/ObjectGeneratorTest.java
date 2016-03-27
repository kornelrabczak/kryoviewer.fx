package com.thecookiezen.kryoviewerfx.bussiness.schema.generator;

import com.thecookiezen.kryoviewerfx.bussiness.rest.types.BooleanSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.IntegerSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.StringSchema;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectGeneratorTest {

    TypeGenerator<ObjectSchema> sut = new ObjectTypeGenerator();

    @Test
    public void shouldGenerateSimpleType() {
        // given
        ObjectSchema schema = new ObjectSchema();
        schema.name = "TestClass";
        schema.type = "object";

        schema.properties.put("field_a", new BooleanSchema());

        // when
        Class<?> generate = sut.generate(schema);

        // then
        Field[] fields = generate.getDeclaredFields();

        assertThat(fields).are(new Condition<>((Predicate<Field>) o -> o.getType().equals(boolean.class), "boolean"));
    }

    @Test
    public void shouldGenerateSimpleTypeWithBooleanIntegerAndStringFields() {
        // given
        ObjectSchema schema = new ObjectSchema();
        schema.name = "TestClass";
        schema.type = "object";

        schema.properties.put("field_a", new BooleanSchema());
        schema.properties.put("field_b", new IntegerSchema());
        schema.properties.put("field_c", new StringSchema());

        // when
        Class<?> generate = sut.generate(schema);

        // then
        Field[] fields = generate.getDeclaredFields();

        assertThat(fields).areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(boolean.class), "boolean"))
                .areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(String.class), "string"))
                .areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(int.class), "int"));
    }

    @Test
    public void shouldGenerateComplexTypeWithObjectBooleanIntegerAndStringFields() {
        // given
        ObjectSchema schema = new ObjectSchema();
        schema.name = "TestClass";
        schema.type = "object";

        ObjectSchema schema2 = new ObjectSchema();
        schema2.name = "TestClass2";
        schema2.type = "object";

        schema2.properties.put("field_a", new BooleanSchema());
        schema2.properties.put("field_b", new IntegerSchema());
        schema2.properties.put("field_c", new StringSchema());

        schema.properties.put("field_x", schema2);

        // when
        Class<?> generate = sut.generate(schema);

        // then
        Field[] fields = generate.getDeclaredFields();
        assertThat(fields[0].getType().getDeclaredFields()).areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(boolean.class), "boolean"))
                .areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(String.class), "string"))
                .areAtLeastOne(new Condition<>((Predicate<Field>) o -> o.getType().equals(int.class), "int"));
    }

}