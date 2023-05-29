package controller.view;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.GarbageBin;
import model.TrashDrop;
import model.Voucher;
import model.getData;

import java.util.ResourceBundle;

import db.dbConnection;
public class HomeController implements Initializable{
    @FXML
    Button btnS, btnAcheter;
    @FXML
    Label lblName, lblPoints, lblAdress, lblId;
    @FXML
    AnchorPane history, home,voucher, admin;
    @FXML
    Pane adminPane;
    @FXML
    TableColumn id, bin, points, date;
    @FXML
    TableColumn idV, cost, amount, shop;
    @FXML
    TableView table;
    @FXML
    TableView tableV;
    @FXML
    ChoiceBox voucherBox, binBox;
    @FXML
    private NumberAxis poubelleX;
    @FXML
    private CategoryAxis poubelleY;
    @FXML
    private BarChart<?,?> dechetPoubelle;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		lblName.setText(getData.user.getUsername());
        lblPoints.setText(""+getData.user.getPoints()+" Points");
		lblAdress.setText(getData.user.getAdress());
        lblId.setText("Id: "+getData.user.getId());
        if(getData.user.isAdmin())
            adminPane.setVisible(true);
        
	}
    public void openHistory(Event e){
        home.setVisible(false);
        history.setVisible(true);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        points.setCellValueFactory(new PropertyValueFactory<>("points"));
        table.setItems(getAllDrop());
    }
    public void openVoucher(Event e){
        home.setVisible(false);
        voucher.setVisible(true);
        update();
    }
    public void openAdmin(Event e){
        home.setVisible(false);
        admin.setVisible(true);
        binBox.getItems().addAll(getAllBin());
        dechetPoubelle.getData().addAll(getAllTrashType());
    }
    public void retour(Event e) throws IOException{
        Node node= (Node) e.getSource();
        Stage stage =(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/vue/Home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void reset(Event e) {
        GarbageBin selection = (GarbageBin) binBox.getValue();
        selection.viderContent();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setContentText("Poubelle vider");
        alert.showAndWait();
        binBox.getItems().clear();
        binBox.getItems().addAll(getAllBin());
    }

    public void startGame(Event e) throws IOException{
        Node node= (Node) e.getSource();
        Stage stage =(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/vue/Game.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

   

    public void update(){
        voucherBox.getItems().clear();
        idV.setCellValueFactory(new PropertyValueFactory<>("id"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        shop.setCellValueFactory(new PropertyValueFactory<>("shop"));
        tableV.setItems(getAllVoucher());
        
        voucherBox.getItems().addAll(getAllVoucherConfig()); 
        lblPoints.setText(""+getData.user.getPoints()+" Points");
    }
    public void acheter(Event e) {
        Voucher selection = (Voucher) voucherBox.getValue();
        if (selection.getCost() > getData.user.getPoints()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Pas assez de point");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succes");
        alert.setContentText("Voucher acheté");
        alert.showAndWait();
        try {
            dbConnection.insert("INSERT INTO voucher (userId, voucherId) VALUES ('"+getData.user.getId()+"','" + selection.getId()+"') ");
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        getData.user.addPoints(-selection.getCost());
        update();
    }
    
    
    public ObservableList<TrashDrop> getAllDrop(){
        ObservableList trashs = FXCollections.observableArrayList();
        try {
            ResultSet rs = dbConnection.select("Select * from trashdrop, garbagebins where trashdrop.binId=garbagebins.id and userId= "+getData.user.getId());
            while(rs.next()){
                TrashDrop trashDrop = new TrashDrop(rs.getInt("trashdrop.id"),rs.getString("date"),rs.getString("trashdrop.trashs"), getData.user, new GarbageBin(rs.getInt("garbagebins.id"), rs.getString("garbagebins.type"), rs.getFloat("garbagebins.capacity"), rs.getFloat("garbagebins.content")),rs.getInt("trashdrop.points"));
                trashs.add(trashDrop);
                }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return trashs;
    }
    public ObservableList<TrashDrop> getAllVoucher(){
        ObservableList vouchers = FXCollections.observableArrayList();
        try {
            ResultSet rs = dbConnection.select("Select * from voucher, voucherConfig where voucher.voucherId = voucherConfig.id and userId= "+getData.user.getId());
            
            while(rs.next()){
                Voucher voucher = new Voucher(rs.getInt("id"),rs.getString("type"),rs.getInt("amount"), rs.getInt("cost"),rs.getString("shop"));
                vouchers.add(voucher);
                }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return vouchers;
    }
    public ObservableList<TrashDrop> getAllVoucherConfig(){
        ObservableList vouchers = FXCollections.observableArrayList();
        try {
            ResultSet rs = dbConnection.select("Select * from voucherConfig");
            
            while(rs.next()){
                Voucher voucher = new Voucher(rs.getInt("id"),rs.getString("type"),rs.getInt("amount"), rs.getInt("cost"),rs.getString("shop"));
                vouchers.add(voucher);
                }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return vouchers;
    }

    public ObservableList<TrashDrop> getAllBin(){
        ObservableList bins = FXCollections.observableArrayList();
        try {
            ResultSet rs = dbConnection.select("Select * from garbagebins");
            
            while(rs.next()){
                GarbageBin bin = new GarbageBin(rs.getInt("id"),rs.getString("type"), rs.getFloat("capacity"),rs.getFloat("content"));
                bins.add(bin);
                }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return bins;
    }
    public XYChart.Series getAllTrashType(){
        XYChart.Series set1 = new XYChart.Series<>();
        try {
            ResultSet rs = dbConnection.select("Select binId, count(*) from trashdrop group by binId");
            
            while(rs.next()){
            set1.getData().add(new XYChart.Data(rs.getString(1),rs.getInt(2)));
            }
            
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return set1;
    }

}
