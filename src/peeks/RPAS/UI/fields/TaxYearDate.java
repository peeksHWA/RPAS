/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.RPAS.UI.fields;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import peeks.RPAS.UI.RPASUI;
import peeks.RPAS.dataobjects.TaxYear;

/**
 *
 * @author Peeks
 */
public class TaxYearDate extends DateField
{
    
    // member vars
    public Date date    = null;
    public int  iTaxID  = -1;
    
    // ctor
    public TaxYearDate( boolean bMandatory )
    {
        super( bMandatory);
    }
    
    
    // get the tax year that the date falls in to
    // return ID for TaxYear Object that the dat fals into or -1 if not found
    public int getConfiguredTaxYear( ArrayList aTaxYears )
    {
        iTaxID = -1;
        TaxYear taxYear = null;
        SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // ccheck member var
        if ( !bValid )
            return iTaxID;
        
        try
        {
            // create date object of rdat string
            // need to add 20 to year as UI only takes 2 digits
            String sTemp = sDate.substring(0, 6) + "20" + sDate.substring( 6 );

            RPASUI.logger.debug( "Item date being checked against tax years = " + sTemp );
            date  = dFormat.parse( sTemp );
        }
        catch ( Exception ex )
        {
            System.out.println( ex.toString() );
            return iTaxID;
        }
            
        // move through tax years and find matching tax year    
        if ( aTaxYears != null )
        {
            for ( int i = 0; i < aTaxYears.size(); i++ )
            {
               taxYear = (TaxYear)aTaxYears.get( i );
               
               // check member var date and see which tax year it fits into 
               if ( date.after( taxYear.dStartdate ) && date.before(taxYear.dEndDate) )
               {
                   // found tax year to match entered date
                   iTaxID = taxYear.iID;
                   break;
               }
            }
        }
        
        return iTaxID;
        
    }
}
