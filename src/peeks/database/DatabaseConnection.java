/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peeks.database;

//imports
import peeks.RPAS.UI.RPASUI;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import peeks.RPAS.dataobjects.DatabaseData;
import peeks.RPAS.dataobjects.Expense;
import peeks.RPAS.dataobjects.Property;
import peeks.RPAS.dataobjects.TaxYear;
import peeks.RPAS.dataobjects.MortgagePayment;
import peeks.RPAS.dataobjects.RentalIncome;
import peeks.RPAS.dataobjects.TaxBand;




/**
 *
 * @author Peeks
 * class to manage db connection and all db interaction
 */
public class DatabaseConnection 
{
    
    protected DatabaseConnectionParams  dbConParams  = null;
    protected Connection                dbConnection = null;
    protected boolean                   bConnected  = false;
    
    // ctor
    public DatabaseConnection( DatabaseConnectionParams params )
    {
        dbConParams = params;
    }
    
    // connect to to DB
    public boolean connect()
    {
        if ( dbConParams.jdbcDriver == null )
        {
            RPASUI.logger.debug( "No JDBC driver specified");
            return bConnected;
        }
        if ( dbConParams.connectionString == null )
        {
            RPASUI.logger.debug( "No databaseconnection string specified specified");
            return bConnected;
        }
        if ( dbConParams.userName == null )
        {
            RPASUI.logger.debug( "No database username specified specified");
            return bConnected;
        }
        if ( dbConParams.password == null )
        {
            RPASUI.logger.debug( "No database password specified");
            return bConnected;
        }
        
        // set driver by creating instance
        try 
        {
            Class.forName(dbConParams.jdbcDriver).newInstance();
            RPASUI.logger.debug( "JDBC driver instance created" );
        } 
        catch (Exception ex) 
        {
            // handle the error
            RPASUI.logger.debug( "Exception thrown creating JDBC driver instance :" + ex.toString() );
            return bConnected;
        }
        
        // ok now connect to DB
        try
        {
            // connect
            RPASUI.logger.debug( "Attempting ot connect to DB with conStr =" + dbConParams.connectionString + " user=" + dbConParams.userName + " pwd = " + dbConParams.password );
            dbConnection = DriverManager.getConnection( dbConParams.connectionString, dbConParams.userName, dbConParams.password );
            RPASUI.logger.debug( "Succesffuly connected to DB" );   
        }
        catch ( SQLException sqlEx )
        {
            RPASUI.logger.debug( "Exception thrown connecting to DB : " + sqlEx.toString() );
            return bConnected;
        }

        bConnected = true;
        return bConnected;
    }
    
