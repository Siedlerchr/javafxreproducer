package org.jabreftest.test.javafxreproducer;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Comparator;

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
                createTitledPane("Custom", false, 200.0, 200.0),
                new Label("Table without using SortedList"),
                createTableView(false),
                new Label("Table using SortedList"),
                createTableView(true));

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

    private TableView<Data> createTableView(boolean useSortedList) {
        TableView<Data> tableView = new TableView<>();

        TableColumn<Data, Integer> indexColumn = new TableColumn<>("#");
        indexColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().index()));
        indexColumn.setSortable(false);

        TableColumn<Data, Integer> column1 = new TableColumn<>("Col1");
        column1.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().val1()));

        TableColumn<Data, Integer> column2 = new TableColumn<>("Col2");
        column2.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().val2()));

        tableView.getColumns().add(indexColumn);
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        ObservableList<Data> data = FXCollections.observableArrayList(
                new Data(0, 5, 4),
                new Data(1, 1, 7),
                new Data(2, 5, 2),
                new Data(3, 5, 3)
        );

        if (useSortedList) {
            SortedList<Data> sortedData = new SortedList<>(data);

            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        } else {
            tableView.setItems(data);
        }
        return tableView;
    }

    public record Data(int index, int val1, int val2) {
    }

    public static void main(String[] args) {
        launch();
    }
}
