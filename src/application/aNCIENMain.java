package application;



import model.GarbageBin;
import model.Trash;
import model.TrashDrop;
import model.User;
import java.util.Scanner;

import exception.FullBinException;

import java.util.ArrayList;
import java.util.Iterator;

public class aNCIENMain {
	
	public static int binChoice(ArrayList<GarbageBin> g) {
		Scanner sc = new Scanner(System.in);
		int choice;
		System.out.println("Quel type de poubelle vous choississez:");
		for(int i = 0; i<g.size();i++)
		{
			GarbageBin b = g.get(i);
			System.out.println(i+". "+ b.getType()+" "+b.getContent()+"/"+b.getCapacity());
		}
		System.out.println("4. Quitter");
		do {
			System.out.println("Veuillez choisir un numéro valide:");
			choice = sc.nextInt();
		}while(choice>g.size()+1|| choice<0);
		return choice;
	}
	public static ArrayList<Trash> todayTrash(Trash[] t){
		ArrayList<Trash> todayTrash = new ArrayList<Trash>();
		for(int y=0; y<10;y++) {
			todayTrash.add(t[(int)(Math.random() * ( 3 ))]);
		}
		System.out.println("Poubelle aujourd'hui:");
		for(Trash trash: todayTrash) {
			System.out.println(trash.getType());
		}
		
		return todayTrash;
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<GarbageBin> g = new ArrayList<GarbageBin>();
		
		Trash[] t = new Trash[10];
		t[0] = new Trash(1,"t1","1",5);
		t[1] = new Trash(2,"t1","2",10);
		t[2] = new Trash(3,"t2","2",20);
		t[3] = new Trash(4,"t4","3",100);
		g.add(new GarbageBin(1,"1", 100));
		g.add(new GarbageBin(2, "2",  100));
		System.out.println("Connecter vous:");
		User user = new User(1, "Youssef", 0,"t");
		System.out.println("Hey " + user.getUsername());
		System.out.println("Que voulez vous faire:");
		System.out.println("1. Sortir la poubelle");
		System.out.println("2. Voir combien d'argent j'ai");
		int i = sc.nextInt();
		if(i==1) {
			ArrayList<Trash> todayTrash = todayTrash(t);
			while(true) {
				int point=0;
				int bin;
				bin = binChoice(g);
				if(bin==4) {
					break;
				}
				TrashDrop trashDrop = new TrashDrop(g.get(bin), user);
				 Iterator<Trash> iter
		            = todayTrash.iterator();
				 while(iter.hasNext()) {
					Trash tI=iter.next();
					System.out.println("type:"+ tI.getType() );
					System.out.println("1. Jeter 2. Ne Pas jeter 3.Fin");
					int trushChoice = sc.nextInt();
					
					if(trushChoice==1) {
						try {
						point += (trashDrop.addTrash(tI));
						iter.remove();
						} catch(FullBinException e) {
						System.out.println(e.getMessage());
						
					}
						if(trushChoice==3) {
							break;
							
						}
					}
						
				}
				user.addPoints(point);
				System.out.println(user.getPoints());
			}
		}
	}

}
