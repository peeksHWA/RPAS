/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.UI.fields;

import java.util.ArrayList;

/**
 *
 * @author Peeks
 */
public class AmountField extends PeeksUIField implements ValidateField
{


    // mmeber vars
    public float    fAmount         = 0;
    String          sValue          = null;
    
    // ctor
    public AmountField ( int iLength, boolean bMandatory )
    {
        //  call  base classs
        super ( iLength, bMandatory );
    }
 
    // base class abstract method
    public boolean validate( Object value )
    {
        bValid = true;
        sValue = (String)value;
        
       if ( sValue == null )
       {
           sErr = "Field value is null";
           bValid = false;
           return bValid;
       }
       
       try
       {
           fAmount = Float.parseFloat( sValue );
       }
       catch( Exception ex )
       {
           sErr = ex.toString();
           System.out.println( sErr );
           bValid = false;
           return bValid;
       }
               
        return bValid;
    }
    
}
