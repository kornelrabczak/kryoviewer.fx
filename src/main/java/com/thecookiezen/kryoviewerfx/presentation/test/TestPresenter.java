package com.thecookiezen.kryoviewerfx.presentation.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.thecookiezen.kryoviewerfx.bussiness.rest.SchemaExtractor;
import com.thecookiezen.kryoviewerfx.bussiness.schema.ClassGenerator;
import com.thecookiezen.kryoviewerfx.bussiness.schema.Schemas;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

public class TestPresenter implements Initializable {

    @FXML
    Label message;

    @FXML
    Pane lightsBox;

    @FXML
    TextArea schemaView;

    @FXML
    private ListView<String> schemas;

    @Inject
    private LocalDate date;

    @Inject
    private String prefix;

    @Inject
    private String happyEnding;

    private String theVeryEnd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.theVeryEnd = resources.getString("theEnd");

        schemaView.setEditable(false);
        schemaView.setWrapText(true);

        registerSchemas();
    }

    private void registerSchemas() {
        final Map<String, Class<?>> schemasMap = new Schemas(new SchemaExtractor(), new ClassGenerator()).getSchemas();

        schemas.setItems(FXCollections.observableArrayList(schemasMap.keySet()));

        schemas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            schemaView.setText(getPrettyPrint(schemasMap, newValue));
        });
    }

    private String getPrettyPrint(Map<String, Class<?>> schemasMap, String newValue) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonSchema jsonSchema = objectMapper.generateJsonSchema(schemasMap.get(newValue));
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public void launch() {
        message.setText("Date: " + date + " -> " + prefix + happyEnding + theVeryEnd);
    }
}
