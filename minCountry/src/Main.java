import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        conn.Conn();
        conn.ReadDB();
        conn.CloseDB();
    }
}
