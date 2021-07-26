package ucf.assignments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryModelTest {
    InventoryModel model;
    @Test
    void readDataFromFile() {
        File file = new File("test.json");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("{\"items\":[{\"name\":\"asdfasdf\",\"serial\":\"XXXXXXXXXA\",\"value\":12.0},{\"name\":\"fasdfawef\",\"serial\":\"XXXXXXXXXB\",\"value\":321.0},{\"name\":\"sdagrgesrgs\",\"serial\":\"XXXXXXXXXC\",\"value\":1.0},{\"name\":\"Test\",\"serial\":\"XXXXXXXXXD\",\"value\":321.0},{\"name\":\"Test Again\",\"serial\":\"XXXXXXXXXE\",\"value\":100.5},{\"name\":\"fasdfwea\",\"serial\":\"XXXXXXXXXF\",\"value\":0.11}]}");
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        model.getItems().clear();
        model.readDataFromFile(file);
        assertTrue(model.getItems().size() > 0);
    }

    @Test
    void writeDataToFile(){
        File json = new File("test.json");
        model.writeDataToFile(json);
        assertTrue(json.exists());
        File tsv = new File("test.txt");
        model.writeDataToFile(tsv);
        assertTrue(tsv.exists());
        File html = new File("test.html");
        model.writeDataToFile(html);
        assertTrue(html.exists());
    }

    @Test
    void readWrittenFiles(){
        File json = new File("test.json");
        model.writeDataToFile(json);
        assertTrue(json.exists());
        File tsv = new File("test.txt");
        model.writeDataToFile(tsv);
        assertTrue(tsv.exists());
        File html = new File("test.html");
        model.writeDataToFile(html);
        assertTrue(html.exists());

        model.getItems().clear();
        model.readDataFromFile(json);
        assertTrue(model.getItems().size() > 0);

        model.getItems().clear();
        model.readDataFromFile(tsv);
        assertTrue(model.getItems().size() > 0);

        model.getItems().clear();
        model.readDataFromFile(html);
        assertTrue(model.getItems().size() > 0);

    }

    @BeforeEach
    void initTestData(){
        model = InventoryModel.getInstance();
        model.getItems().clear();
        model.getItems().add(new Item("Test 1", "XXXXXXXXXX", 12.50));
        model.getItems().add(new Item("Test 2", "XXXXXXXXXA", 1.50));
        model.getItems().add(new Item("Test 3", "XXXXXXXXXB", 100.50));
    }
}