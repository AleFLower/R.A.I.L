package dao;

import exception.PasswordReadException;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbConnection {
    private static Connection connection;
    private static final String URL="jdbc:mysql://localhost:3306/Track";  //da gestire in modo piu sicuro?
    private static final String USERNAME="root";
    private DbConnection() throws SQLException, IOException {
        connectToDb();
    }
    private static void connectToDb() throws IOException, SQLException {
        Properties properties=new Properties();
        InputStream is= new FileInputStream("application.properties");
        properties.load(is);
        connection=DriverManager.getConnection(URL,USERNAME,(String)properties.get("password"));
    }

    public static Connection getInstance() throws SQLException, PasswordReadException {
        try {
            if (connection == null) {
                new DbConnection();
            }
        }catch (SQLException e){
            throw new SQLException("Cannot connect to the database\nRetry later");
        } catch (IOException e) {
            throw new PasswordReadException("Cannot extract the password\nof connection to DB");
        }
        return connection;
    }
    public static void closeConnection()  {
        if (connection != null) {
            try {
                connection.close();
            }catch (SQLException e){
                //ho provato tutti i casi possibili e non viene mai lanciata un eccezione del tipo sql exception
                //da questo metodo
                System.exit(-2);
            }
        }
    }
}
