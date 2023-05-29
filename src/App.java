


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("vue/Login.fxml"));
			Scene scene = new Scene(root,1116,800);
			stage.setScene(scene);
			stage.setTitle("Hello World");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
