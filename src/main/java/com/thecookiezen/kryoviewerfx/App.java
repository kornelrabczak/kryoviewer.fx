package com.thecookiezen.kryoviewerfx;

import com.airhacks.afterburner.injection.Injector;
import com.thecookiezen.kryoviewerfx.test.TestView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        /*
         * Properties of any type can be easily injected.
         */
        LocalDate date = LocalDate.of(4242, Month.JULY, 21);
        Map<Object, Object> customProperties = new HashMap<>();
        customProperties.put("date", date);
        /*
         * any function which accepts an Object as key and returns
         * and return an Object as result can be used as source.
         */
        Injector.setConfigurationSource(customProperties::get);

        System.setProperty("happyEnding", " Enjoy the flight!");
        TestView appView = new TestView();
        Scene scene = new Scene(appView.getView());
        stage.setTitle("followme.fx");
        final String uri = getClass().getClassLoader().getResource("app.css").toExternalForm();
        System.out.println(uri);
        scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
