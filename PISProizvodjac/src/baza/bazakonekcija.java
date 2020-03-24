package baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author toni
 */
public class bazakonekcija {
    
   private static final String host = "jdbc:mysql://uk.sql05.yourwebservers.com:13307/pis?useSSL=false";
    private static final String user = "bruno";
    private static final String pass = "1234";
    
    
    //Connection function
    public static Connection spoji() throws SQLException{
        
        try {
            Connection conn = DriverManager.getConnection(host, user, pass);
            return conn;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Connection class : " + e);
            return null;
        }
    }
    
}
