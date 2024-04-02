
package com.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class DBConnection {
    private static Connection conn;

    public static Connection getConn() {
        return conn;
    }
    public static void closeConnection(){
        try{
            conn.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Connot close Connection");
            ex.printStackTrace();
        }
    }
    static{
        try{
            conn=DriverManager.getConnection("jdbc:oracle:thin:@//LAPTOP-CVA9M52B:1521/xe","happytummy","happytummy");
            JOptionPane.showMessageDialog(null,"Connected successfully");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Connot open Connection");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
}