    // disconnect to to DB
    public void disConnect()
    {
        
        if ( dbConnection != null )
        {
            try
            {
                dbConnection.close();
            }
            catch( SQLException sqlEx )
            {
              RPASUI.logger.error( "Exception thrown closing db connection : " + sqlEx.toString() );  
            }
        }
        
        bConnected = false;

    }
    
    
    // execute generic SQL update statement
    // will execute any sql statemnt but we do not check for results set so cannot
    // be used to retrieve data
    // returns:
    // -1 if sql error
    // num of records updated in sql successful
    public int executeUpdateSQLStatement( String sql )
    {

        
        Statement stmt = null;
        ResultSet rs = null;
        int iUpdateRowCount = -1;

        // do we have a connection
        if ( !bConnected )
        {
            if ( !connect() )
                return iUpdateRowCount;
        }
        try 
        {
            
            RPASUI.logger.debug( "SQL EXECUTE :" + sql );
            stmt = dbConnection.createStatement();
            stmt.execute( sql );
            
            // find out how may rows were updated
            iUpdateRowCount  = stmt.getUpdateCount();
            RPASUI.logger.error( "UPDATE COUNT = " + iUpdateRowCount + " on SQL :: " + sql );
        }
        catch (SQLException ex)
        {
            // handle any errors
            RPASUI.logger.error( "SQLException: " + ex.getMessage() );
            RPASUI.logger.error("SQLState: " + ex.getSQLState() );
            RPASUI.logger.error("VendorError: " + ex.getErrorCode());
            iUpdateRowCount = -1;
        }
        finally 
        {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (stmt != null) 
            {
                try 
                {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
            
            // if connect to db close db connection
            if ( bConnected )
                disConnect();
        }
        
        return iUpdateRowCount;
    }

    // execiute SQL agains db to load expenses from db EXPENSES table
    public boolean getDBExpenses ( DatabaseData dbData )
    {
        
        if ( !bConnected )
        {
            RPASUI.logger.error( "Cannot get expenses as no db connection");
            return bConnected;
        }
        
        // clear any existing expense objects
        dbData.clearExpenses();
        
        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            Expense exp = null;
            
            stmt = dbConnection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Expenses");

            // move to first row in results set
            // wil lreturn false when after last row
            while ( rs.next() )
            {       
                exp = new Expense();
                exp.iID = rs.getInt("ID");
                exp.sTaxYear = rs.getString("TAX_YEAR");
                exp.expDate = rs.getDate("DATE");
                exp.sDesc = rs.getString("DESCRIPTION");
                exp.sSupplier = rs.getString("SUPPLIER");
                exp.dValue = rs.getFloat("VALUE");
                exp.sReciept = rs.getString("RECEIPT");
                exp.sReceiptLink = rs.getString("RECEIPT_LINK");
                exp.sComment = rs.getString("COMMENT");
                exp.iPropID = rs.getInt("PROPERTY_ID");
                exp.iTaxID = rs.getInt("TAX_YEAR_ID");
                
                // RPASUI.logger.debug( exp.toString() );       
                
                // add new object to the databasedata object
                dbData.addExpense( exp );
            }
            
            // close record set
            rs.close();
        }
        catch ( SQLException err ) 
        {
            RPASUI.logger.debug( "DB execpetion thrown getting expenses : " + err.toString() );
            return false;
        }
        return true;
    }
    
    // execiute SQL agains db to load property details from db Property table
    public boolean getDBProperties ( DatabaseData dbData )
    {
        
        if ( !bConnected )
        {
            RPASUI.logger.error( "Cannot get properties as no db connection");
            return bConnected;
        }
        
        // clear any existing expense objects
        dbData.clearProperties();
        
        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            Property prop = null;
            
            stmt = dbConnection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Property");

            // move to first row in results set
            // wil lreturn false when after last row
            while ( rs.next() )
            {       
                prop = new Property();
                prop.iID = rs.getInt("ID");
                prop.sAddressLine1 = rs.getString("ADDRESS_LINE1");
                prop.sAddressLine2 = rs.getString("ADDRESS_LINE2");
                prop.sAddressLine3 = rs.getString("ADDRESS_LINE3");
                prop.sCounty = rs.getString("COUNTY");
                prop.sCountry = rs.getString("COUNTRY");
                prop.sPostCode = rs.getString("POST_CODE");
                
                // RPASUI.logger.debug( prop.toString() );
                
                // add new object to the databasedata object
                dbData.addProperty(prop );
            }
            
            // close record set
            rs.close();
            
        }
        catch ( SQLException err ) 
        {
            RPASUI.logger.debug( "DB execpetion thrown getting properties : " + err.toString() );
            return false;
        }
        
        return true;
    }   
    
        
    // execiute SQL agains db to load tax bands details from db table
    public boolean getDBTaxBands ( DatabaseData dbData )
    {
        
        if ( !bConnected )
        {
            RPASUI.logger.error( "Cannot get tax band as no db connection");
            return bConnected;
        }
        
        // clear any existing expense objects
        dbData.clearTaxBands();
        
        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            TaxBand band = null;
            
            stmt = dbConnection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM TAX_BAND");

            // move to first row in results set
            // wil lreturn false when after last row
            while ( rs.next() )
            {       
                band = new TaxBand();
                band.iID = rs.getInt("ID");
                band.iTaxRate = rs.getInt( "TAX_RATE");
                band.sComment = rs.getString("COMMENT");

                // RPASUI.logger.debug( prop.toString() );
                
                // add new object to the databasedata object
                dbData.addTaxBand( band );
            }
            
            // close record set
            rs.close();
            
        }
        catch ( SQLException err ) 
        {
            RPASUI.logger.debug( "DB execpetion thrown getting tax bands : " + err.toString() );
            return false;
        }
        
