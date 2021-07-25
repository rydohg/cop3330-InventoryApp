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
    @FXML TextField name_field;
    @FXML TextField serial_field;
    @FXML TextField value_field;
    @FXML AnchorPane error_box;
    private static String name, serial;
    private static double value;
    private static boolean cancelled = false;

    public static Item display(){
        try {
            Parent root = FXMLLoader.load(ItemDataDialog.class.getResource("/dialog.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Enter Item Details");
            stage.setScene(scene);
            stage.showAndWait();
            if (!cancelled){
                return new Item(name, serial.trim(), value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void submitOnClick(MouseEvent event){
        name = name_field.getText();
        serial = serial_field.getText();
        value = Double.parseDouble(value_field.getText());
        if (checkUniqueSerial(serial) && name.length() <= 256 && name.length() >= 2){
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } else {
            error_box.setStyle("-fx-opacity: 1");
            error_box.setStyle("-fx-background-color: #ff6859");
        }
    }

    private boolean checkUniqueSerial(String serial) {
        InventoryModel model = InventoryModel.getInstance();
        for (Item i : model.getItems()) {
            if (i.serial.equals(serial.trim())){
                return false;
            }
        }
        return true;
    }

    public void closeOnClick(MouseEvent event){
        cancelled = true;
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
