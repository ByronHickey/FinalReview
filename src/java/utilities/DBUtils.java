package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class DBUtils {

    // TODO: Change the following line to provide your c0xxxxxx student number
    final private static String studentNumber = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        String jdbc = "jdbc:mysql://ipro:3306/" + studentNumber + "-java";
        String user = studentNumber + "-java";
        String pass = studentNumber;
        return DriverManager.getConnection(jdbc, user, pass);
    }
}
