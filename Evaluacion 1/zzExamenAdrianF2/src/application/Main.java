package application;
	
import java.util.Locale;
import java.util.ResourceBundle;

import conexionBD.Propiedades;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/Productos.fxml"));
			Scene scene = new Scene(root,915,672);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			String imagePath = getClass().getResource("/images/carrito.png").toString();
			primaryStage.getIcons().add(new Image(imagePath));
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			primaryStage.setMaximized(false);
			primaryStage.setTitle("Productos");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}