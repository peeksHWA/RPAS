/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.database;

import peeks.RPAS.UI.RPASUI;



/**
 *
 * @author Peeks
 */
public class DatabaseConnectionParams 
{

    protected String jdbcDriver = null;
    protected String connectionString = null;
    protected String userName = null;
    protected String password = null;
    
    
    
/* CTOR
 */
    public DatabaseConnectionParams( String driver, String conStr, String user, String pwd)
    {
        
        jdbcDriver = driver;
        connectionString = conStr;
        userName = user;
        password = pwd;
    }        
    
    
    
}
