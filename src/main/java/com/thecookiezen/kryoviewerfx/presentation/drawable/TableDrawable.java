package com.thecookiezen.kryoviewerfx.presentation.drawable;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface TableDrawable {
    List<TableColumn> getColumns();

    ObservableList draw(File loadFrom) throws FileNotFoundException;
}
