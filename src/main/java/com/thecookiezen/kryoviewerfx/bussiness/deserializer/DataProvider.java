package com.thecookiezen.kryoviewerfx.bussiness.deserializer;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

public interface DataProvider {
    ObservableList deserialize(File from, Optional<Class<?>> optionalType) throws FileNotFoundException;
}
