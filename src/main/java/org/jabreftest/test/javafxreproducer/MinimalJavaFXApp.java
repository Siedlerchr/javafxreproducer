package org.jabreftest.test.javafxreproducer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import org.tinylog.Logger;
import com.tobiasdiez.easybind.EasyBind;


public class MinimalJavaFXApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField area = new TextField();

        SimpleStringProperty textProperty = new SimpleStringProperty();

        EasyBind.subscribe(textProperty, newText -> {
            area.setText(newText);
        });
        EasyBind.subscribe(textInputControl.textProperty(), viewModelTextProperty::set);


        VBox layout = new VBox(10); // 10 is the spacing between elements
        layout.getChildren().add(area);

        // Create a scene with the layout
        Scene scene = new Scene(layout, 300, 200);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Set the title of the stage (window)
        primaryStage.setTitle("Minimal JavaFX App");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}