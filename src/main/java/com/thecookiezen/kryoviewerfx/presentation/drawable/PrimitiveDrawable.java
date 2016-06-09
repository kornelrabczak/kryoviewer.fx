package com.thecookiezen.kryoviewerfx.presentation.drawable;

import com.thecookiezen.kryoviewerfx.bussiness.deserializer.ListProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrimitiveDrawable extends TableDrawable {

    public PrimitiveDrawable() {
        super(new ListProvider());
    }

    @Override
    public List<TableColumn> getColumns() {
        List<TableColumn> tables = new ArrayList<>();
        TableColumn<Object, String> column = new TableColumn<>("value");
        column.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue())));
        tables.add(column);
        return tables;
    }

    @Override
    protected ObservableList getData(File selectedFile) throws FileNotFoundException {
        return dataProvider.deserialize(selectedFile, Optional.empty());
    }
}
