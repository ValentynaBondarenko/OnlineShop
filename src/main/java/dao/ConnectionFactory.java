package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private final static String DB_URL = "db.db_url";
    static final String JDBC_DRIVER = "db.jdbc_driver";

    private final static String USER = "db.user";
    private final static String PASS = "db.pass";

    Properties properties = new Properties();

    void loadFileWithDatabaseProperties() {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {
            if (fileInputStream != null) {
                properties.load(fileInputStream);
            } else {
                throw new IOException("property File'" + fileInputStream + "'not found");
            }
        } catch (IOException exception) {
            throw new RuntimeException("Cant find file with properties");
        }
    }

    public Connection connectionToDatabase() {
        loadFileWithDatabaseProperties();
        try {
            Class.forName(properties.getProperty(JDBC_DRIVER));

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC_DRIVER is not running");
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection(properties.getProperty(DB_URL), properties.getProperty(USER), properties.getProperty(PASS));
        } catch (SQLException e) {
            throw new RuntimeException("Cant connect to database. Check your root, password or user`s name in the database");
        }

        return connection;
    }
}
