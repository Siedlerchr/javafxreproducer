// "Full name" cannot be used - otherwise, one gets following error message:
//     Module name 'org.jabreftest.test.javafxreproducer' does not fit the project and source set names; expected name '<optional.prefix.>javafx.reproducer'.
module javafx.reproducer {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.jabreftest.test.javafxreproducer to javafx.fxml;
    exports org.jabreftest.test.javafxreproducer;
}