/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peeks.RPAS;

import java.util.List;

/**
 *
 * @author peeks
 */
public class DatabaseConfigList
{
    
    // mmeber vars
    List<DatabaseConfig> m_dbList = null;   // list thta hols the Dataase config obj creted from loading JSON file
    int m_active = -1;                      // -1 means no active entry
    
    
    // ctor
    public DatabaseConfigList( List<DatabaseConfig> databaseList, int iActive )
    {
        m_dbList = databaseList;
        m_active = iActive;
    }
    
    // ctor
    public DatabaseConfigList( List<DatabaseConfig> databaseList)
    {
        m_dbList = databaseList;
    }
    
    // get the object that is active - this means selected in UI database menu
    // first object is at index 0 in list
    public DatabaseConfig getActiveDb()
    {
        // if active object is -1 then no active object. Also chewck list is valid ands has entries
        if ( m_active == -1 || m_dbList == null || m_dbList.size() == 0 )
            return null;
        /*
        for (DatabaseConfig config : m_dbList) 
        {
            System.out.println(config);
        }
        */
        
        return m_dbList.get(m_active);
        
    }
    
    // get a db config object so cna popilate UI
    public DatabaseConfig getConfigItem ( int iVal )
    {
        if ( m_dbList == null )
            return null;
        
        DatabaseConfig item = null;
        try
        {
            item = m_dbList.get(iVal);      
        }
        catch ( Exception ex )
        {
            ex.printStackTrace(); 
        }
         return item;
    }
    
    // get active item in lis
    public int getActive()
    {
        return m_active;
    } 
       
    // get active item in lis
    public void setActive( int active )
    {
        m_active = active;
    }            
    
    // how many items in list
    public int getListCount()
    {
        if ( m_dbList == null )
                return 0;
        
        return m_dbList.size();
    }
}
