package fr.insa.sdbd.MissionGestionMS.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {

    private  final String URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_065";
    private  final String USERNAME = "projet_gei_065";
    private  final String PASSWORD = "aeQuail5"; 

    public  Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public  void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void printTableNames() {
        Connection connection = null;

        try {
            connection = getConnection();

            // Get metadata about the database
            DatabaseMetaData metaData = connection.getMetaData();

            // Specify the schema (database) and type of object (table)
            String[] types = {"TABLE"};
            ResultSet resultSet = metaData.getTables(null, null, "%", types);

            // Print the table names
            System.out.println("Tables in the database:");
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println(tableName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }
}
    