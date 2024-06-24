package org.jabreftest.test.javafxreproducer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import org.tinylog.Logger;

public class MinimalJavaFXApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a TextField
        TextField textField = new TextField();

        textField.addEventHandler(KeyEvent.ANY, event -> {
            Logger.info("Key pressed: code: {}", event.getCode());
            Logger.info("Key pressed: event: {}", event);
            Logger.info("Condition: {}", event.getCode() == KeyCode.Z);
            if ((event.getCode() == KeyCode.Z || event.getCode() == KeyCode.Y) && event.isShortcutDown()) {
                event.consume();
                Logger.info("Undo/Redo event consumed");
            }
        });


        // Create a layout and add the TextField to it
        VBox layout = new VBox(10); // 10 is the spacing between elements
        layout.getChildren().add(textField);

        // Create a scene with the layout
        Scene scene = new Scene(layout, 300, 200);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Set the title of the stage (window)
        primaryStage.setTitle("Minimal JavaFX App");

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
