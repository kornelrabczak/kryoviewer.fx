package com.thecookiezen.kryoviewerfx.presentation.drawable;

import com.thecookiezen.kryoviewerfx.bussiness.classloader.KryoWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectDrawable implements TableDrawable {

    protected final Class<?> clazz;

    protected final KryoWrapper kryoWrapper = new KryoWrapper();

    public ObjectDrawable(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<TableColumn> getColumns() {
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
    public ObservableList draw(File loadFrom) throws FileNotFoundException {
        Object o = kryoWrapper.deserializeFromFile(loadFrom, clazz);
        return FXCollections.observableArrayList(o);
    }
}
