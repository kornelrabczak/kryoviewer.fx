package com.thecookiezen.kryoviewerfx.presentation.test;

import com.thecookiezen.kryoviewerfx.presentation.Tower;
import com.thecookiezen.kryoviewerfx.presentation.light.LightView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TestPresenter implements Initializable {

    @FXML
    Label message;

    @FXML
    Pane lightsBox;

    @Inject
    Tower tower;

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
    }

    public void createLights() {
        for (int i = 0; i < 255; i++) {
            final int red = i;
            LightView view = new LightView((f) -> red);
            view.getViewAsync(lightsBox.getChildren()::add);
        }
    }

    public void launch() {
        message.setText("Date: " + date + " -> " + prefix + tower.readyToTakeoff() + happyEnding + theVeryEnd);
    }
}
