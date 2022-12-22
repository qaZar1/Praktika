import java.sql.*;

public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    public static void Conn() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:country.db");
        statmt = conn.createStatement();
    }

    public static void ReadDB() throws SQLException {
        resSet = statmt.executeQuery("SELECT min(data.users) AS min_users, country.name AS name\n" +
                "FROM data\n" +
                "JOIN subregion\n" +
                "ON data.subregion_id = subregion.id\n" +
                "JOIN country\n" +
                "ON data.country_id = country.id\n" +
                "WHERE subregion.name = 'Eastern Europe'");

        while (resSet.next()){
            String nameCountry = resSet.getString("name");
            System.out.println("Страна с наименьшем кол-вом зарегистрированных пользователей в интернете - " + nameCountry);
        }
    }

    public static void CloseDB() throws SQLException {
        conn.close();
        statmt.close();
        resSet.close();
    }
}
