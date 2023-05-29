package model;

public class Trash {
	private int id;
	private String name;
	private String type;
	private float weight;
	public Trash(int id, String name, String type, float weight) {
		this.id=id;
		this.name = name;
		this.type = type;
		this.weight = weight;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
}
