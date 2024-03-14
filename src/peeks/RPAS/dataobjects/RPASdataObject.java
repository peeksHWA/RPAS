/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.dataobjects;

/**
 *
 * @author Peeks
 */
public abstract class RPASdataObject 
{
    
    // mmeber vars. 
    // ID is PK from db
    public int       iID         = -1;
    // row count equstes to the row in the UI grsi that this object represents
    public int       iRowID      = -2;
    
    
    public int getID()
    {
        return iID;
    }
    
    public int getRowID()
    {
        return iRowID;
    }  
    
    public String toString()
    {
        return super.toString();
    }
}
