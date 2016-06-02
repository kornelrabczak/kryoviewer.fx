package com.thecookiezen.kryoviewerfx.presentation.viewer;

import com.thecookiezen.kryoviewerfx.bussiness.classloader.KryoWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ObjectTable {

    private final Class<?> clazz;

    private final KryoWrapper kryoWrapper = new KryoWrapper();

    public ObjectTable(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<TableColumn> getColumns() {
        Field[] fields = clazz.getDeclaredFields();
        List<TableColumn> tables = new ArrayList<>();
        for (Field field : fields) {
            logField(field);
            TableColumn column = new TableColumn(field.getName());
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                @Override
                public ObservableValue call(TableColumn.CellDataFeatures param) {
                    try {
                        return new ReadOnlyStringWrapper(String.valueOf(field.get(param.getValue())));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            tables.add(column);
        }
        return tables;
    }

    public ObservableList<Object> loadData(File loadFrom) throws FileNotFoundException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        System.out.printf("%d fields:%n", fields.length);
        final Object o = kryoWrapper.deserializeFromFile(loadFrom, clazz);
        for (Field field : fields) {
            System.out.printf("%s %s %s %s%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName(), field.get(o));
        }

        return FXCollections.observableArrayList(o);
    }

    private PrintStream logField(Field field) {
        return System.out.printf("%s %s %s%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName());
    }
}
