package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/fxml/VistaAgenda.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			primaryStage.setTitle("Agenda");
			primaryStage.setScene(scene);
			//Image image = new Image(getClass().getResource("/images/agenda.png").toString());
			//primaryStage.getIcons().add(image);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			//mostrarAlertInfo(primaryStage,"Error al abrir la ventana" );
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
