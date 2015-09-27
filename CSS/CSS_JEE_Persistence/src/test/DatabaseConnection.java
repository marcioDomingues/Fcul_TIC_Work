package test;

import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;

public class DatabaseConnection {

    static {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static final String URL = "jdbc:mysql://localhost:3306/css_proj_jpa";
    public static final String USER = "css";
    public static final String PASSWORD = "css";

    public static final Destination DESTINATION = new DriverManagerDestination(URL, USER, PASSWORD);

}
