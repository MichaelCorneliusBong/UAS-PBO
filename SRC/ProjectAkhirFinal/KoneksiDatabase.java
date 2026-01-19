package ProjectAkhirFinal;
import java.sql.*;

public class KoneksiDatabase {
    private static final String Database = "jdbc:postgresql://localhost:5400/shoppingList";
    private static final String User = "postgres";
    private static final String Password = "02082006";
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
    Class.forName("org.postgresql.Driver");
    return DriverManager.getConnection(Database, User, Password);
    }
}

