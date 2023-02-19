package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.Libro;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.ResourceBundle;

import conexionBD.ConexionBD;
import dao.LibroDao;
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
	private Libro libro, li;
	private LibroDao dao;
	private Boolean editar = false;

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
					msg = dao.crearLibro(libro);
				}else {
					//Editar
					msg = dao.editarLibro(libro);
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
		        	aceptar(event);
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
		this.li=li;
		editar = true;
		lblTexto.setText("EDITAR");	
		tfTitulo.setText(li.getTitulo());
		tfAutor.setText(li.getAutor());
		tfEditorial.setText(li.getEditorial());
		if (li.getEstado().equals("Usado nuevo"))
			cbEstado.getSelectionModel().select(1);
		else if (li.getEstado().equals("Usado seminuevo"))
			cbEstado.getSelectionModel().select(2);
		else if (li.getEstado().equals("Usado estropeado"))
			cbEstado.getSelectionModel().select(3);
		else if (li.getEstado().equals("Usado Restaurado"))
			cbEstado.getSelectionModel().select(4);
	}
	
	public String validacion() {
		String msg = "";
		String titulo = tfTitulo.getText().toString();
		String autor = tfAutor.getText().toString();
		String editorial = tfEditorial.getText().toString();
		String estado = cbEstado.getSelectionModel().getSelectedItem();
		int id = 0;
		int baja = 0;
		if (editar) {
			id = li.getCodigo();
			baja = li.getBaja();
		}
		libro = new Libro(id, titulo, autor, editorial, estado, baja);
		return msg;
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		estados.addAll("Nuevo", "Usado nuevo", "Usado seminuevo", "Usado estropeado", "Restaurado");
		cbEstado.setItems(estados);
		cbEstado.getSelectionModel().select(0);
	}
	
	public void aceptar(ActionEvent event) throws FileNotFoundException {
	    try {
	    	ConexionBD conexion = new ConexionBD();
			Connection con = conexion.getConexion();
	        HashMap<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("codigo", 5);
	        parameters.put("nombre", "xxxx");
	        JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/Informe1.jasper"));
	        JasperPrint jprint = JasperFillManager.fillReport(report, null, con);
	        JasperViewer viewer = new JasperViewer(jprint, false);
	        viewer.setVisible(true);
	    } catch (Exception e) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText(null);
	        alert.initOwner(this.btnEjecuta.getScene().getWindow());
	        alert.setTitle("ERROR");
	        alert.setContentText("Ha ocurrido un error");
	        alert.showAndWait();
	        e.printStackTrace();
	    }
	}
	
}
