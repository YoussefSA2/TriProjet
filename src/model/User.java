package model;

import java.sql.SQLException;
import java.util.Date;

import db.dbConnection;
public class User {
	private int id;
	private String username;
	private int points;
	private String adress;
	private Date createdAt;
	private boolean admin;

	public User(int id, String username, int points, String adress, boolean admin) {
		this.id = id;
		this.username = username;
		this.points = points;
		this.adress=adress;
		this.admin = admin;
	}
	public int getId(){
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getAdress() {
		return adress;
	}
	public int getPoints() {
		return points;
	}
	public boolean isAdmin(){
		return admin;
	}
	public void addPoints(int points) {
		this.points += points;
		try {
			dbConnection.update("UPDATE users SET points = points+"+points+" WHERE id ="+this.id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
