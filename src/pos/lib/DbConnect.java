package pos.lib;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class DbConnect {
    
    public static Connection connect(){
           
           String user = "root";
           String user_psw ="";
           Connection conn= null;
        
           try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos", user, user_psw);
     
           }catch(SQLException e){
               System.out.println(e);
           } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        } 
           return  conn;
    }   
}