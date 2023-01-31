package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class VentanaLoginController {
	@FXML
	private TextField tfUsuario;
	@FXML
	private TextField tfPassword;
	@FXML
	private Button btnLogin;

	// Event Listener on Button[#btnLogin].onAction
	@FXML
	public void login(ActionEvent event) {
		try{
	          FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Listado.fxml"));
	          Parent root = loader.load();
	          Scene newScene = new Scene(root);
	          Stage newStage = new Stage();
	          newStage.setScene(newScene);
	          newStage.setTitle("Gestión");
	          newStage.show();
	          Stage myStage = (Stage) this.btnLogin.getScene().getWindow();
	          myStage.close();
	      } catch (IOException e) {
	    	  e.printStackTrace();
	          Alert alert = new Alert(Alert.AlertType.ERROR);
	          alert.setHeaderText(null);
	          alert.setTitle("Error");
	          alert.setContentText(e.getMessage());
	          alert.showAndWait();
	      }
	}
}
