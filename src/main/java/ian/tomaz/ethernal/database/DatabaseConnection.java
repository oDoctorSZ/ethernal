package ian.tomaz.ethernal.database;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static String host;
    public static String port;
    public static String username;
    public static String password;
    public static String database;
    private Connection connection;

    private final String connectionString = "jdbc:mysql://" + DatabaseConnection.host + ":" +
            DatabaseConnection.port + "/" + DatabaseConnection.database;

    //private final String connectionString = "jdbc:mysql://" + DatabaseConnection.username + ":" + DatabaseConnection.password + "@" +
            //DatabaseConnection.host + ":" + DatabaseConnection.port + "/" + DatabaseConnection.database;

    public DatabaseConnection() {
        try {
            connect();
            Bukkit.getConsoleSender().sendMessage("Database has been connected!");

        } catch (Exception x) {
            Bukkit.getConsoleSender().sendMessage("Failed to connect database :(");
            x.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void connect() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(connectionString, DatabaseConnection.username, DatabaseConnection.password);

    }

    public void disconnect() {

        try {
            if (!connection.isClosed()) {
                getConnection().close();
            }
        } catch (SQLException x) {
            Bukkit.getConsoleSender().sendMessage("Failed to disconnect database :(");
            x.printStackTrace();
        }

    }

}
