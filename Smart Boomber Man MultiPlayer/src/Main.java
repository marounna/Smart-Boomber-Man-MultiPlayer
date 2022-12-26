

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import CONTROL.DBControl;
public class Main {

	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, UnknownHostException {
	// TODO Auto-generated method stub

		try{
			DBControl.CreateDB();
			System.out.println("database was succesfuly created ");
		}
		catch (ClassNotFoundException ex) {System.out.println(ex.getMessage());}
	    catch (IllegalAccessException ex) {System.out.println(ex.getMessage());}
	    catch (InstantiationException ex) {System.out.println(ex.getMessage());}
	    catch (SQLException ex)           {System.out.println(ex.getMessage());}

		try{
			DBControl.CreateDBTables();
			System.out.println("tables were succesfuly created ");
		}
		catch (ClassNotFoundException ex) {System.out.println(ex.getMessage());}
	    catch (IllegalAccessException ex) {System.out.println(ex.getMessage());}
	    catch (InstantiationException ex) {System.out.println(ex.getMessage());}
	    catch (SQLException ex)           {System.out.println(ex.getMessage());}
		
		new MainFrame();

	}

}
