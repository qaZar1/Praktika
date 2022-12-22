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


    public static void ReadDB() throws SQLException
    {
        resSet = statmt.executeQuery("SELECT SUM(data.users) AS users, SUM(data.population) AS population, subregion.name AS subregion\n" +
                "FROM data\n" +
                "JOIN subregion\n" +
                "ON subregion.id = data.subregion_id\n" +
                "GROUP BY subregion.name");
        var values = new double[22];
        var key = new String[22];
        var i = 0;
        while(resSet.next())
        {
            var  users = Long.valueOf(resSet.getString("users"));
            var  population = Long.valueOf(resSet.getString("population"));
            var subregion = resSet.getString("subregion");
            double percent = 0;
            if (population == 0){
                System.out.println("Ошибка в Базе данных");
            } else {
                percent = (double)users * 100 / population;
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

            values[i] = result.doubleValue();
            key[i] = subregion;
            i++;
        }
        Graphs.createChart(Graphs.createDataset(values, key));
    }

    public static void CloseDB() throws SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();
    }
}
