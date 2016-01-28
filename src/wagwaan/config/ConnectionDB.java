/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wagwaan.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author maurice
 */
public class ConnectionDB {
  private  Connection con;
  private static ConnectionDB instance;

    private ConnectionDB() {
        dbConnection();
    }

    public static ConnectionDB getInstance() {
        if(instance==null){
            instance=new ConnectionDB();
        }
        return instance;
    }

    public static void setInstance(ConnectionDB instance) {
        ConnectionDB.instance = instance;
    }
   
    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
private void dbConnection(){
    try
    {
        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch(ClassNotFoundException e)
        {
            throw new SQLException("Postgres database driver not found");
        }
        Properties credentials = new Properties();
        try
        {
            credentials.load(new FileInputStream("Sys.properties"));
        }
        catch(IOException e)
        {
            throw new SQLException("Can't read Sys,properties.");
        }
        Properties props = new Properties();
        props.setProperty("user", credentials.getProperty("username"));
        props.setProperty("password", credentials.getProperty("password"));
        
        String url=(new StringBuilder()).append("jdbc:postgresql://").append(credentials.getProperty("host")).append(":").append(credentials.getProperty("port")).append("/").append(credentials.getProperty("database")).toString();
        
        con = DriverManager.getConnection(url, props);
        if(con!=null){
            System.out.println("CONNECTION SUCCESSFUL");
        }
        else{
            System.out.println("UNABLE TO CONNECT");
        }
    }
        catch(SQLException ex)
        {
          Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
          JOptionPane.showMessageDialog(null, "Could not read the sys.properties file. \n Ensure the parameters in the props file are correct");
          System.exit(0);
        }
    }
}