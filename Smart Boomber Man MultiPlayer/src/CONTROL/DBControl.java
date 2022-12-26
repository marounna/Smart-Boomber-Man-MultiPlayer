package CONTROL;

import java.sql.*;

import javax.swing.JOptionPane;

public class DBControl {
	static String DBname = "testest33";
	static Connection con; 
	static Statement stmt; 
	static ResultSet rs;
	static String url = "jdbc:mysql://localhost/"+DBname; 
	static String Default_url = "jdbc:mysql://localhost/"; 
	static String driverURL ="com.mysql.jdbc.Driver";

	
	static void open(String url) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	
		Class.forName(driverURL).newInstance();
	
		con = DriverManager.getConnection(url,"root","");
		
		stmt = con.createStatement();
	}
	
	static void close() throws SQLException{
		if(rs!=null)
			rs.close();
		if(stmt!=null)
			stmt.close();
		if(con!=null)
			con.close();
	}

	//CREATING DATABASE FUNCTION
	public static void CreateDB() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		String sql = "CREATE DATABASE "+DBname;
		open(Default_url);
		stmt.executeUpdate(sql);
		close();
	}

	
	public static void CreateDBTables() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		String sql1 = "CREATE TABLE Players " +
                "(UserName VARCHAR(55) not NULL, " +
                " first_name VARCHAR(55), " + 
                " last_name VARCHAR(55), " + 
                " pass VARCHAR(55), " + 
                " PRIMARY KEY ( UserName ))"; 
		String sql2 = "CREATE TABLE Statistics" +
				 " (code_game int NOT NULL AUTO_INCREMENT, " + 
                " UserName1 VARCHAR(55) , " +
                " UserName2 VARCHAR(55) , " +
                " kills VARCHAR(55), " + 
                " death VARCHAR(55), " + 
                " score VARCHAR(55), " +
                " boomb_used VARCHAR(55), " + 
                " boxies_dest VARCHAR(55), " + 
                " boxies_built VARCHAR(55), " + 
                " result VARCHAR(55), " + 
                "PRIMARY KEY (code_game),"+
                "FOREIGN KEY (UserName1) REFERENCES Players(UserName),"+
				"FOREIGN KEY (UserName2) REFERENCES Players(UserName))"; 
		
	//	UpdateURL();
		open(url);
		stmt.executeUpdate(sql1);
		stmt.executeUpdate(sql2);
		close();
		//System.out.println(url1);

	}
	
	//not working 
	//check if database exist
	public void test() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
	open(Default_url);
	String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema'[database name]' AND table_name = '[table name]'";
		ResultSet rs = stmt.executeQuery(query);                  
		System.out.println(rs);
		
		  close();
	}
	
	
	
	
	public void insert_Players(String UserName,String First_Name,String Last_Name,String password) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		open(url);
			String query = "insert into Players values( '"+UserName+"','"+First_Name+"','"+Last_Name+"','"+password+"')";
			stmt.execute(query);                  
		close();
		}
	public void insert_statistics(String UserName1,String username2 ,String kills,String death,String score,String boomb_used,String boxies_dest,String	boxies_built,String	result) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		open(url);
			String query = "INSERT INTO Statistics (UserName1, UserName2,  kills, death,score,boomb_used,boxies_dest,boxies_built,result) VALUES ('"+ UserName1+"', '"+username2+"','"+kills+"', '"+death+"','"+score+"','"+boomb_used+"','"+boxies_dest+"','"+boxies_built+"','"+result+"');";
			stmt.execute(query);                  
		close();
		}
	
	
	public void update_statistics(String code_game,String username2 ,String kills,String death,String score,String boomb_used,String boxies_dest,String	boxies_built,String	result) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		open(url);
			String query = "UPDATE  Statistics SET UserName2='"+username2+"' ,  kills ='"+kills+"', death ='"+death+"',score ='"+score+"',boomb_used ='"+boomb_used+"',boxies_dest ='"+boxies_dest+"' ,boxies_built ='"+boxies_built+"' ,result ='"+result+"' where code_game ='"+code_game+"'";
			stmt.execute(query);                  
		close();
		}
	
	
	
	public void select_all_Players() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		open(url);
			String query = "select * from Players";
			ResultSet rs = stmt.executeQuery(query);   
			 while (rs.next())
		      {
		        String c = rs.getString("UserName");
		        String f = rs.getString("first_name");
		        String l = rs.getString("last_name");
		        String p = rs.getString("pass");
		        System.out.println(c + "   " + f + "   " + l+ "   " + p);
		      }
		close();
		}
	public int get_last_code_game() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		open(url);
			int gc=-1;
			String query = "select * from Statistics ";
			ResultSet rs = stmt.executeQuery(query);   
			 while (rs.next())
		      {
		        String c = rs.getString("code_game");
		        gc=Integer.parseInt(c);
		      }
			 
		close();
		return gc;
		}
	public String get_username1_by_code(int code_game) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		open(url);
			String un="";
			String query = "select * from Statistics ";
			ResultSet rs = stmt.executeQuery(query);   
			 while (rs.next())
		      {
		        String c = rs.getString("code_game");
		        if((code_game+"").equals(c))
		        	un= rs.getString("UserName1");
		        
		      }
			 
		close();
		return un;
		}
	
	public void delete_from_Players_by_code(String code) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		open(url);
			String query = "delete from Players where UserName='"+code+"'";
			stmt.execute(query);
		close();
		}
	public void delete_all_Players() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		open(url);
			String query = "delete from Players";
			stmt.execute(query);
		close();
		}
	
	public void Show_Statistic(String Name) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		System.out.println("UserName is "+Name);
			open(url);
			char c =(char)9;
			String query = "select * from Statistics where UserName1 =  '"+Name+"'";
			String  ALL_RESULTS="Statistis: \n";
			ResultSet rs = stmt.executeQuery(query); 
			int i=0;
			 while (rs.next())
		      {
				 i++;
					String op = rs.getString("UserName2");
					String kills = rs.getString("kills");
					String death = rs.getString("death");
					String score = rs.getString("score");
		        	String boomb = rs.getString("boomb_used");
					String boxies_dest = rs.getString("boxies_dest");
					String boxies_built = rs.getString("boxies_built");
					String result = rs.getString("result");
		        	ALL_RESULTS+="*  "+i+"  * opponent = "+op+"  | kills  = "+kills +"  | death =  "+death+"  |  score = "+score+"  |  boomb used = "+boomb+ "  | boxies destroied =  "+ boxies_dest+"  | boxies built =   "+boxies_built+"  | result = "+result+"  |\n";

				 
		      }
			 JOptionPane.showMessageDialog(null, ALL_RESULTS, "Statistics for "+Name, 3);
		close();
		}


	public boolean check_user_pass(String UserName, String Password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		open(url);
		String query = "select * from Players where UserName = '"+UserName+"' and pass = '"+Password+"' ";
		ResultSet rs = stmt.executeQuery(query);   
		 if(rs.next())
	      {
	       return true;
	      }
	close();
		
		
		return false;
	}
	
	
	
}
