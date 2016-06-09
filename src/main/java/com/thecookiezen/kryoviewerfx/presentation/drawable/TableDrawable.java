package com.thecookiezen.kryoviewerfx.presentation.drawable;

import com.thecookiezen.kryoviewerfx.bussiness.deserializer.DataProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public abstract class TableDrawable {

    protected final DataProvider dataProvider;

    public TableDrawable(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public void draw(File selectedFile, TableView objectsTable) {
        objectsTable.getColumns().setAll(getColumns());

        ObservableList data;
        try {
            data = getData(selectedFile);
        } catch (FileNotFoundException e) {
            data = FXCollections.emptyObservableList();
        }
        objectsTable.setItems(data);
    }

    protected abstract List<TableColumn> getColumns();

    protected abstract ObservableList getData(File selectedFile) throws FileNotFoundException;
}
