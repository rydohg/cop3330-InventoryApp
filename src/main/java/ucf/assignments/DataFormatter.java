package ucf.assignments;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DataFormatter {
    public static ArrayList<Item> fromJson(String data) {
        Gson gson = new Gson();
        ItemList list = gson.fromJson(data, ItemList.class);
        return list.items;
    }
}
