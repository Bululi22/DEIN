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
			//Idioma
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			ResourceBundle bundle = ResourceBundle.getBundle("idiomas/messages");
			
			//Rutas
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/Agenda.fxml"),bundle);
			Scene scene = new Scene(root,998,525);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			
			//Icono
			String img=getClass().getResource("/images/agenda.png").toString();
			primaryStage.getIcons().add(new Image(img));
			
			//Mostrar
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Redimensionable
//			primaryStage.setResizable(false);
//			primaryStage.setMaximized(false);
			
			//Titulo
			primaryStage.setTitle("AGENDA");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
