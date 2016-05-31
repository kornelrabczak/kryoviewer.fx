package com.thecookiezen.kryoviewerfx.bussiness.schema;

import com.thecookiezen.kryoviewerfx.bussiness.classloader.ClassLoaderFactory;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class SchemasTest {

    private Schemas schemas;

    @Test
    public void should_load_array_and_object_schemas_from_file_list() {
        // given
        Supplier<Collection<File>> collectionSupplier = () -> Arrays.asList(getFile("arraySchema.json"), getFile("objectSchema.json"));

        // when
        schemas = new Schemas(new SchemaDeserializer(), new ClassLoaderFactory(), collectionSupplier);

        // then
        assertThat(schemas.getSchemaName2ClassMap()).containsKeys("arraySchema", "objectSchema");
        assertThat(schemas.getSchemaName2ClassMap().get("arraySchema")).isEqualTo(LinkedList.class);
        assertThat(schemas.getSchemaName2ClassMap().get("objectSchema").getSimpleName()).isEqualTo("TestObject");
    }

    @Test(expected = IllegalStateException.class)
    public void should_fail_when_not_supported_schema() {
        // given
        Supplier<Collection<File>> collectionSupplier = () -> Arrays.asList(getFile("arraySchema.json"), getFile("booleanSchema.json"));

        // when & then
        schemas = new Schemas(new SchemaDeserializer(), new ClassLoaderFactory(), collectionSupplier);
    }

    private File getFile(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        assert resource != null;
        return new File(resource.getPath());
    }
}