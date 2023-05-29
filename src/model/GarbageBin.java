package model;

import java.sql.SQLException;

import db.dbConnection;
import exception.FullBinException;

public class GarbageBin {
	private int id;
	private String type;
	private float capacity;
	private float content;
	public GarbageBin(int id, String type, float capacity, float content) {
		this.id = id;
		this.type = type;
		this.capacity = capacity;
		this.content = content;
	}
	public GarbageBin(int id, String type, float capacity) {
		this.id = id;
		this.type = type;
		this.capacity = capacity;
		this.content = 0;
	}
	public int getId(){
		return id;
	}
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public float getContent() {
		return content;
	}
	public void addContent(float content)throws FullBinException {
		if(this.content+content>capacity)
			throw new FullBinException("Full Bin");
		else{
			
				this.content += content;
			try {
				dbConnection.update("UPDATE garbagebins SET content ="+this.content+" WHERE id ="+this.id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public float getCapacity() {
		return capacity;
	}
	public void viderContent(){
		this.content=0;
		try {
			dbConnection.update("UPDATE garbagebins SET content = 0 WHERE id ="+this.id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String toString() {
       return "Poubelle "+id+" "+ getType()+" " + content + "/"+capacity;
    }
	
}
