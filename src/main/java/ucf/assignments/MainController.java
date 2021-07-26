/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ryan Doherty
 */
package ucf.assignments;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Comparator;

public class MainController {
    @FXML
    private ListView<Item> list_view;
    @FXML
    private AnchorPane category_pane;
    @FXML
    private AnchorPane search_pane;
    @FXML
    private TextField search_box;
    @FXML
    private ChoiceBox<String> category_selector;
    private boolean categoryOpen = false;
    private boolean searchOpen = false;
    private String sortBy = "Name";
    private String searchField = "Name";

    public void initialize() {
        list_view.setCellFactory(lv -> new ItemListCell(list_view));
        refreshList();
        category_selector.getItems().add("Name");
        category_selector.getItems().add("Serial Number");
        category_selector.getItems().add("Value");
    }

    private void refreshList() {
        // Refreshes view to match the data singleton and can filter by complete or not
        InventoryModel data = InventoryModel.getInstance();
        list_view.getItems().clear();
        switch (sortBy) {
            case "Name" -> data.getItems().sort(Comparator.comparing(item -> item.name.toLowerCase()));
            case "Serial Number" -> data.getItems().sort(Comparator.comparing(item -> item.serial));
            case "Value" -> data.getItems().sort(Comparator.comparing(item -> item.value));
        }
        for (Item item : data.getItems()) {
            list_view.getItems().add(item);
        }
    }

    public void saveOnClick() {
        // Get file from FileChooser
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Save Location");
        File file = chooser.showSaveDialog(null);
        // Attempt to save to that file
        InventoryModel model = InventoryModel.getInstance();
        model.writeDataToFile(file);
    }

    public void openOnClick() {
        // Get file from FileChooser
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose File Location");
        File file = chooser.showOpenDialog(null);
        // Attempt to read from file
        InventoryModel model = InventoryModel.getInstance();
        model.readDataFromFile(file);
        // Update list view
        refreshList();
    }

    public void sortOnClick() {
        // Selectively hide choicebox/textfield as needed
        if (searchOpen) {
            search_pane.setStyle("-fx-opacity: 0;");
            searchOpen = false;
        }
        if (!categoryOpen) {
            category_pane.setStyle("-fx-opacity: 1");
            category_selector.getSelectionModel().selectedIndexProperty()
                    .addListener(
                            (observable, oldValue, newValue) ->
                                    sortOnChoice(category_selector.getItems().get(newValue.intValue()))
                    );
            categoryOpen = true;
        } else {
            category_pane.setStyle("-fx-opacity: 0;");
            categoryOpen = false;
        }
    }

    public void searchOnClick() {
        // Selectively hide choicebox/textfield as needed
        if (!searchOpen) {
            if (!categoryOpen) {
                category_pane.setStyle("-fx-opacity: 1;");
                // Listen for the user to select an option
                category_selector.getSelectionModel().selectedIndexProperty()
                        .addListener(
                                (observable, oldValue, newValue) ->
                                        sortOnChoice(category_selector.getItems().get(newValue.intValue()))
                        );
            }
            // Show the dropdown
            search_pane.setStyle("-fx-opacity: 1;");
            searchOpen = true;
        } else {
            // Hide if already open
            category_pane.setStyle("-fx-opacity: 0;");
            search_pane.setStyle("-fx-opacity: 0;");
            categoryOpen = false;
            searchOpen = false;
            refreshList();
        }
    }

    public void searchOnType() {
        search(search_box.getText());
    }

    private void search(String text) {
        // Selectively add things from the model to the list view if they match the query
        // I used contains() but maybe I should use startsWith()?
        InventoryModel data = InventoryModel.getInstance();
        list_view.getItems().clear();
        for (Item item : data.getItems()) {
            String itemText = switch (searchField) {
                case "Name" -> item.name;
                case "Serial Number" -> item.serial;
                case "Value" -> String.format("%.2f", item.value);
                default -> "";
            };
            if (itemText.contains(text)) {
                list_view.getItems().add(item);
            }
        }
    }

    public void newItemOnClick() {
        // Use dialog to get/verify new Item then add it
        Item newItem = ItemDataDialog.display();
        InventoryModel model = InventoryModel.getInstance();
        if (newItem != null) {
            model.getItems().add(newItem);
            refreshList();
        }
    }

    public void sortOnChoice(String choice) {
        // Keep track of what's open to keep the right things on screen then
        // refresh to put new sort in to place
        if (searchOpen) {
            searchField = choice;
        } else {
            sortBy = choice;
        }
        refreshList();
    }
}