        return true;
    }       
    
    
    // execiute SQL agains db to load tax years table
    public boolean getDBTaxYears ( DatabaseData dbData )
    {
        
        if ( !bConnected )
        {
            RPASUI.logger.error( "Cannot get TAX_YEARS as no db connection");
            return bConnected;
        }
        
        // clear any existing expense objects
        dbData.clearTaxYears();
        
        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            TaxYear taxYear = null;
            
            stmt = dbConnection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM TAX_YEAR");

            // move to first row in results set
            // wil lreturn false when after last row
            while ( rs.next() )
            {       
                taxYear = new TaxYear();
                taxYear.iID = rs.getInt("ID");
                taxYear.sDesc = rs.getString( "TAX_YEAR_DESC" ); 
                taxYear.dStartdate = rs.getDate("START_DATE");
                taxYear.dEndDate = rs.getDate("END_DATE");
                taxYear.sComment = rs.getString("COMMENT");
                taxYear.sMorgTaxFactor = rs.getFloat( "MORG_TAX_FACTOR");
                
                // RPASUI.logger.debug( taxYear.toString() );
                
                            
                // add new object to the databasedata object
                dbData.addTaxYear(taxYear );
            }
            
            // close record set
            rs.close();
            
        }
        catch ( SQLException err ) 
        {
            RPASUI.logger.debug( "DB execpetion thrown getting properties : " + err.toString() );
            return false;
        }
        return true;
    }
    
    
    // execute SQL agains db to mortgage payments table
    public boolean getMortgagePayments ( DatabaseData dbData )
    {
        
        if ( !bConnected )
        {
            RPASUI.logger.error( "Cannot get mortgage payments as no db connection");
            return bConnected;
        }
        
        // clear any existing expense objects
        dbData.clearMortgagePayments();
        
        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            MortgagePayment morgPay = null;
            
            stmt = dbConnection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Mortgage");

            // move to first row in results set
            // will return false when after last row
            while ( rs.next() )
            {       
                morgPay = new MortgagePayment();
                morgPay.iID = rs.getInt("ID");
                morgPay.sTaxYear = rs.getString("TAX_YEAR");
                morgPay.payDate = rs.getDate("DATE");
                morgPay.dPayment = rs.getFloat("VALUE");
                morgPay.dInterest = rs.getFloat("INTEREST");
                morgPay.sMortgageProvider = rs.getString("PROVIDER");
                morgPay.sComment = rs.getString("COMMENT");
                morgPay.iPropID = rs.getInt("PropertyId");
                morgPay.iTaxID = rs.getInt("TAX_YEAR_ID");
                
                // RPASUI.logger.debug( morgPay.toString() );
                
                            
                // add new object to the databasedata object
                dbData.addMortgagePayment( morgPay );
            }
            
            // close record set
            rs.close();

        }
        catch ( SQLException err ) 
        {
            RPASUI.logger.debug( "DB execpetion thrown getting mortgage payments : " + err.toString() );
            return false;
        }
        return true;
    }
    
    // execute SQL agains db to get RENTAL_INCOME table
    public boolean getRentalIncome ( DatabaseData dbData )
    {
        
        if ( !bConnected )
        {
            RPASUI.logger.error( "Cannot get rental income as no db connection");
            return bConnected;
        }
        
        // clear any existing expense objects
        dbData.clearRentalIncome();
        
        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            RentalIncome income = null;
            
            stmt = dbConnection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM RENTAL_INCOME");

            // move to first row in results set
            // will return false when after last row
            while ( rs.next() )
            {       
                income = new RentalIncome();
                income.iID = rs.getInt("ID");
                income.sTaxYear = rs.getString("TAX_YEAR");
                income.incomeDate = rs.getDate("DATE");
                income.dBankIncome = rs.getFloat("BANK_INCOME");
                income.dRentalIncome = rs.getFloat("RENT_VALUE");
                income.sPayMode = rs.getString("PAYMENT_MODE");
                income.sPaySource = rs.getString("RENT_SOURCE");
                income.sTenant = rs.getString("TENANT");
                income.sComment = rs.getString("COMMENT");
                income.iPropID = rs.getInt("PROPERTY_ID");
                income.iTaxID = rs.getInt("TAX_YEAR_ID");
                
                // RPASUI.logger.debug( income.toString() );
                
                  
                // add new object to the databasedata object
                dbData.addRentalIncome(income );
            }
            
            // close record set
            rs.close();
          
        }
        catch ( SQLException err ) 
        {
            RPASUI.logger.debug( "DB execpetion thrown getting rental income payments : " + err.toString() );
            return false;
        }
        return true;
    }
   
    
    public boolean loadDatabaseData( DatabaseData dbData )
    {
        
        boolean bReturn = true;
        
        // valid object
        if ( dbData == null )
            dbData = new DatabaseData();
        
        // clear all data
        dbData.clearAllData();
        
        // load new data
        if ( !getDBExpenses( dbData ) )
            bReturn = false;
        
        if ( !getDBProperties( dbData ) )
            bReturn = false;
        
        if ( !getDBTaxYears( dbData ) )
            bReturn = false;
        
        if ( !getMortgagePayments(dbData) )
           bReturn = false;
        
        if ( !getRentalIncome(dbData) )
            bReturn = false;
        
        if ( !getDBTaxBands( dbData) )
            bReturn = false;
        
        return bReturn;
    }
}
