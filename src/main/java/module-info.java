module org.jabreftest.test.javafxreproducer {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.jabreftest.test.javafxreproducer to javafx.fxml;
    exports org.jabreftest.test.javafxreproducer;
}