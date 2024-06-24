module org.jabreftest.test.javafxreproducer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.tinylog.api;
    requires org.tinylog.impl;

    opens org.jabreftest.test.javafxreproducer to javafx.fxml;
    exports org.jabreftest.test.javafxreproducer;
}