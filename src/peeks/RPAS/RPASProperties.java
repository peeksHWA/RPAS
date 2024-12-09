/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import peeks.RPAS.UI.RPASUI;

/**
 *
 * @author Peeks
 *
 * CLass loads values from config.properties
 */
public class RPASProperties 
{
    
    // public vars - canr be bothered with set and gets
    public String dbConnectionStr = "";
    public String dbJDBCdriver = "";
    public String dbUser = "";
    public String dbPwd = "";
    public String jdbcDriver = "";
    public String dbList = "";
    
    // private member vars
    private InputStream inputStream;
    private String sFilename = null;
    
    /*
    *   Member functions
    */
    // CTOR
    public RPASProperties( String sConfigFile ) 
    {
       sFilename = sConfigFile; 
    }
    
    // get property vals
    public boolean getPropValues() throws IOException 
    {

        if ( sFilename == null )
        {
            System.out.println( "No config filename provided!" );
            return false;
        }
        
        boolean bSuccess = true;
        try 
        {
            Properties prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream( sFilename );

            if (inputStream != null) 
            {
                prop.load(inputStream);
            } 
            else 
            {
                throw new FileNotFoundException("property file '" + sFilename + "' not found in the classpath");
            }
            
            // get the property values from properties files
            dbConnectionStr = prop.getProperty("database_connection_str");
            dbJDBCdriver = prop.getProperty("JDCB_driver");
            dbUser = prop.getProperty("db_user");
            dbPwd = prop.getProperty("db_pwd");
            jdbcDriver = prop.getProperty("jdbc_driver");
            dbList = prop.getProperty("dbListFile");
            
            RPASUI.logger.error("Loaded properties file");
        } 
        catch (Exception e) 
        {
                System.out.println("Exception: " + e);
                bSuccess = false;
        } 
        finally 
        {
            // close file IO stream down
            inputStream.close();
        }
        
        return bSuccess;
    }
}
