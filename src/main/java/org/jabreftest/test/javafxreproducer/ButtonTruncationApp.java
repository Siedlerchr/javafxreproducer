package org.jabreftest.test.javafxreproducer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonTruncationApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button("Open Buggy Dialog");
        btn.setOnAction(e -> showBuggyDialog());

        VBox root = new VBox(btn);
        // Simulate the global JabRef font scaling
        root.setStyle("-fx-font-size: 10pt;");

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("JavaFX Truncation Reproducer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showBuggyDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Clean up entries");
        DialogPane dialogPane = dialog.getDialogPane();

        // 1. Add buttons that are prone to truncation
        ButtonType copyType = new ButtonType("Copy Version", ButtonBar.ButtonData.LEFT);
        ButtonType cleanUpType = new ButtonType("Clean up", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialogPane.getButtonTypes().addAll(copyType, cleanUpType, cancelType);

        // 2. Set the content (simulating the CleanUp dialog)
        dialogPane.setContent(new Label("When font is 10pt, 'Clean up' becomes 'Clean u'"));

        // Apply the same global font to the dialog
        dialogPane.setStyle("-fx-font-size: 10pt;");

        /* * UNCOMMENT THE BLOCK BELOW TO TEST THE FIX
         */
        /*
        dialog.setOnShowing(event -> {
            for (ButtonType type : dialogPane.getButtonTypes()) {
                Button button = (Button) dialogPane.lookupButton(type);
                if (button != null) {
                    ButtonBar.setButtonUniformSize(button, false);
                    button.setMinWidth(javafx.scene.layout.Region.USE_PREF_SIZE);
                }
            }
            // Shrink-wrap the window to the new button widths
            dialogPane.getScene().getWindow().sizeToScene();
        });
        */

        dialog.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
