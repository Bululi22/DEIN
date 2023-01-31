package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class SeleccionController  {
	
	@FXML
	private Label lblSeleccionaCampo;
	
	private String seleccion;
	
	// Event Listener on Button.onAction
	@FXML
	public void olimpiadas(ActionEvent event) {
		// TODO Autogenerated
		seleccion = "Olimpiada";
		ventanaTabla();
	}
	// Event Listener on Button.onAction
	@FXML
	public void deportes(ActionEvent event) {
		// TODO Autogenerated
		seleccion = "Deporte";
		ventanaTabla();
	}
	// Event Listener on Button.onAction
	@FXML
	public void equipos(ActionEvent event) {
		// TODO Autogenerated
		seleccion = "Equipo";
		ventanaTabla();
	}
	// Event Listener on Button.onAction
	@FXML
	public void eventos(ActionEvent event) {
		// TODO Autogenerated
		seleccion = "Evento";
		ventanaTabla();
	}
	// Event Listener on Button.onAction
	@FXML
	public void deportistas(ActionEvent event) {
		// TODO Autogenerated
		seleccion = "Deportista";
		ventanaTabla();
	}
	// Event Listener on Button.onAction
	@FXML
	public void participaciones(ActionEvent event) {
		// TODO Autogenerated
		seleccion = "Participacion";
		ventanaTabla();
	}
		
	public void ventanaTabla() {
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Tabla.fxml"));
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			TablaController control = loader.getController();
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.lblSeleccionaCampo.getScene().getWindow());
			newStage.setMinWidth(930);
			newStage.setMinHeight(300);
			newStage.setScene(newScene);
			newScene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			
			newStage.setTitle("Tabla "+seleccion);
			control.cargarDatos(seleccion);

			
			
			newStage.showAndWait();
			
			
	    } catch (IOException e) {
	    	e.printStackTrace();
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText(null);
	        alert.setTitle("Error");
	        alert.setContentText("Se a producido un error");
	        alert.showAndWait();
	    }
	}
}
