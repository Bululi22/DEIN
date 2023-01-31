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
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			ResourceBundle bundle = ResourceBundle.getBundle("idiomas/messages");
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/EjercicioI.fxml"),bundle);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			String imagePath = getClass().getResource("/images/agenda.png").toString();
			primaryStage.getIcons().add(new Image(imagePath));
			primaryStage.setTitle("AGENDA");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
