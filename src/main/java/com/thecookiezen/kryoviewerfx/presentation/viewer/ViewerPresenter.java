package com.thecookiezen.kryoviewerfx.presentation.viewer;

import com.thecookiezen.kryoviewerfx.bussiness.schema.Schema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.SchemaDeserializer;
import com.thecookiezen.kryoviewerfx.bussiness.schema.Schemas;
import com.thecookiezen.kryoviewerfx.presentation.drawable.DrawableFactory;
import com.thecookiezen.kryoviewerfx.presentation.drawable.TableDrawable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;
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

    private Map<String, Schema> schemasMap;

    private final Supplier<Collection<File>> findFiles = () -> {
        File directory = new File("./target/classes/schemas");
        return listFiles(directory, new String[]{"json"}, true);
    };

    private DrawableFactory drawableFactory = new DrawableFactory();;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schemaView.setEditable(false);
        schemaView.setWrapText(true);

        registerSchemas();
    }

    private void registerSchemas() {
        schemasMap = new Schemas(new SchemaDeserializer(), findFiles).getSchemas();

        schemas.setItems(FXCollections.observableArrayList(schemasMap.keySet()));

        schemas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Schema schema = schemasMap.get(newValue);
            schemaView.setText(schema.getPrettyPrint());
        });
    }

    public void drawDataInTableFromFile() throws FileNotFoundException, IllegalAccessException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String schemaName = schemas.getSelectionModel().getSelectedItem();
            TableDrawable drawable = drawableFactory.createDrawable(schemasMap.get(schemaName));
            drawable.draw(selectedFile, objectsTable);
            message.setText("File : " + selectedFile.getName());
        }
    }
}
