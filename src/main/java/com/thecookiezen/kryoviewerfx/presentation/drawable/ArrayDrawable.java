package com.thecookiezen.kryoviewerfx.presentation.drawable;

import com.thecookiezen.kryoviewerfx.bussiness.schema.Schema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ArraySchema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.types.ObjectSchema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class ArrayDrawable extends ObjectDrawable {
    public ArrayDrawable(Schema schema) throws ClassNotFoundException {
        super(Class.forName(((ObjectSchema) ((ArraySchema) schema.getSchema()).getItemsSchema()).name));
    }

    @Override
    public ObservableList draw(File loadFrom) throws FileNotFoundException {
        List list = kryoWrapper.deserializeFromFile(loadFrom, LinkedList.class);
        return FXCollections.observableArrayList(list);
    }
}
