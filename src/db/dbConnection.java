package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dbConnection {
	public static Connection conn;
	public static Connection connect() {
		try {
			String url="jdbc:mysql://localhost:3306/tri";
			String user = "root";
			String passwrd = "";
			Class.forName("com.mysql.cj.jdbc.Driver");
			 conn=DriverManager.getConnection(url,user,passwrd);
			return conn;
		}catch(ClassNotFoundException | SQLException e) {
			Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE, null, e);

		}
		return null;
	}
	public static int insert(String sql) throws SQLException{
		
			if(conn==null)
				connect();
			
			PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next()) {
				generatedKey = rs.getInt(1);
			}
			
			return generatedKey;

		
	}
		
	
	public static void update(String sql) throws SQLException{
		
			if(conn==null)
				connect();

		  	Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		
		}
		
	
	public static ResultSet select(String sql) throws SQLException{
		
			if(conn==null)
				connect();

		  	Statement stmt = conn.createStatement();
			return stmt.executeQuery(sql);

		
		
		
	}
}
