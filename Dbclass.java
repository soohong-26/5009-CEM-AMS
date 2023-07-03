package ams;

import java.sql.*;

public class Dbclass {
    private String url = "jdbc:mysql://localhost:3306/ams";
    private String userName = "root";
    private String password = "joah123";
    private Statement stmt = null;
    private Connection con = null;
    
    public Dbclass (){
        userName = "root";
        password = "joah123";
    }
    
    public Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, password);
//            if (con != null){
//                System.out.println("Successfully connected!");
//            }
        } catch (Exception e){
            System.out.println(e);
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return con;
    }
}
