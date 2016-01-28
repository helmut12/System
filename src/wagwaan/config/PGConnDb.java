package wagwaan.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Referenced classes of package com.kelsas.datacrypto.db:
//            DatabaseConnection

public class PGConnDb {
Connection con;
    public PGConnDb()  throws SQLException
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
            credentials.load(new FileInputStream("postgres.props"));
        }
        catch(IOException e)
        {
            throw new SQLException("Can't read postgres.props.");
        }
        Properties props = new Properties();
        props.setProperty("user", credentials.getProperty("username"));
        props.setProperty("password", credentials.getProperty("password"));
        con = DriverManager.getConnection((new StringBuilder()).append("jdbc:postgresql://").append(credentials.getProperty("host")).append(":").append(credentials.getProperty("port")).append("/").append(credentials.getProperty("database")).toString(), props);
    }
}