/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ryan Doherty
 */
package ucf.assignments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class ItemListCell extends ListCell<Item> {
    private final ListView<Item> listView;
    private FXMLLoader mLoader;
    private Item item;
    @FXML
    private Label item_name;
    @FXML
    private Label serial_num;
    @FXML
    private Label value_label;
    @FXML
    private AnchorPane view;

    public ItemListCell(ListView<Item> listView) {
        this.listView = listView;
    }

    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        this.item = item;
        // Make empty items empty
        if (empty || item == null) {
            setStyle("-fx-background-color: #045D56");
            setText(null);
            setGraphic(null);
        } else {
            if (mLoader == null) {
                // Load list item fxml file
                mLoader = new FXMLLoader(getClass().getResource("/list_item.fxml"));
                // Set this class as controller for each list item
                mLoader.setController(this);
                try {
                    mLoader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Initialize values
            item_name.setText(item.name);
            serial_num.setText(item.serial);
            value_label.setText(String.format("$%.2f", item.value));
            // Fix spacing between list items
            setStyle("-fx-padding: 0");
            setText(null);
            setGraphic(view);
        }
    }

    public void editOnClick() {
        // Edit item on successful dialog
        Item newItem = ItemDataDialog.display();
        if (newItem != null) {
            item.name = newItem.name;
            item.serial = newItem.serial;
            item.value = newItem.value;
            refreshList();
        }
    }

    public void deleteOnClick() {
        // Delete the current item from the model then refresh the list
        InventoryModel model = InventoryModel.getInstance();
        model.getItems().remove(item);
        refreshList();
    }

    private void refreshList() {
        // We don't need to sort since removing something will keep the rest in order
        InventoryModel data = InventoryModel.getInstance();
        listView.getItems().clear();
        for (Item item : data.getItems()) {
            listView.getItems().add(item);
        }
    }
}
