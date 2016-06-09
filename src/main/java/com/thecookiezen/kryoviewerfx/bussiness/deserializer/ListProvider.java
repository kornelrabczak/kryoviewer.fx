package com.thecookiezen.kryoviewerfx.bussiness.deserializer;

import com.thecookiezen.kryoviewerfx.bussiness.classloader.KryoWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ListProvider implements DataProvider {

    private final KryoWrapper kryoWrapper = new KryoWrapper();

    @Override
    public ObservableList deserialize(File from, Optional<Class<?>> ignore) throws FileNotFoundException {
        List list = kryoWrapper.deserializeFromFile(from, LinkedList.class);
        return FXCollections.observableArrayList(list);
    }
}
