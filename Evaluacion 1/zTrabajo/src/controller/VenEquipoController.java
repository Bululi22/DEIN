package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Equipo;
import dao.EquipoDao;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class VenEquipoController {
	@FXML
	private Label lblTexto;
	@FXML
	private TextField tfNombre;
	@FXML
	private TextField tfIniciales;
	@FXML
	private Button btnEjecuta;

	private Boolean editar = false;
	private Equipo e;
	private String nombre, iniciales;
	private EquipoDao dao;
	
	// Event Listener on Button[#btnEjecuta].onAction
	@FXML
	public void ejecuta(ActionEvent event) {
		String msg = "";
		
		try {
		
			msg = validacion();
			
			if(msg.equals("")) {
				
				if(!editar) {
					//Crear
					Equipo eq= new Equipo(null, nombre, iniciales);
					msg = dao.crearEquipo(eq);
				}else {
					//Editar
					Equipo eq = new Equipo(this.e.getId_equipo(), nombre, iniciales);
					msg = dao.editarEquipo(eq);
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
	
	public void cargarDatos(Equipo e) {
		this.e=e;
		editar = true;
		lblTexto.setText("EDITAR");
		rellenarCampos();
	}
	
	public void rellenarCampos() {
		tfNombre.setText(e.getNombre());
		tfIniciales.setText(e.getIniciales());
	}
	
	public String validacion() {
		String msg = "";
		dao = new EquipoDao();
		
		nombre = tfNombre.getText().toString();
		iniciales = tfIniciales.getText().toString();
		
		if (nombre.equals("")) {
			msg+="Campo 'nombre' no puede estar vacio \n";
		}
		if (iniciales.length() != 3 ) {
			msg+="Campo 'inicial' tiene que tener 3 caracteres \n";
		}
		
		try {
			int resp = dao.verificadorRepetidos(nombre);
			if(editar) {
				if (e.getNombre().equals(nombre)) {
					if (resp>1) {
						System.out.println(resp);
						msg+="Este equipo ya existe";
					}
				}else {
					if (resp>0) {
						msg+="Este equipo ya existe";
					}
				}
				if (resp<0) {
					msg ="Error al verificar los datos";
				}
			}else {
				if (resp>0) {
					msg+="Este equipo ya existe";
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