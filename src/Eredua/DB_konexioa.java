package Eredua;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;  

public class DB_konexioa {
    private static final String URL = "jdbc:mysql://localhost:3306/bideokluba";  
    private static final String ERABILTZAILEA = "root";  
    private static final String PASAHITZA = "";  

    public static Connection getConexion() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        return DriverManager.getConnection(URL, ERABILTZAILEA, PASAHITZA);   
    }
}