package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Deportista;
import dao.DeportistaDao;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

public class VenDeportistaController {
	@FXML
	private Label lblTexto;
	@FXML
	private TextField tfNombre;
	@FXML
	private RadioButton rbHombre;
	@FXML
	private ToggleGroup tgSexo;
	@FXML
	private RadioButton rbMujer;
	@FXML
	private TextField tfPeso;
	@FXML
	private TextField tfAltura;
	@FXML
	private Button btnEjecuta;

	private DeportistaDao dao;
	private Boolean editar = false;
	private Deportista d;
	private String nombre, sexo, peso, altura;
	
	// Event Listener on Button[#btnEjecuta].onAction
	@FXML
	public void ejecuta(ActionEvent event) {
		String msg = "";
		
		try {
		
			msg = validacion();
			
			if(msg.equals("")) {
				if(!editar) {
					//Crear
					Deportista de = new Deportista(null, nombre, sexo, peso, altura);	
					msg = dao.crearDeportista(de);
				}else {
					//Editar
					Deportista de = new Deportista(d.getId_deportista(), nombre, sexo, peso, altura);	
					msg = dao.editarDeportista(de);
				}
			}
			if(msg.equals("")) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setHeaderText(null);
		        alert.setTitle("Hecho :)");
		        if(editar) {
		        	alert.setContentText("Editado correctamente \n");
		        }else {
		        	alert.setContentText("Creado correctamente \n");
		        }
		        alert.showAndWait();
		        this.cerrar(event);
			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setHeaderText(null);
		        alert.setTitle("Error");
		        alert.setContentText(msg);
		        alert.showAndWait();
			}
		}catch (Exception e) {
			e.printStackTrace();
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText(null);
	        alert.setTitle("Error");
	        alert.setContentText("Se a producido un error, vuelva más tarde");
	        System.out.println(e.getMessage());
	        alert.showAndWait();
	    }
	}
	// Event Listener on Button.onAction
	@FXML
	public void cerrar(ActionEvent event) {
		Stage stage = (Stage) btnEjecuta.getScene().getWindow();
		stage.close();
	}
	
	public void cargarDatos(Deportista d) {
		this.d=d;
		editar = true;
		lblTexto.setText("EDITAR");
		rellenarCampos();
	}
	
	public void rellenarCampos() {

		tfNombre.setText(d.getNombre());
		if (d.getSexo().equals("M")) {
			rbHombre.setSelected(true);
		}else {
			rbMujer.setSelected(true);
		}
		
		if (d.getPeso() != null) {
			tfPeso.setText(d.getPeso());
		}
		if (d.getAltura() != null) {
			tfAltura.setText(d.getAltura());
		}
		

	}
	
	public String validacion() {
		String msg = "";
		dao = new DeportistaDao();
			
		nombre = tfNombre.getText().toString();
		if (rbHombre.isSelected()) {
			sexo = "M";
		}else {
			sexo = "F";
		}
		peso = tfPeso.getText().toString();
		altura = tfAltura.getText().toString();
		
		
		if (nombre.equals("")) {
			msg+="Campo 'nombre' no puede estar vacio \n";
		}
		if (!peso.equals("")) {
			try {
				int aux = Integer.parseInt(peso);
				if (aux>500 || aux<0) {
					msg+="Campo 'peso' tiene que ser entre 0-500 \n";
				}
			}catch (NumberFormatException e) {
				msg += "Campo 'peso' tiene que ser numerico \n"; 
			}
		}
		if (!altura.equals("")) {	
			try {
				int aux = Integer.parseInt(altura);
				if (aux>500 || aux<0) {
					msg+="Campo 'altura' tiene que ser entre 0-300 \n";
				}
			}catch (NumberFormatException e) {
				msg += "Campo 'altura' tiene que ser numerico \n"; 
			}
		}
		try {
			int resp = dao.verificadorRepetidos(nombre);
			if(editar) {
				if (d.getNombre().equals(nombre)) {
					if (resp>1) {
						System.out.println(resp);
						msg+="Este deportista ya existe";
					}
				}else {
					if (resp>0) {
						msg+="Este deportista ya existe";
					}
				}
				if (resp<0) {
					msg ="Error al verificar los datos";
				}
			}else {
				if (resp>0) {
					msg+="Este deportista ya existe";
				}
				if (resp<0) {
					msg ="Error al verificar los datos";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error al verificar los datos";
		}
		
		return msg;
	}
}
