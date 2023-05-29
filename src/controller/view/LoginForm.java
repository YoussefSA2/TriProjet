package controller.view;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.dbConnection;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import model.getData;

public class LoginForm {
    @FXML
    TextField txtUser;
    @FXML
    TextField txtPass;
    @FXML
    Button btnSign;
    @FXML
    Label lblMsg;
    
    public void login(Event e) throws SQLException, IOException{
        if(txtUser.getText().isEmpty() && txtPass.getText().isEmpty()){
            lblMsg.setText("Faut remplire");
            return;
        }
        
        ResultSet res = dbConnection.select("select * from users where username='"+txtUser.getText()+"' and passeword='"+hashMD5(txtPass.getText())+"'");
        if(res.next()){
            getData.user = new User(res.getInt("id"), res.getString("username"), res.getInt("points"),res.getString("adress"),res.getBoolean("admin"));
            Node node= (Node) e.getSource();
            Stage stage =(Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/vue/Home.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            lblMsg.setText("User ou passeword incorrecte");

        }
        
    }
    public void register(Event e) {
        if(txtUser.getText().isEmpty() && txtPass.getText().isEmpty()){
            lblMsg.setText("Faut remplire");
            return;
        }
        
        try {
            dbConnection.insert("INSERT INTO users (username, passeword) VALUES ('"+txtUser.getText()+"','" + hashMD5(txtPass.getText())+"') ");
            lblMsg.setText("Inscrit !");
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            lblMsg.setText(e1.getMessage());
        }
        
        
    }
    public String hashMD5(String passwordToHash){
        String generatedPassword = null;

        try 
        {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(passwordToHash.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
