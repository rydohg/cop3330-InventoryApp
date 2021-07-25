package ucf.assignments;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        switch (sortBy){
            case "Name":
                data.getItems().sort(Comparator.comparing(item -> item.name));
            case "Serial Number":
                data.getItems().sort(Comparator.comparing(item -> item.serial));
                break;
            case "Value":
                data.getItems().sort(Comparator.comparing(item -> item.value));
                break;
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
        if (searchOpen){
            search_pane.setStyle("-fx-opacity: 0;");
            searchOpen = false;
        }
        if (!categoryOpen){
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
        if (!searchOpen){
            if(!categoryOpen){
                category_pane.setStyle("-fx-opacity: 1;");
                category_selector.getSelectionModel().selectedIndexProperty()
                        .addListener(
                                (observable, oldValue, newValue) ->
                                        sortOnChoice(category_selector.getItems().get(newValue.intValue()))
                        );
            }
            search_pane.setStyle("-fx-opacity: 1;");
            searchOpen = true;
        } else {
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
        InventoryModel data = InventoryModel.getInstance();
        list_view.getItems().clear();
        for (Item item : data.getItems()) {
            String itemText = switch (searchField) {
                case "Name" -> item.name;
                case "Serial Number" -> item.serial;
                case "Value" -> String.format("%.2f", item.value);
                default -> "";
            };
            if (itemText.contains(text)){
                list_view.getItems().add(item);
            }
        }
    }

    public void newItemOnClick() {
        Item newItem = ItemDataDialog.display();

    }

    public void sortOnChoice(String choice) {
        if (searchOpen){
            searchField = choice;
        } else {
            sortBy = choice;
        }
    }
}
