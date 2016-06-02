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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArrayTable {

    private final KryoWrapper kryoWrapper = new KryoWrapper();

    public List<TableColumn> getColumns() {
        List<TableColumn> tables = new ArrayList<>();
        TableColumn column = new TableColumn("value");
        column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures param) {
                return new ReadOnlyStringWrapper("test");
            }
        });
        tables.add(column);
        return tables;
    }

    public ObservableList<Object> loadData(File loadFrom) throws FileNotFoundException {
        final Object o = kryoWrapper.deserializeFromFile(loadFrom, LinkedList.class);

        return FXCollections.observableArrayList(o);
    }
}
