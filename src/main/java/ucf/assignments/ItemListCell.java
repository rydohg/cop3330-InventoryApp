package ucf.assignments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class ItemListCell extends ListCell<Item> {
    private FXMLLoader mLoader;
    private ListView<Item> listView;
    @FXML private Label item_name;
    @FXML private Label serial_num;
    @FXML private Label value_label;
    @FXML private AnchorPane view;

    public ItemListCell(ListView<Item> listView) {
        this.listView = listView;
    }

    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        // Make empty items empty
        if (empty || item == null) {
            setStyle("-fx-background-color: #045D56");
            setText(null);
            setGraphic(null);
        } else {
            if (mLoader == null){
                // Load list item fxml file
                mLoader = new FXMLLoader(getClass().getResource("/list_item.fxml"));
                // Set this class as controller for each list item
                mLoader.setController(this);
                try {
                    mLoader.load();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            item_name.setText(item.name);
            serial_num.setText(item.serial);
            value_label.setText(String.format("$%.2f", item.value));
            setStyle("-fx-padding: 0");
            setText(null);
            setGraphic(view);
        }
    }
}
