package app.javacrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
    private static final String url = "jdbc:mysql://localhost:3306/javaCrud";
    private static final String user = "root";
    private static final String password = "";

    private static Connection connection = null;

    public static Connection getConnection() {
        if(connection == null){
            try{
                connection = DriverManager.getConnection(url,user,password);
                //System.out.println("Connection established");
            }catch (SQLException e){
                System.out.println("Connection Error " + e.getMessage());
            }
        }
        return connection;
    }

}
