package com.thecookiezen.kryoviewerfx.presentation.drawable;

import com.thecookiezen.kryoviewerfx.bussiness.deserializer.DataProvider;
import com.thecookiezen.kryoviewerfx.bussiness.deserializer.ObjectProvider;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObjectDrawable extends TableDrawable {

    protected final Class<?> clazz;

    public ObjectDrawable(Class<?> clazz) {
        super(new ObjectProvider());
        this.clazz = clazz;
    }

    protected ObjectDrawable(Class<?> clazz, DataProvider dataProvider) {
        super(dataProvider);
        this.clazz = clazz;
    }

    @Override
    protected List<TableColumn> getColumns() {
        Field[] fields = clazz.getDeclaredFields();
        List<TableColumn> tables = new ArrayList<>();
        for (Field field : fields) {
            TableColumn<Object, String> column = new TableColumn<>(field.getName());
            column.setCellValueFactory(param -> {
                try {
                    return new ReadOnlyStringWrapper(String.valueOf(field.get(param.getValue())));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            });
            tables.add(column);
        }
        return tables;
    }

    @Override
    protected ObservableList getData(File selectedFile) throws FileNotFoundException {
        return dataProvider.deserialize(selectedFile, Optional.of(clazz));
    }
}
