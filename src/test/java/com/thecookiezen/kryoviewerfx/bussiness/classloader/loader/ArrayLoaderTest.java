package com.thecookiezen.kryoviewerfx.bussiness.classloader.loader;

import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.BooleanSchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;
import org.junit.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ArrayLoaderTest {

    Loadable<ArraySchema> sut = new ArrayLoader();

    @Test
    public void shouldLoadPrimitiveArrayType() {
        // given
        ArraySchema schema = new ArraySchema();
        schema.setItemsSchema(new BooleanSchema());

        // when
        Class<?> loaded = sut.loadFromSchema(schema);

        // then
        assertThat(loaded).isEqualTo(LinkedList.class);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldFailForNestedArray() {
        // given
        ArraySchema schema = new ArraySchema();
        schema.setItemsSchema(new ArraySchema());

        // when & then
        sut.loadFromSchema(schema);
    }

    @Test
    public void shouldLoadObjectForArraySchema() {
        // given
        ArraySchema schema = new ArraySchema();
        ObjectSchema objectSchema = new ObjectSchema();
        objectSchema.name = "com.thecookiezen.TestClass";
        objectSchema.type = "object";
        schema.setItemsSchema(objectSchema);

        // when
        sut.loadFromSchema(schema);

        // then
        try {
            Class<?> clazz = Class.forName(objectSchema.name);
            assertThat(clazz.getSimpleName()).isEqualTo("TestClass");
        } catch (ClassNotFoundException e) {
            fail();
        }
    }
}