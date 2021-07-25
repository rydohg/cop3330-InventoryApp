package ucf.assignments;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MainController {
    @FXML
    private ListView<Item> list_view;

    public void initialize(){
        list_view.setCellFactory(lv -> new ItemListCell(list_view));
        refreshList();
    }

    private void refreshList(){
        // Refreshes view to match the data singleton and can filter by complete or not
        InventoryModel data = InventoryModel.getInstance();
        list_view.getItems().clear();
        for (Item item : data.getItems()) {
            list_view.getItems().add(item);
        }
    }
}
