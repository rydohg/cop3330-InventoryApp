/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ryan Doherty
 */
package ucf.assignments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemDataDialog {
    private static String name, serial;
    private static double value;
    private static boolean cancelled = false;
    @FXML
    TextField name_field;
    @FXML
    TextField serial_field;
    @FXML
    TextField value_field;
    @FXML
    AnchorPane error_box;

    public static Item display() {
        try {
            // Load the dialog as a modal and wait until it is closed by submit or close
            Parent root = FXMLLoader.load(ItemDataDialog.class.getResource("/dialog.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Enter Item Details");
            stage.setScene(scene);
            stage.showAndWait();
            if (!cancelled) {
                return new Item(name, serial.trim(), value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void submitOnClick(MouseEvent event) {
        name = name_field.getText();
        serial = serial_field.getText();
        value = Double.parseDouble(value_field.getText());
        // Check the validity of the data
        if (checkValidSerial(serial) && name.length() <= 256 && name.length() >= 2) {
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } else {
            // Show the error dialog
            error_box.setStyle("-fx-opacity: 1");
            error_box.setStyle("-fx-background-color: #ff6859");
        }
    }

    public boolean checkValidSerial(String serial) {
        // Check it is the right length and is alphanumeric
        if (serial.length() != 10 || !serial.matches("[a-zA-Z0-9]*")) {
            return false;
        }
        // Check if in model already
        InventoryModel model = InventoryModel.getInstance();
        for (Item i : model.getItems()) {
            if (i.serial.equals(serial.trim())) {
                return false;
            }
        }
        return true;
    }

    public void closeOnClick(MouseEvent event) {
        // Close dialog gracefully
        cancelled = true;
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
