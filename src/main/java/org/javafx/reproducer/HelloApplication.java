package org.javafx.reproducer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import atlantafx.base.theme.NordDark;

public class HelloApplication extends Application {
    private Scene scene;

    @Override
    public void start(Stage stage) {
//        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());

        stage.setTitle("Hello World!");
        Button button = new Button();
        button.setText("Add Overlay");
        button.setOnAction(_ -> addOverlay());

        // Current implementation
        var root = new DialogPane();
        root.setContent(button);

        // Comparison with different Pane
//        var root = new StackPane();
//        root.getChildren().add(button);

        scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void addOverlay() {
        var overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: black; -fx-opacity: 0.75;");

        var originalRoot = scene.getRoot();
        scene.setRoot(overlayPane);

        overlayPane.getChildren().add(originalRoot);
    }
}
