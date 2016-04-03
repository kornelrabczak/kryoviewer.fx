package com.thecookiezen.kryoviewerfx.bussiness.schema.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.ObjectSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.types.StringSchema;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ArrayTypeGeneratorTest {


    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void shouldDeserializeArrayOfStrings() throws IOException {
        List<String> arrayOfStrings = objectMapper.readValue(loadArrayOfStringFromFile(), TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));

        System.out.println(arrayOfStrings);
    }

    @Test
    public void shouldDeserializeArrayOfObjects() throws IOException, NoSuchFieldException, IllegalAccessException {
        ObjectSchema schema = new ObjectSchema();
        schema.name = "TestClass";
        schema.type = "object";

        schema.properties.put("name", new StringSchema());
        schema.properties.put("address", new StringSchema());
        schema.properties.put("id", new StringSchema());

        Class<?> generate = new ObjectTypeGenerator().generate(schema);

        CollectionLikeType valueTypeRef = TypeFactory.defaultInstance().constructCollectionLikeType(List.class, generate);
        List<Object> users = objectMapper.readValue(loadArrayOfObjectsFromFile(), valueTypeRef);

        for (Object o : users) {
            System.out.println(o.getClass());
            System.out.println(o.getClass().getField("name").get(o));
        }
    }

    private InputStream loadArrayOfObjectsFromFile() {
        return getClass().getClassLoader().getResourceAsStream("arrayOfObjects.json");
    }

    private InputStream loadArrayOfStringFromFile() {
        return getClass().getClassLoader().getResourceAsStream("arrayOfStrings.json");
    }
}