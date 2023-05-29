package controller.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import db.dbConnection;
import exception.FullBinException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.GarbageBin;
import model.Trash;
import model.TrashDrop;
import model.getData;

public class GameController implements Initializable{
    @FXML
    Label lblTrash, lblPoints;
    @FXML
    ImageView imgTrash;
    ArrayList<Trash> todayTrash = new ArrayList<Trash>() ;
    ArrayList<GarbageBin> bin = new ArrayList<GarbageBin>();
    Iterator<Trash> iter;
    ArrayList<TrashDrop> trashDrop = new ArrayList<TrashDrop>();
    Trash trash;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeBin();
        initializeTrashDrop();
        todayTrash();
    }
    public void retour(Event e) throws IOException{
        Node node= (Node) e.getSource();
        Stage stage =(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/vue/Home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void greenBinThrow(Event e){
        throwInBin(0);
            
            
    }
    public void blueBinThrow(Event e){
        throwInBin(1);
            
    }
    public void yellowBinThrow(Event e){
        throwInBin(2);
            
    }
    public void blackBinThrow(Event e){
        throwInBin(3);
            
    }
    public void throwInBin(int id){
        int point=0;
        TrashDrop drop = trashDrop.get(id);
        try {
            point += (drop.addTrash(trash));
            iter.remove();
            getData.user.addPoints(point);
            updateLbl(point);
            next();
            } catch(FullBinException ea) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("FullBin");
                alert.setContentText("Poubelle remplie");
                alert.showAndWait();
            }
    }

    public void updateLbl(int point){
        if(point>0){
            lblPoints.setTextFill(Color.rgb(0, 255, 0, 0.87));
            lblPoints.setText("+"+point);
        }
        else{
            lblPoints.setTextFill(Color.rgb(255, 0, 0, 0.87));
            lblPoints.setText(""+point);
        }
    }
    public void initializeBin(){
        try {
            ResultSet rs = dbConnection.select("Select * from garbagebins");
            while(rs.next()){
                bin.add(new GarbageBin(rs.getInt("id"),rs.getString("type"), rs.getFloat("capacity"),rs.getFloat("content")));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void initializeTrashDrop(){
        for(int i=0;i<bin.size();i++){
         trashDrop.add(new TrashDrop(bin.get(i), getData.user));
        }
    }
    
    public void todayTrash(){
		for(int y=0; y<30;y++) {
            try {
                ResultSet rs = dbConnection.select("SELECT * FROM trashs ORDER BY RAND() LIMIT 1");
                while(rs.next()){
                    this.todayTrash.add(new Trash(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getInt("weight")));
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			
		}
		this.iter= this.todayTrash.iterator();
        next();
	}
    public void next(){
        if(iter.hasNext()) {
            trash=iter.next();
            lblTrash.setText(trash.getName() +" : "+trash.getType());
            File file = new File("src/img/"+trash.getType()+".png");
            Image image = new Image(file.toURI().toString());
            imgTrash.setImage(image);
         }else{
             lblTrash.setText("Fin");
         }
 
     }


    
    
}
