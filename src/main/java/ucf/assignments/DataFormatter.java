/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Ryan Doherty
 */
package ucf.assignments;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DataFormatter {
    public static ArrayList<Item> fromJson(String data) {
        // Use gson to convert from JSON
        Gson gson = new Gson();
        ItemList list = gson.fromJson(data, ItemList.class);
        return list.items;
    }

    public static ArrayList<Item> fromTsv(String data) {
        // Convert from TSV format
        ArrayList<Item> items = new ArrayList<>();
        for (String line : data.split("\n")) {
            String[] values = line.split("\t");
            items.add(new Item(values[0], values[1], Double.parseDouble(values[2])));
        }

        return items;
    }

    public static String toTsv(ArrayList<Item> list) {
        // Convert to TSV format
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

    public static String toHtml(ArrayList<Item> list) {
        // Create skeleton for html, add the data in a comment in the header for easy
        // parsing using GSON, then create table with data
        StringBuilder data = new StringBuilder();
        data.append("<!DOCTYPE html><html><head><!--DATA:")
                .append(toJson(list))
                .append("-->")
                .append("<title>Inventory App</title>")
                .append("</head><body><table>");
        for (Item i : list) {
            data.append("<tr><td>")
                    .append(i.name)
                    .append("</td><td>")
                    .append(i.serial)
                    .append("</td><td>")
                    .append(i.value)
                    .append("</td></tr>");
        }
        data.append("</table></body></html>");
        return data.toString();
    }

    public static String toJson(ArrayList<Item> items) {
        // Use GSON to convert to JSON
        Gson gson = new Gson();
        return gson.toJson(new ItemList(items));
    }

    public static ArrayList<Item> fromHtml(String data) {
        // Use DATA comment to decode html file
        return fromJson(data.substring(data.indexOf("<!--DATA:") + 9, data.indexOf("-->")));
    }
}