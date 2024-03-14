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
public class DateField extends PeeksUIField implements ValidateField
{

    // consts
    public final static int LENGTH = 8;
    public final static String DELIM = "/";
   
    public String sDate = null;
    
    // ctor
    public DateField ( boolean bMandatory )
    {
       bMandField = bMandatory;
    }
 
    // interface method
    public boolean validate( Object value )
    {
        bValid = true;
        
        String sTemp = null;
        String sVal = (String)value;
        
        int iLength = sVal.length();
        if ( sVal == null || iLength != LENGTH)
        {
            bValid = false;
            return bValid;
        }    
         
        
                    
        // first two char should be 'dd'
        sTemp = sVal.substring(0,2);
        
        // convert to num
        try
        {
            int iDay = Integer.parseInt( sTemp );
            
            System.out.printf("day = %d%n", iDay);
            
            // month
            sTemp = sVal.substring(3,5);
            int iMonth = Integer.parseInt( sTemp );
            
            System.out.printf("month = %d%n", iMonth);
            
            // year
            sTemp = sVal.substring(6,8);
            int iYear = Integer.parseInt( sTemp );
            
            System.out.printf("year = %d%n", iYear);
            
            // if got her day, month and year are numbers
            // check delimeters
            if ( !(sVal.substring(2, 3)).equals( DELIM )  )
            {
                sErr  = "DateField : bad first delim";
                System.out.printf(sErr);
                bValid = false;
                return false;
            }
            if ( !(sVal.substring(5, 6)).equals( DELIM )  )
            {
                sErr  = "DateField : bad second delim";
                System.out.printf(sErr);
                bValid = false;
                return false;
            }
            
            // ok now just validate actual calendar day, days i month, months in year, etc
            if ( iMonth < 0 || iMonth > 12 )
            {
                sErr = "Invalid month specified";
                System.out.printf("bad month = %d%n", iMonth); 
                bValid = false;
                return false;
            }   
            
            // days in month
            if (    !(                                                 //
                    (iMonth == 1  && ( iDay > 0 && iDay < 32 ))  ||    //
                    (iMonth == 2  && ( iDay > 0 && iDay < 30 ))  ||    //
                    (iMonth == 3  && ( iDay > 0 && iDay < 32 ))  ||    //
                    (iMonth == 4  && ( iDay > 0 && iDay < 31 ))  ||    //
                    (iMonth == 5  && ( iDay > 0 && iDay < 32 ))  ||    //
                    (iMonth == 6  && ( iDay > 0 && iDay < 31 ))  ||    //
                    (iMonth == 7  && ( iDay > 0 && iDay < 32 ))  ||    //
                    (iMonth == 8  && ( iDay > 0 && iDay < 32 ))  ||    //
                    (iMonth == 9  && ( iDay > 0 && iDay < 31 ))  ||    //
                    (iMonth == 10 && ( iDay > 0 && iDay < 32 ))  ||    //
                    (iMonth == 11 && ( iDay > 0 && iDay < 31 ))  ||    //
                    (iMonth == 12 && ( iDay > 0 && iDay < 32 ))        //
                    )
               )
            {
                sErr = "Invalid number of days specified for month";
                System.out.printf("%d is an invalid num days for Month %d%n", iDay, iMonth); 
                bValid = false;
                return false;
            }     
        }
        catch( Exception ex )
        {
            sErr = ex.toString();
            System.out.println( sErr );
            bValid = false;
            return bValid;
        }
        
        sDate = (String)value;
        
        return bValid;
    }
    
}
