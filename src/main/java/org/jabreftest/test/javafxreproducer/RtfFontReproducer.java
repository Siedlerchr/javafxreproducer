package org.jabreftest.test.javafxreproducer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jfx.incubator.scene.control.richtext.CodeArea;
import jfx.incubator.scene.control.richtext.TextPos;
import jfx.incubator.scene.control.richtext.model.RtfFormatHandler;
import jfx.incubator.scene.control.richtext.skin.RichTextAreaSkin;

public class RtfFontReproducer extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        CodeArea area = new CodeArea();
        area.setFont(Font.font("Monospaced", 16));
        area.setText("    aligned text");
        stage.setScene(new Scene(new StackPane(area), 500, 120));
        stage.show();
        area.applyCss();
        area.layout();

        var resolver = ((RichTextAreaSkin) area.getSkin()).getStyleResolver();
        String rtf = (String) RtfFormatHandler.getInstance().copy(
                area.getModel(), resolver, TextPos.ZERO, TextPos.ofLeading(0, 16));
        System.out.println("CodeArea font: " + area.getFont());
        System.out.println("RTF output:\n" + rtf);
        System.out.println("Expected: monospace font; actual: RTF defaults to Helvetica.");
        if (getParameters().getRaw().contains("--verify")) {
            if (!rtf.contains("\\fswiss Helvetica") || rtf.contains("\\fmodern")) {
                throw new AssertionError("The default RTF font is no longer Helvetica: inspect output");
            }
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
