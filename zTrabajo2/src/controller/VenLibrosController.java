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
import model.Libro;
import model.Participacion;

import java.net.URL;
import java.util.ResourceBundle;

import dao.LibroDao;
import dao.ParticipacionDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

public class VenLibrosController implements Initializable{
	@FXML
	private Label lblTexto;
	@FXML
	private TextField tfTitulo;
	@FXML
	private TextField tfAutor;
	@FXML
	private TextField tfEditorial;
	@FXML
	private ComboBox<String> cbEstado;
	@FXML
	private Button btnEjecuta;
	private ObservableList<String> estados = FXCollections.observableArrayList();
	private Libro libro;
	private LibroDao dao;

	// Event Listener on Button[#btnEjecuta].onAction
	@FXML
	public void ejecuta(ActionEvent event) {
		String msg = "";
		
		try {
		
			msg = validacion();
			
			if(msg.equals("")) {
				dao = new LibroDao();
				
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
	
	public void cargarDatos(Libro li) {
		// TODO Auto-generated method stub
		
	}
	
	public String validacion() {
		String msg = "";
		String titulo = tfTitulo.getText().toString();
		String autor = tfAutor.getText().toString();
		String editorial = tfEditorial.getText().toString();
		String estado = cbEstado.getSelectionModel().getSelectedItem();
		libro = new Libro(0, titulo, autor, editorial, estado, 0);
		return msg;
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		estados.addAll("Nuevo", "Usado nuevo", "Usado seminuevo", "Usado estropeado", "Restaurado");
		cbEstado.setItems(estados);
		cbEstado.getSelectionModel().select(0);
	}
}
