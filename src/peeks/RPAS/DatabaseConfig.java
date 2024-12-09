/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peeks.RPAS;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class DatabaseConfig {
    
    public static final String  ACTIVE = "true";
    
    private String databaseName;
    private String connectionString;
    private String host;
    private String username;
    private String password;
    private String jdbcDriver;
    private String active;

    // Constructor with parameters
    public DatabaseConfig(String databaseName, String connectionString, String host, String username, String password, String jdbcDriver, String active) {
        this.databaseName = databaseName;
        this.connectionString = connectionString;
        this.host = host;
        this.username = username;
        this.password = password;
        this.jdbcDriver = jdbcDriver;
        this.active = active;
    }

    // Default constructor
    public DatabaseConfig() {
    }

    // Getters and Setters
    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    
    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }
    
    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "DatabaseConfig{" +
                "databaseName='" + databaseName + '\'' +
                ", connectionString='" + connectionString + '\'' +
                ", host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", jdbcDriver='" + jdbcDriver + '\'' +
                ", active='" + active + '\'' +
                '}';
    }

    // Method to write the object to a JSON file
    public void writeToJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(filePath), this);
            System.out.println("DatabaseConfig written to JSON file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read the object from a JSON file
    public static DatabaseConfig readFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filePath), DatabaseConfig.class);
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println( "Error opening databse JSON config file : " + filePath );
            return null;
        }
    }

    // Main method for testing
    
    public static void main(String[] args) {
        // Create a DatabaseConfig object
        DatabaseConfig config = new DatabaseConfig(
                "ExampleDB",
                "jdbc:mysql://localhost:3306/exampledb",
                "127.0.0.1",
                "root",
                "password123",
                "com.mysql.cj.jdbc.Driver",
                "true"
        );

        // Path to save JSON file
        String filePath = "/Users/peeks/config/DatabaseConfig.json";

        // Write the object to a JSON file
        config.writeToJson(filePath);

        // Read the object from the JSON file
        DatabaseConfig loadedConfig = DatabaseConfig.readFromJson(filePath);
        System.out.println("Loaded Config from JSON: " + loadedConfig);
    }

}
