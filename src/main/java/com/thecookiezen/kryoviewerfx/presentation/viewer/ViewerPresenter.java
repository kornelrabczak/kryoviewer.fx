package com.thecookiezen.kryoviewerfx.presentation.viewer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.kryo.KryoWrapper;
import com.thecookiezen.kryoviewerfx.bussiness.rest.SchemaExtractor;
import com.thecookiezen.kryoviewerfx.bussiness.schema.ClassGenerator;
import com.thecookiezen.kryoviewerfx.bussiness.schema.Schemas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schemaView.setEditable(false);
        schemaView.setWrapText(true);

        registerSchemas();
    }

    private void registerSchemas() {
        schemasMap = new Schemas(new SchemaExtractor(), new ClassGenerator()).getSchemas();

        schemas.setItems(FXCollections.observableArrayList(schemasMap.keySet()));

        schemas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            schemaView.setText(getPrettyPrint(newValue));


            Class<?> clazz = schemasMap.get(newValue);
            Field[] fields = clazz.getDeclaredFields();
            List<TableColumn> tables = new ArrayList<>();
            for (Field field : fields) {
                System.out.printf("%s %s %s%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName());
                TableColumn column = new TableColumn(field.getName());
                column.setCellValueFactory(new PropertyValueFactory(field.getName()));
                tables.add(column);
            }
            objectsTable.getColumns().setAll(tables);
        });
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

            Input input = new Input(new FileInputStream(selectedFile));
            Kryo kryo = new KryoWrapper().getKryo();
            final Object o = kryo.readObject(input, clazz);
            input.close();

            Field[] fields = clazz.getDeclaredFields();
            System.out.println(o);
            System.out.printf("%d fields:%n", fields.length);
            for (Field field : fields) {
                System.out.printf("%s %s %s %s%n", Modifier.toString(field.getModifiers()), field.getType().getSimpleName(), field.getName(), field.get(o));
            }

            final ObservableList data = FXCollections.observableArrayList(o);
            objectsTable.setItems(data);

        }
    }
}
