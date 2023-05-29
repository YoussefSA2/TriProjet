package model;

public class Voucher {
    private int id;
    private String type;
    private int amount;
    private int cost;
    private String shop; 
    public Voucher(int id, String type, int amount, int cost, String shop){
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.cost = cost;
        this.shop = shop;
    }
    public int getId(){
        return id;
    }
    public int getCost(){
        return cost;
    }
    public int getAmount(){
        return amount;
    }
    public String getShop(){
        return shop;
    }
    public String getType(){
        return type;
    }

    public String toString() {
        String re = "-" + amount;
        return "-" + amount+ " "+ type+ " à " + shop + " - prix : " + cost + " points";
    }
}
