package com.thecookiezen.kryoviewerfx.presentation.viewer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.classloader.ClassLoaderFactory;
import com.thecookiezen.kryoviewerfx.bussiness.schema.SchemaDeserializer;
import com.thecookiezen.kryoviewerfx.bussiness.schema.Schemas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
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
        if (clazz.equals(LinkedList.class)) {
            return new ArrayTable().getColumns();
        }
        return new ObjectTable(clazz).getColumns();
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

            String schemaName = schemas.getSelectionModel().getSelectedItem();
            Class<?> clazz = schemasMap.get(schemaName);
            ObservableList<Object> data;
            if (clazz.equals(LinkedList.class)) {
                data = new ArrayTable().loadData(selectedFile);
            } else {
                data = new ObjectTable(clazz).loadData(selectedFile);
            }
            objectsTable.setItems(data);
        }
    }
}
