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
public class StringField extends PeeksUIField implements ValidateField
{

    // mmeber vars
    public String sValue = null;
    
    // ctor
    public StringField( int iFieldLen, boolean bMandatory )
    {
        // call base class ctor
        super( iFieldLen,bMandatory );
        
    }
    
     // ctor
    public StringField()
    {
        // call base class ctor
        super();
    }

    // base class abstract method    
    public boolean validate( Object field )
    {
        
        bValid = true;
        sValue = (String)field;
        
        if ( field == null)
        {
            bValid = false;
            sErr = "Field object in null";
            return bValid;
        }
    
        // check if mandatory and if so need a vakue
        if ( bMandField && sValue.length() == 0 )
        {
            bValid = false;
            sErr = "Mandatory field and no value entered";
        }
        else
        {
            // check length does not exceed allowed len
            if ( sValue.length() > iLen )
            {
                bValid = false;
                sErr = "Field length exceed allowable limit of " + iLen;   
            }
        }
    
        return bValid;
    }
}
