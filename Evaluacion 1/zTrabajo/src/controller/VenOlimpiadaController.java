package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Olimpiada;
import dao.OlimpiadaDao;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

public class VenOlimpiadaController {
	@FXML
	private Label lblTexto;
	@FXML
	private TextField tfNombre;
	@FXML
	private TextField tfAnio;
	@FXML
	private RadioButton rbVerano;
	@FXML
	private ToggleGroup tgTemporada;
	@FXML
	private RadioButton rbInvierno;
	@FXML
	private TextField tfCiudad;
	@FXML
	private Button btnEjecuta;
	private OlimpiadaDao dao;
	private Boolean editar = false;
	private Olimpiada o;
	private String nombre, anio, temporada, ciudad;
	
	// Event Listener on Button[#btnEjecuta].onAction
	@FXML
	public void ejecuta(ActionEvent event) {
		String msg = "";
		
		try {
		
			msg = validacion();
			
			if(msg.equals("")) {
				if(!editar) {
					//Crear
					Olimpiada ol = new Olimpiada(-1, nombre, Integer.parseInt(anio), temporada, ciudad);		
					msg = dao.crearOlimpiada(ol);
				}else {
					//Editar
					Olimpiada ol = new Olimpiada(o.getId_olimpiada(), nombre, Integer.parseInt(anio), temporada, ciudad);
					msg = dao.editarOlimpiada(ol);
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
	
	public void cargarDatos(Olimpiada o) {
		this.o=o;
		editar = true;
		lblTexto.setText("EDITAR");
		rellenarCampos();
	}
	
	public void rellenarCampos() {

		tfNombre.setText(o.getNombre());
		tfAnio.setText(o.getAnio()+"");
		if (o.getTemporada().equals("Summer")) {
			rbVerano.setSelected(true);
		}else {
			rbInvierno.setSelected(true);
		}
		tfCiudad.setText(o.getCiudad());

	}
	
	public String validacion() {
		String msg = "";
		dao = new OlimpiadaDao();
			
		nombre = tfNombre.getText().toString();
		anio = tfAnio.getText().toString();
		if (rbInvierno.isSelected()) {
			temporada = "Winter";
		}else {
			temporada = "Summer";
		}
		ciudad = tfCiudad.getText().toString();
		
		
		if (nombre.equals("")) {
			msg+="Campo 'nombre' no puede estar vacio \n";
		}
		if (anio.equals("")) {
			msg+="Campo 'año' no puede estar vacio \n";
		}
		try {
			int aux = Integer.parseInt(anio);
			System.out.println(aux);
			if (aux>10000 || aux<1000) {
				msg+="Campo 'año' tiene que ser entre 1000-10000 \n";
			}
		}catch (NumberFormatException e) {
			msg += "Campo 'año' tiene que ser numerico \n"; 
		}
		if (ciudad.equals("")) {
			msg+="Campo 'ciudad' no puede estar vacio \n";
		}
		try {
			int resp = dao.verificadorRepetidos(nombre, temporada);
			if(editar) {
				if (o.getNombre().equals(nombre) && o.getTemporada().equals(temporada)) {
					if (resp>1) {
						System.out.println(resp);
						msg+="Esta olimpiada ya existe";
					}
				}else {
					if (resp>0) {
						msg+="Esta olimpiada ya existe";
					}
				}
				if (resp<0) {
					msg ="Error al verificar los datos";
				}
			}else {
				if (resp>0) {
					msg+="Esta olimpiada ya existe";
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