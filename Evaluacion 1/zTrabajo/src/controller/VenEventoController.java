package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Deporte;
import model.Evento;
import model.Olimpiada;

import java.net.URL;
import java.util.ResourceBundle;

import dao.DeporteDao;
import dao.EventoDao;
import dao.OlimpiadaDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

public class VenEventoController implements Initializable {
	@FXML
	private Label lblTexto;
	@FXML
	private TextField tfNombre;
	@FXML
	private ComboBox<Olimpiada> cbOlimpiada;
	@FXML
	private ComboBox<Deporte> cbDeporte;
	@FXML
	private Button btnEjecuta;

	private Boolean editar = false;
	private Evento e;
	private String nombre;
	private Olimpiada o;
	private Deporte d;
	private EventoDao eveDao;
	private OlimpiadaDao oliDao;
	private DeporteDao depDao;
	private ObservableList<Olimpiada> olimpiadas = FXCollections.observableArrayList();
	private ObservableList<Deporte> deportes = FXCollections.observableArrayList();
	
	// Event Listener on Button[#btnEjecuta].onAction
	@FXML
	public void ejecuta(ActionEvent event) {
		String msg = "";
		
		try {
		
			msg = validacion();
			
			if(msg.equals("")) {
				if(!editar) {
					//Crear
					Evento ev= new Evento(null, nombre, o, d);
					msg = eveDao.crearEvento(ev);
				}else {
					//Editar
					Evento ev= new Evento(e.getId_evento(), nombre, o, d);
					msg = eveDao.editarEvento(ev);
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
	
	public void cargarDatos(Evento e) {
		this.e=e;
		editar = true;
		lblTexto.setText("EDITAR");
		rellenarCampos();
	}
	
	public void rellenarCampos() {
		try {
			tfNombre.setText(e.getNombre());
			
			Olimpiada oAux = oliDao.getOlimpiada(e.getOlimpiada().getId_olimpiada()+"");
			int cont = 0;
			for (Olimpiada oli:olimpiadas) {
				if (oli.getNombre().equals(oAux.getNombre())) {
					cbOlimpiada.getSelectionModel().select(cont);
					break;
				}
				cont++;
			}
			
			Deporte dAux = depDao.getDeporte(e.getDeporte().getId_deporte());
			cont = 0;
			for (Deporte dep:deportes) {
				if (dep.getNombre().equals(dAux.getNombre())) {
					cbDeporte.getSelectionModel().select(cont);
					break;
				}
				cont++;
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public String validacion() {
		String msg = "";
		
		nombre = tfNombre.getText().toString();
		o = (Olimpiada) cbOlimpiada.getSelectionModel().getSelectedItem();
		d = (Deporte) cbDeporte.getSelectionModel().getSelectedItem();
		
		if (nombre.equals("")) {
			msg+="Campo 'nombre' no puede estar vacio \n";
		}
		
		try {
			int resp = eveDao.verificadorRepetidos(nombre,o.getId_olimpiada(),d.getId_deporte());
			if(editar) {
				if (e.getNombre().equals(nombre) && e.getOlimpiada().getId_olimpiada() == o.getId_olimpiada() && e.getDeporte().getId_deporte().equals(d.getId_deporte())) {
					if (resp>1) {
						System.out.println(resp);
						msg+="Este evento ya existe";
					}
				}else {
					if (resp>0) {
						msg+="Este evento ya existe";
					}
				}
				if (resp<0) {
					msg ="Error al verificar los datos";
				}
			}else {
				if (resp>0) {
					msg+="Este evento ya existe";
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		eveDao = new EventoDao();
		oliDao = new OlimpiadaDao();
		depDao = new DeporteDao();
		
		try {
			olimpiadas = oliDao.cargarOlimpiadas();
			deportes = depDao.cargarDeportes();
		
			cbOlimpiada.setItems(olimpiadas);
			cbOlimpiada.getSelectionModel().select(0);
			
			cbDeporte.setItems(deportes);
			cbDeporte.getSelectionModel().select(0);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
