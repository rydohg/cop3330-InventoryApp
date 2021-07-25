package ucf.assignments;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DataFormatter {
    public static ArrayList<Item> fromJson(String data) {
        Gson gson = new Gson();
        ItemList list = gson.fromJson(data, ItemList.class);
        return list.items;
    }

    public static ArrayList<Item> fromTsv(String data) {
        ArrayList<Item> items = new ArrayList<>();
        for (String line : data.split("\n")) {
            String[] values = line.split("\t");
            items.add(new Item(values[0], values[1], Double.parseDouble(values[2])));
        }

        return items;
    }

    public static String toTsv(ArrayList<Item> list) {
        StringBuilder data = new StringBuilder();
        for (Item i : list) {
            data.append(i.name);
            data.append("\t");
            data.append(i.serial);
            data.append("\t");
            data.append(i.value);
            data.append("\n");
        }
        return data.toString();
    }
}
