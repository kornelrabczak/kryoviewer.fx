package com.thecookiezen.kryoviewerfx.bussiness.deserializer;

import com.thecookiezen.kryoviewerfx.bussiness.classloader.KryoWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

public class ObjectProvider implements DataProvider {

    private final KryoWrapper kryoWrapper = new KryoWrapper();

    @Override
    public ObservableList<Object> deserialize(File from, Optional<Class<?>> type) throws FileNotFoundException {
        Object t = kryoWrapper.deserializeFromFile(from, type.get());
        return FXCollections.observableArrayList(t);
    }
}
