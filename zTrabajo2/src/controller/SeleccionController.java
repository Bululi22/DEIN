package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class SeleccionController {
	@FXML
	private GridPane gridSeleccion;
	@FXML
	private Label lblSeleccionaCampo;
	
	@SuppressWarnings("rawtypes")
	private ObservableList lista;
	private String seleccion, texto;

	// Event Listener on Button.onAction
	@FXML
	public void alumnos(ActionEvent event) {
		seleccion = "Alumnos";
		ventanaTabla();
	}
	// Event Listener on Button.onAction
	@FXML
	public void libros(ActionEvent event) {
		seleccion = "Libros";
		ventanaTabla();
	}
	// Event Listener on Button.onAction
	@FXML
	public void prestamos(ActionEvent event) {
		seleccion = "Prestamos";
		ventanaTabla();
	}
	// Event Listener on Button.onAction
	@FXML
	public void devoluciones(ActionEvent event) {
		seleccion = "GestionDeDevoluciones";
		ventanaTabla();
	}
	// Event Listener on Button.onAction
	@FXML
	public void historicoPrestamos(ActionEvent event) {
		seleccion = "Historico_prestamos";
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
