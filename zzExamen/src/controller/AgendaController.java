package controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ListView;

import javafx.scene.layout.GridPane;

public class AgendaController {
	@FXML
	private GridPane gridTabla;
	@FXML
	private Label lblTitulo;
	@FXML
	private ListView lvTelefonos;
	@FXML
	private ListView lvEmails;
	@FXML
	private Button btnNuevoTelefono;
	@FXML
	private Button btnBorrarTelefono;
	@FXML
	private Button btnAnterior;
	@FXML
	private Button btnNuevoEmail;
	@FXML
	private Button btnBorrarEmail;
	@FXML
	private Button btnAnterior1;
	@FXML
	private Label lblNumUsuarios;

	// Event Listener on MenuItem.onAction
	@FXML
	public void informe1(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void informe2(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void ayuda1(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void ayudaOnline(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnAnterior].onAction
	@FXML
	public void usuarioAnterior(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnAnterior1].onAction
	@FXML
	public void usuarioSiguiente(ActionEvent event) {
		// TODO Autogenerated
	}
}
