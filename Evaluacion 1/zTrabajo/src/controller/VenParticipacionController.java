package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Deportista;
import model.Equipo;
import model.Evento;
import model.Participacion;

import java.net.URL;
import java.util.ResourceBundle;

import dao.DeportistaDao;
import dao.EquipoDao;
import dao.EventoDao;
import dao.ParticipacionDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

public class VenParticipacionController implements Initializable{
	@FXML
	private Label lblTexto;
	@FXML
	private ComboBox<Deportista> cbDeportista;
	@FXML
	private ComboBox<Evento> cbEvento;
	@FXML
	private ComboBox<Equipo> cbEquipo;
	@FXML
	private TextField tfEdad;
	@FXML
	private ComboBox<String> cbMedalla;
	@FXML
	private Button btnEjecuta;

	private Boolean editar = false;
	private Participacion p;
	private String edad, medalla;
	private Deportista dep;
	private Evento eve;
	private Equipo equ;
	
	private ParticipacionDao parDao;
	private DeportistaDao depDao;
	private EventoDao eveDao;
	private EquipoDao equDao;
	
	
	private ObservableList<Deportista> deportistas = FXCollections.observableArrayList();
	private ObservableList<Evento> eventos = FXCollections.observableArrayList();
	private ObservableList<Equipo> equipos = FXCollections.observableArrayList();
	private ObservableList<String> medallas = FXCollections.observableArrayList();
	
	// Event Listener on Button[#btnEjecuta].onAction
	@FXML
	public void ejecuta(ActionEvent event) {
		String msg = "";
		
		try {
		
			msg = validacion();
			
			if(msg.equals("")) {
				parDao = new ParticipacionDao();
				System.out.println("1");
				Participacion pa = new Participacion(dep, eve, equ, edad, medalla);
				System.out.println("2");
				if(!editar) {
					//Crear
					msg = parDao.crearParticipacion(pa);
				}else {
					//Editar
					String id_deportista, id_evento;
					System.out.println("3");
					id_deportista = p.getDeportista().getId_deportista();
					System.out.println("4");
					id_evento = p.getEvento().getId_evento();
					System.out.println("5");
					msg = parDao.editarParticipacion(pa, id_deportista, id_evento);
					System.out.println("6");
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
	
	public void cargarDatos(Participacion p) {
		this.p=p;
		editar = true;
		lblTexto.setText("EDITAR");
		rellenarCampos();
	}
	
	public void rellenarCampos() {
		try {
			if (p.getEdad() != null) {
				tfEdad.setText(p.getEdad());
			}
			
			Deportista depAux = depDao.getDeportista(p.getDeportista().getId_deportista());
			int cont = 0;
			for (Deportista x:deportistas) {
				if (x.getNombre().equals(depAux.getNombre())) {
					cbDeportista.getSelectionModel().select(cont);
					break;
				}
				cont++;
			}
			
			
			Evento eveAux = eveDao.getEvento(p.getEvento().getId_evento());
			cont = 0;
			for (Evento x:eventos) {
				if (x.getNombre().equals(eveAux.getNombre())) {
					cbEvento.getSelectionModel().select(cont);
					break;
				}
				cont++;
			}
			
			
			Equipo equAux = equDao.getEquipo(p.getEquipo().getId_equipo());
			cont = 0;
			for (Equipo x:equipos) {
				if (x.getNombre().equals(equAux.getNombre())) {
					cbEquipo.getSelectionModel().select(cont);
					break;
				}
				cont++;
			}
			try {
				if (p.getMedalla().equals("Gold")) {
					cbMedalla.getSelectionModel().select(1);
				}else if (p.getMedalla().equals("Silver")) {
					cbMedalla.getSelectionModel().select(2);
				}else if (p.getMedalla().equals("Bronze")) {
					cbMedalla.getSelectionModel().select(3);
				}
			}catch (Exception e) {}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public String validacion() {
		String msg = "";
		dep = (Deportista) cbDeportista.getSelectionModel().getSelectedItem();
		eve = (Evento) cbEvento.getSelectionModel().getSelectedItem();
		equ = (Equipo) cbEquipo.getSelectionModel().getSelectedItem();		
		System.out.println("10");
		edad = tfEdad.getText().toString();
		System.out.println("11");
		medalla = cbMedalla.getSelectionModel().getSelectedItem().toString();
		System.out.println("89899898899898989");
		System.out.println(edad);
		System.out.println("89899898899898989");
		if (!edad.equals("")) {
			try {
				int aux = Integer.parseInt(edad);
				if (aux>80 || aux<10) {
					msg+="Campo 'edad' tiene que ser entre 10-80 \n";
				}
			}catch (NumberFormatException e) {
				msg += "Campo 'edad' tiene que ser numerico \n"; 
			}
		}
		return msg;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		parDao = new ParticipacionDao();
		depDao = new DeportistaDao();
		eveDao = new EventoDao();
		equDao = new EquipoDao();
		try {
			deportistas = depDao.cargarDeportista();
			eventos = eveDao.cargarEvento();
			equipos = equDao.cargarEquipo();
			medallas.addAll("Ninguna", "Gold", "Silver", "Bronze");

		
			cbDeportista.setItems(deportistas);
			cbDeportista.getSelectionModel().select(0);
			
			cbEvento.setItems(eventos);
			cbEvento.getSelectionModel().select(0);
			
			cbEquipo.setItems(equipos);
			cbEquipo.getSelectionModel().select(0);
			
			cbMedalla.setItems(medallas);
			cbMedalla.getSelectionModel().select(0);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

