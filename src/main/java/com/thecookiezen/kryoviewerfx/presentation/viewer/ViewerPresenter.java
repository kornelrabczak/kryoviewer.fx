package com.thecookiezen.kryoviewerfx.presentation.viewer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.classloader.KryoWrapper;
import com.thecookiezen.kryoviewerfx.bussiness.schema.SchemaDeserializer;
import com.thecookiezen.kryoviewerfx.bussiness.classloader.ClassLoaderFactory;
import com.thecookiezen.kryoviewerfx.bussiness.schema.Schemas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;

import static org.apache.commons.io.FileUtils.listFiles;

@SuppressWarnings("unchecked")
public class ViewerPresenter implements Initializable {

    @FXML
    Label message;

    @FXML
    Pane lightsBox;

    @FXML
    TextArea schemaView;

    @FXML
    TableView objectsTable;

    @FXML
    private ListView<String> schemas;

    private Map<String, Class<?>> schemasMap;

    private final KryoWrapper kryoWrapper = new KryoWrapper();

    private final Supplier<Collection<File>> findFiles = () -> {
        File directory = new File("./target/classes/schemas");
        return listFiles(directory, new String[]{"json"}, true);
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schemaView.setEditable(false);
        schemaView.setWrapText(true);

        registerSchemas();
    }

    private void registerSchemas() {
        schemasMap = new Schemas(new SchemaDeserializer(), new ClassLoaderFactory(), findFiles).getSchemaName2ClassMap();

        schemas.setItems(FXCollections.observableArrayList(schemasMap.keySet()));

        schemas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            schemaView.setText(getPrettyPrint(newValue));
            objectsTable.getColumns().setAll(getTableColumns(newValue));
            objectsTable.setItems(FXCollections.emptyObservableList());
        });
    }

    private List<TableColumn> getTableColumns(String schemaName) {
        Class<?> clazz = schemasMap.get(schemaName);
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

    private PrintStream logField(Field field) {
        return System.out.printf("%s %s %s%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName());
    }

    private String getPrettyPrint(String newValue) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonSchema jsonSchema = objectMapper.generateJsonSchema(schemasMap.get(newValue));
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public void launch() throws FileNotFoundException, IllegalAccessException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            message.setText("File : " + selectedFile.getName());

            String className = schemas.getSelectionModel().getSelectedItem();
            Class<?> clazz = schemasMap.get(className);

            Field[] fields = clazz.getDeclaredFields();
            System.out.printf("%d fields:%n", fields.length);
            final Object o = kryoWrapper.deserializeFromFile(selectedFile, clazz);
            for (Field field : fields) {
                System.out.printf("%s %s %s %s%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName(), field.get(o));
            }

            final ObservableList data = FXCollections.observableArrayList(o);
            objectsTable.setItems(data);
        }
    }
}
