/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peeks.RPAS;

/**
 *
 * @author peeks
 */
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConfigManager {

    // Method to write a list of DatabaseConfig objects to a JSON file
    public static void writeToJson(List<DatabaseConfig> configList, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(filePath), configList);
            System.out.println("DatabaseConfig list written to JSON file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read a list of DatabaseConfig objects from a JSON file
    public static List<DatabaseConfig> readFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<DatabaseConfig>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        // Create a few DatabaseConfig objects
        DatabaseConfig config1 = new DatabaseConfig(
                "RPAS",
                "jdbc:mysql://192.168.1.230:3306/RPAS",
                "192.168.1.230",
                "root",
                "Emmab_01",
                "com.mysql.cj.jdbc.Driver",
                "true"
        );

        DatabaseConfig config2 = new DatabaseConfig(
                "RPAS-TEST",
                "jdbc:postgresql://localhost:3306/RPAS-TEST",
                "127.0.0.1",
                "cparkinson",
                "Emmab_01",
                "com.mysql.cj.jdbc.Driver",
                "false"
        );

        // Create a list of DatabaseConfig objects
        List<DatabaseConfig> configList = new ArrayList<>();
        configList.add(config1);
        configList.add(config2);

        // Path to save JSON file
        String filePath = "/Users/peeks/config/RPAS.json";

        // Write the list to a JSON file
        writeToJson(configList, filePath);

        // Read the list back from the JSON file
        List<DatabaseConfig> loadedConfigList = readFromJson(filePath);
        System.out.println("Loaded Configs from JSON:");
        for (DatabaseConfig config : loadedConfigList) {
            System.out.println(config);
        }
    }
}

