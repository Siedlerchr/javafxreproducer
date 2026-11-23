package org.jabreftest.test.javafxreproducer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jfx.incubator.scene.control.richtext.CodeArea;

public class EthiopicFontReproducer extends Application {
    private static final String SAMPLE = "Latin: Hello — Ethiopic: ጸጋየ ገብረመድህን ሀሁ ወይ ፐፑ";

    @Override
    public void start(Stage stage) {
        String family = "Noto Sans Ethiopic";
        boolean available = Font.getFamilies().stream().anyMatch(name -> name.equalsIgnoreCase(family));
        System.out.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        System.out.println("Java: " + System.getProperty("java.version") + "; JavaFX: " + System.getProperty("javafx.runtime.version"));
        System.out.println(family + " available to JavaFX: " + available);

        Label defaultText = new Label(SAMPLE);
        TextField explicitText = new TextField(SAMPLE);
        explicitText.setFont(Font.font(family, 16));
        CodeArea source = new CodeArea();
        source.setFont(Font.font("Monospaced", 16));
        source.setText("@article{test,\n  title = {" + SAMPLE + "}\n}");
        source.setPrefHeight(120);
        System.out.println("Explicit font resolved to: " + explicitText.getFont());
        System.out.println("CodeArea font resolved to: " + source.getFont());

        VBox root = new VBox(8, new Label("Default label (automatic fallback)"), defaultText,
                new Label("Text field (Noto Sans Ethiopic requested)"), explicitText,
                new Label("CodeArea (Monospaced, automatic fallback)"), source);
        root.setStyle("-fx-padding: 16;");
        stage.setScene(new Scene(root, 750, 300));
        stage.setTitle("Ethiopic font fallback reproducer");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
