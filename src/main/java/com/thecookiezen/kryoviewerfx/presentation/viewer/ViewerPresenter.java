package com.thecookiezen.kryoviewerfx.presentation.viewer;

import com.thecookiezen.kryoviewerfx.bussiness.schema.Schema;
import com.thecookiezen.kryoviewerfx.bussiness.schema.SchemaDeserializer;
import com.thecookiezen.kryoviewerfx.bussiness.schema.Schemas;
import com.thecookiezen.kryoviewerfx.presentation.drawable.DrawableFactory;
import com.thecookiezen.kryoviewerfx.presentation.drawable.TableDrawable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
    private TableDrawable tableDrawable;

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
            tableDrawable = DrawableFactory.createDrawable(schema);
            objectsTable.getColumns().setAll(tableDrawable.getColumns());
            objectsTable.setItems(FXCollections.emptyObservableList());
        });
    }

    public void loadDataFromFile() throws FileNotFoundException, IllegalAccessException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            message.setText("File : " + selectedFile.getName());
            ObservableList draw = tableDrawable.draw(selectedFile);
            objectsTable.setItems(draw);
        }
    }
}
