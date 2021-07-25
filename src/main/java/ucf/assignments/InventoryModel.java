/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ryan Doherty
 */
package ucf.assignments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryModel {
    private static InventoryModel model;
    private ArrayList<Item> items;

    private InventoryModel() {
        items = new ArrayList<>();
    }

    public static InventoryModel getInstance() {
        if (model == null) {
            model = new InventoryModel();
        }
        return model;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void readDataFromFile(File file) {
        if (file != null && file.exists()) {
            StringBuilder fileContents = new StringBuilder();
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    fileContents.append(scanner.nextLine());
                    fileContents.append('\n');
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String data = fileContents.toString();
            String extension = getExtension(file.getPath());
            System.out.println(extension);
            switch (extension) {
                case ".json":
                    items = DataFormatter.fromJson(data);
                    break;
                case ".txt":
                    items = DataFormatter.fromTsv(data);
                    break;
                case ".html":
                    items = DataFormatter.fromHtml(data);
                    break;
            }
        }
    }

    public void writeDataToFile(File file) {
        if (file != null) {
            String extension = getExtension(file.getPath());
            String dataString = switch (extension) {
                case ".json" -> DataFormatter.toJson(items);
                case ".txt" -> DataFormatter.toTsv(items);
                case ".html" -> DataFormatter.toHtml(items);
                default -> "";
            };

            FileWriter writer;
            try {
                file.createNewFile();
                writer = new FileWriter(file);
                writer.write(dataString);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getExtension(String path) {
        return path.substring(path.lastIndexOf("."));
    }

}
