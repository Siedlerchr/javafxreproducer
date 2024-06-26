package org.jabreftest.test.javafxreproducer;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Dialog<String> alert = new Dialog<>();
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you! With some longer text to show the issue");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.initOwner(stage.getOwner());
        alert.setResizable(true);

        DialogPane dlgPane = new DialogPane();
        dlgPane.setPrefHeight(476.0);
        dlgPane.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.APPLY);
        VBox vbox = new VBox();
        vbox.setPrefWidth(400);
        vbox.setPrefHeight(200);

        FlowPane otherPane = new FlowPane();
        otherPane.setPrefWidth(100.0);
        otherPane.setPrefHeight(200.0);

        for (int i = 0; i < 10; i++) {
            Button button = new Button("Button with long" + i);
            otherPane.getChildren().add(button);
        }
        TitledPane otherTitledPane = new TitledPane();
        otherTitledPane.setContent(otherPane);
        otherTitledPane.setText("other");
        otherTitledPane.setCollapsible(true);
        otherTitledPane.setExpanded(false);

        otherTitledPane.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
            if (isNowExpanded) {
                alert.setHeight(alert.getHeight() + otherPane.getHeight());
            } else {
                alert.setHeight(alert.getHeight() - otherPane.getHeight());
            }
        });

        vbox.getChildren().addAll(createTitledPane("Recommmend", false, 50.0, 100.0),
                otherTitledPane,
                createTitledPane("Custom", false, 200.0, 200.0));

        TextField textField = new TextField();
        vbox.getChildren().add(textField);

        dlgPane.setContent(vbox);

        alert.setDialogPane(dlgPane);
        alert.showAndWait();
    }

    private Node createTitledPane(String titleText, Boolean collapsible, double height, double width) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(width);
        flowPane.setPrefHeight(height);

        for (int i = 0; i < 10; i++) {
            Button button = new Button("Button with long" + i);
            flowPane.getChildren().add(button);
        }
        TitledPane titledPane = new TitledPane();
        titledPane.setContent(flowPane);
        titledPane.setText(titleText);
        titledPane.setCollapsible(collapsible);
        titledPane.setExpanded(!collapsible);

        return titledPane;
    }

    public static void main(String[] args) {
        launch();
    }
}
