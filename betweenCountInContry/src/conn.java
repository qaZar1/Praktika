import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
        resSet = statmt.executeQuery("SELECT data.users AS users, data.population AS population, country.name AS country\n" +
                "FROM data\n" +
                "JOIN country\n" +
                "ON data.country_id = country.id");

        System.out.println("Страны, процент зарегистрированных пользователей в интеренете которых находится в промежутке от 75% до 85% - ");
        while (resSet.next()){
            Long users = Long.valueOf(resSet.getString("users"));
            Long population = Long.valueOf(resSet.getString("population"));
            String country = resSet.getString("country");
            double percent = 0.0;
            if (population == 0L) {
                break;
            } else {
                percent = (double)users * 100.0 / (double)population;
            }

            BigDecimal result;
            if ((int)percent < 10){
                MathContext context = new MathContext(2, RoundingMode.HALF_EVEN);
                result = new BigDecimal(percent, context);
            } else if ((int)percent > 10){
                MathContext context = new MathContext(3, RoundingMode.HALF_EVEN);
                result = new BigDecimal(percent, context);
            } else {
                result = new BigDecimal(0);
            }

            if (result.doubleValue() > 75 && result.doubleValue() < 85){
                System.out.println(country);
            }
        }
    }

    public static void CloseDB() throws SQLException {
        conn.close();
        statmt.close();
        resSet.close();
    }
}