package com.thecookiezen.kryoviewerfx.presentation.drawable;

import com.thecookiezen.kryoviewerfx.bussiness.classloader.KryoWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrimitiveDrawable implements TableDrawable {

    private final KryoWrapper kryoWrapper = new KryoWrapper();

    @Override
    public List<TableColumn> getColumns() {
        List<TableColumn> tables = new ArrayList<>();
        TableColumn<Object, String> column = new TableColumn<>("value");
        column.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue())));
        tables.add(column);
        return tables;
    }

    @Override
    public ObservableList draw(File loadFrom) throws FileNotFoundException {
        LinkedList list = kryoWrapper.deserializeFromFile(loadFrom, LinkedList.class);
        return FXCollections.observableArrayList(list);
    }
}
