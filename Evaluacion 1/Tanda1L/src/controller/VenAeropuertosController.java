package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Vuelos;

import java.net.URL;
import java.util.ResourceBundle;

import dao.AeropuertosDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

public class VenAeropuertosController implements Initializable{
	@FXML
	private TextField tfNombre;
	@FXML
	private TextField tfPais;
	@FXML
	private TextField tfCiudad;
	@FXML
	private TextField tfCalle;
	@FXML
	private TextField tfNumero;
	@FXML
	private TextField tfAnioInanguracion;
	@FXML
	private TextField tfCapacidad;
	@FXML
	private RadioButton rbPublico;
	@FXML
	private RadioButton rbPrivado;
	@FXML
	private Label lblFinanciacion;
	@FXML
	private TextField tfFinanciacion;
	@FXML
	private Label lblSocios;
	@FXML
	private TextField tfSocios;
	@FXML
	private Label lblTrabajadores;
	@FXML
	private TextField tfTrabajadores;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnCancelar;
	
	private ObservableList<Vuelos> lista;
	private Vuelos vuelo, vueloNuevo;
	private ToggleGroup toggleGroup;
	private Boolean publico;

	// Event Listener on Button[#btnCancelar].onAction
	@FXML
	public void guardar(ActionEvent event) {
		String msg;
		msg = verificar();
		if (!msg.equals("")) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText(null);
	        alert.setTitle("Error");
	        alert.setContentText(msg);
	        alert.showAndWait();
		}else {
			//EDITAR;
			if (vuelo!=null) {
				//System.out.println("b");
				AeropuertosDao dao = new AeropuertosDao();
				vueloNuevo = new Vuelos(vuelo.getId(), getTfNombre().getText(), getTfPais().getText(), getTfCiudad().getText(), getTfCalle().getText(), Integer.parseInt(getTfNumero().getText()), Integer.parseInt(getTfAnioInanguracion().getText()), Integer.parseInt(getTfCapacidad().getText()), Integer.parseInt(getLblSocios().getText()), vuelo.getIdDirecciones());
				//System.out.println("1");
				dao.editarAeropuerto(vueloNuevo, getTfFinanciacion().getText(), getTfTrabajadores().getText(), publico);
				//System.out.println("2");
			}
			//CREAR
			else {
				AeropuertosDao dao = new AeropuertosDao();
				if(publico) {
					vueloNuevo = new Vuelos(0, getTfNombre().getText(), getTfPais().getText(), getTfCiudad().getText(), getTfCalle().getText(), Integer.parseInt(getTfNumero().getText()), Integer.parseInt(getTfAnioInanguracion().getText()), Integer.parseInt(getTfCapacidad().getText()), 0);
					dao.crearAeropuerto(vueloNuevo, getTfFinanciacion().getText(), getTfTrabajadores().getText(), publico);
				}else {
					System.out.println("a");
					vueloNuevo = new Vuelos(0, getTfNombre().getText(), getTfPais().getText(), getTfCiudad().getText(), getTfCalle().getText(), Integer.parseInt(getTfNumero().getText()), Integer.parseInt(getTfAnioInanguracion().getText()), Integer.parseInt(getTfCapacidad().getText()), Integer.parseInt(getTfSocios().getText()), 0);
					System.out.println("b");
					dao.crearAeropuerto(vueloNuevo, "", "", publico);
				}
			}
		}
	}
	
	// Event Listener on Button[#btnCancelar].onAction
	@FXML
	public void cancelar(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}
	
	public void cargarDatos(ObservableList<Vuelos> l, Boolean b) {
		lista = l;
		publico = b;
	}
	
	public void cargarDatos(ObservableList<Vuelos> l, Vuelos v, Boolean b) {
		lista = l;
		vuelo = v;
		publico = b;
	}
	
	public void tipoAeropuerto() {
		if (rbPrivado.isSelected()) {
			getRbPrivado().setSelected(true);
			getLblFinanciacion().setVisible(false);
			getTfFinanciacion().setVisible(false);
			getLblTrabajadores().setVisible(false);
			getTfTrabajadores().setVisible(false);
			getLblSocios().setVisible(true);
			getTfSocios().setVisible(true);
		}else {
			getRbPublico().setSelected(true);
			getLblFinanciacion().setVisible(true);
			getTfFinanciacion().setVisible(true);
			getLblTrabajadores().setVisible(true);
			getTfTrabajadores().setVisible(true);
			getLblSocios().setVisible(false);
			getTfSocios().setVisible(false);
		}
	}

	private String verificar(){
		@SuppressWarnings("unused")
		int aux;
		String msg = "";
		if (getTfNombre().getText().equals("")) {
			msg += "Añade Nombre \n"; 
		}
		if (getTfPais().getText().equals("")) {
			msg += "Añade Pais \n"; 
		}
		if (getTfCiudad().getText().equals("")) {
			msg += "Añade Ciudad \n"; 
		}
		if (getTfCalle().getText().equals("")) {
			msg += "Añade Calle \n"; 
		}
		try {
			aux = Integer.parseInt(getTfNumero().getText());
		}catch (NumberFormatException e) {
			msg += "Numero no valido \n"; 
		}
		try {
			aux = Integer.parseInt(getTfAnioInanguracion().getText());
		}catch (NumberFormatException e) {
			msg += "Año no valido \n"; 
		}
		try {
			aux = Integer.parseInt(getTfCapacidad().getText());
		}catch (NumberFormatException e) {
			msg += "Capacidad no valido \n"; 
		}
		if (rbPublico.isSelected()) {
			publico = true;
			if (!getTfFinanciacion().getText().matches("[0-9]+([,.][0-9]{1,2})?")) {
				msg += "Financiacion no valido";
			}
			try {
				aux = Integer.parseInt(getTfTrabajadores().getText());
			}catch (NumberFormatException e) {
				msg += "Número de trabajadores no valido \n"; 
			}
		}else {
			publico = false;
			try {
				aux = Integer.parseInt(getTfSocios().getText());
			}catch (NumberFormatException e) {
				msg += "Número de socios no valido \n"; 
			}
		}
		//System.out.println(msg);
		return msg;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		toggleGroup = new ToggleGroup();
		rbPublico.setToggleGroup(toggleGroup);
		rbPublico.setOnAction(e -> tipoAeropuerto());
		rbPrivado.setToggleGroup(toggleGroup);
		rbPrivado.setOnAction(e -> tipoAeropuerto());
	}
	
	public TextField getTfNombre() {
		return tfNombre;
	}

	public void setTfNombre(TextField tfNombre) {
		this.tfNombre = tfNombre;
	}

	public TextField getTfPais() {
		return tfPais;
	}

	public void setTfPais(TextField tfPais) {
		this.tfPais = tfPais;
	}

	public TextField getTfCiudad() {
		return tfCiudad;
	}

	public void setTfCiudad(TextField tfCiudad) {
		this.tfCiudad = tfCiudad;
	}

	public TextField getTfCalle() {
		return tfCalle;
	}

	public void setTfCalle(TextField tfCalle) {
		this.tfCalle = tfCalle;
	}

	public TextField getTfNumero() {
		return tfNumero;
	}

	public void setTfNumero(TextField tfNumero) {
		this.tfNumero = tfNumero;
	}

	public TextField getTfAnioInanguracion() {
		return tfAnioInanguracion;
	}

	public void setTfAnioInanguracion(TextField tfAnioInanguracion) {
		this.tfAnioInanguracion = tfAnioInanguracion;
	}

	public TextField getTfCapacidad() {
		return tfCapacidad;
	}

	public void setTfCapacidad(TextField tfCapacidad) {
		this.tfCapacidad = tfCapacidad;
	}

	public RadioButton getRbPublico() {
		return rbPublico;
	}

	public void setRbPublico(RadioButton rbPublico) {
		this.rbPublico = rbPublico;
	}

	public RadioButton getRbPrivado() {
		return rbPrivado;
	}

	public void setRbPrivado(RadioButton rbPrivado) {
		this.rbPrivado = rbPrivado;
	}

	public ToggleGroup getToggleGroup() {
		return toggleGroup;
	}

	public void setToggleGroup(ToggleGroup toggleGroup) {
		this.toggleGroup = toggleGroup;
	}

	public TextField getTfFinanciacion() {
		return tfFinanciacion;
	}

	public void setTfFinanciacion(TextField tfFinanciacion) {
		this.tfFinanciacion = tfFinanciacion;
	}

	public TextField getTfSocios() {
		return tfSocios;
	}

	public void setTfSocios(TextField tfSocios) {
		this.tfSocios = tfSocios;
	}

	public TextField getTfTrabajadores() {
		return tfTrabajadores;
	}

	public void setTfTrabajadores(TextField tfTrabajadores) {
		this.tfTrabajadores = tfTrabajadores;
	}

	public void setLblFinanciacion(Label lblFinanciacion) {
		this.lblFinanciacion = lblFinanciacion;
	}

	public void setLblSocios(Label lblSocios) {
		this.lblSocios = lblSocios;
	}

	public void setLblTrabajadores(Label lblTrabajadores) {
		this.lblTrabajadores = lblTrabajadores;
	}

	public Label getLblFinanciacion() {
		return lblFinanciacion;
	}

	public Label getLblSocios() {
		return lblSocios;
	}

	public Label getLblTrabajadores() {
		return lblTrabajadores;
	}

	

	
	
	
}
