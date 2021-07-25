package ucf.assignments;

import javafx.scene.Node;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class DialogController {
    private void close(ActionEvent event){
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}
