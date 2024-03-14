/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.UI.fields;

/**
 *
 * @author Peeks
 */
public class PeeksUIField 
{
    
    // member vars
    public String   sErr        = null;
    public boolean  bValid      = false;
    boolean         bMandField  = false;
    int             iLen        = 0;
            
    public PeeksUIField()
    {
    }
    
    public PeeksUIField( int iFieldLen, boolean bMandatory )
    {
        iLen = iFieldLen;
        bMandField = bMandatory;
    }
}
