package model;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import db.dbConnection;
import exception.FullBinException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class TrashDrop {
	private int id;
	private Date date;
	private User user;
	private HashMap<Integer, Integer> trashs;
	private GarbageBin bin;
	private int points;
	public TrashDrop(GarbageBin bin, User user)  {
		this.date = new Date();
		this.bin = bin;
		this.user = user;
		this.trashs = new HashMap<Integer, Integer>();
		try {
			this.id = dbConnection.insert("INSERT INTO trashdrop (userId, trashs, binId) VALUES ('"+user.getId()+"','{}','" + bin.getId()+"') ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.points=0;
		
	}
	public TrashDrop(int id, String date, String trashs, User user, GarbageBin bin, int points)  {
		try {
			this.date = new SimpleDateFormat("yyyy-MM--DD HH:mm:ss").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.date = new Date();
		}
		this.id = id;
		this.bin = bin;
		this.user = user;
		Gson gson = new Gson();
        Type type = new com.google.gson.reflect.TypeToken<HashMap<String, Object>>(){}.getType();
        HashMap<Integer, Integer> hashMap = gson.fromJson(trashs, type);
		this.trashs =hashMap;
		this.points=points;
	}
	public int getPoints() {
		return points;
	}
	public int getId() {
		return id;
	}
	public String getDate() {
		String pattern = "MM-dd-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(this.date);
		return date;
	}
	public User getUser() {
		return user;
	}

	public GarbageBin getBin() {
		return bin;
	}
	
	public int addTrash(Trash trash) throws FullBinException {
		int points=0;
		try {
			bin.addContent(trash.getWeight());
			trashs.put(trash.getId(), ((trashs.get(trash.getId())==null) ? 1 : trashs.get(trash.getId())+1 ));
			points = ((trash.getType().equals(bin.getType()))? 1 :-1);
			try {
				dbConnection.update("UPDATE trashdrop SET points = points+"+points+" WHERE id ="+this.id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		catch(FullBinException e) {
			throw new FullBinException("Full Bin");
		}
		return points;
	}
	
}
