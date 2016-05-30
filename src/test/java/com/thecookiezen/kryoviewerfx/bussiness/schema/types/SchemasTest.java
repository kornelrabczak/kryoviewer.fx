package com.thecookiezen.kryoviewerfx.bussiness.schema.types;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SchemasTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void deserializeBooleanSchema() throws IOException {
        ClassJsonSchema booleanSchema = getSchema("booleanSchema.json");

        assertThat(booleanSchema.isPrimitive()).isTrue();
        assertThat(booleanSchema.getType()).isEqualTo(boolean.class);
    }

    @Test
    public void deserializeIntegerSchema() throws IOException {
        ClassJsonSchema integerSchema = getSchema("integerSchema.json");

        assertThat(integerSchema.isPrimitive()).isTrue();
        assertThat(integerSchema.getType()).isEqualTo(int.class);
    }

    @Test
    public void deserializeStringSchema() throws IOException {
        ClassJsonSchema stringSchema = getSchema("stringSchema.json");

        assertThat(stringSchema.isPrimitive()).isTrue();
        assertThat(stringSchema.getType()).isEqualTo(String.class);
    }

    @Test
    public void deserializeArraySchema() throws IOException {
        ClassJsonSchema arraySchema = getSchema("arraySchema.json");

        assertThat(arraySchema).isInstanceOf(RootSchema.class);
        assertThat(arraySchema.isPrimitive()).isFalse();
        assertThat(arraySchema.isArray()).isTrue();
        assertThat(((ArraySchema) arraySchema).getItemsSchema()).isInstanceOf(BooleanSchema.class);
        assertThat(arraySchema.getType()).isEqualTo(LinkedList.class);
    }

    private ClassJsonSchema getSchema(String fileName) throws IOException {
        URL resource = getClass().getClassLoader().getResource(fileName);
        assert resource != null;
        File file = new File(resource.getPath());
        return mapper.readValue(file, ClassJsonSchema.class);
    }

}