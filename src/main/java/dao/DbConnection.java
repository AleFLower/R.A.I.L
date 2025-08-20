package dao;

import exception.PasswordReadException;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbConnection {
    private static Connection connection;

    private DbConnection() throws SQLException, IOException {
        connectToDb();
    }

    private static void connectToDb() throws IOException, SQLException {
        Properties properties = new Properties();

        try (InputStream is = new FileInputStream("db.properties")) {
            properties.load(is);
        }


        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        if (url == null || username == null || password == null) {
            throw new IOException("Missing database configuration in db.properties");
        }

        connection = DriverManager.getConnection(url, username, password);
    }

    public static synchronized Connection getInstance() throws SQLException, PasswordReadException {
        try {
            if (connection == null || connection.isClosed()) {
                new DbConnection();
            }
        } catch (SQLException e) {
            throw new SQLException("Cannot connect to the database. Retry later", e);
        } catch (IOException e) {
            throw new PasswordReadException("Cannot read database configuration");
        }
        return connection;
    }

    public static void closeConnection()  {
        if (connection != null) {
            try {
                connection.close();
            }catch (SQLException e){
                System.exit(-2);
            }
        }
    }
}
