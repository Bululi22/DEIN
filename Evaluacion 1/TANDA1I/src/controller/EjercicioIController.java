package controller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Persona;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import conexionBD.Propiedades;
import dao.PersonaDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

public class EjercicioIController implements Initializable{
	@FXML
	private TableView tablaPersona;
	@FXML
	private TableColumn colNombre1;
	@FXML
	private TableColumn colApellido1;
	@FXML
	private TableColumn colEdad1;
	@FXML
	private Button btnAnadir;
	@FXML
	private Button btnModificar;
	@FXML
	private Button btnEliminar;
	@FXML
	private TextField txtBuscar;
	
	private ObservableList<Persona> listaPersona;
	
	private Connection con;
	
	private int ultimoId;
	private ResourceBundle bundle;

	// Event Listener on Button[#btnAñadir].onAction
	@FXML
	public void anadirPersona(ActionEvent event) {
		abrirVentana(1);
		
	}
	
	@FXML
	public void modificarPersona(ActionEvent event) {
		abrirVentana(0);
		
	}
	
	public void abrirVentana(int tipo) {
		//CREAR LA VENTANA AÑADIR PERSONA
		 try{
	         FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoUsuarioFXML.fxml"),bundle);
	         Parent root = loader.load();
	         Scene newScene = new Scene(root);
	         Stage newStage = new Stage();
	         NuevoUsuarioFXMLController control= loader.getController();
	        
	         newStage.initModality(Modality.APPLICATION_MODAL);
	         newStage.initOwner(this.btnAnadir.getScene().getWindow());
	         newStage.setResizable(false);
	         newStage.setMaximized(false);
	         newStage.setScene(newScene);
	         if(tipo==1) {
	        	 control.cargarDatos(listaPersona);
	        	 newStage.setTitle("Nueva Persona");
	        	 newStage.showAndWait ();
				 cargarTabla();
			          
		         
	         }else {
	        	
		        	 if (tablaPersona.getSelectionModel().getSelectedItem()==null) {
		         		this.Alert(btnModificar.getScene().getWindow());
		         	}else {
		         		 Persona p =(Persona)tablaPersona.getSelectionModel().getSelectedItem();
		         		 control.cargarDatos(listaPersona,p);

			        	 newStage.setTitle("Modificar Persona");
				         newStage.showAndWait ();
				         cargarTabla();
					          

					      
		         	}
		         
	         }
	        

	   } catch (IOException e) {
	       Alert alert = new Alert(Alert.AlertType.ERROR);
	       alert.setHeaderText(null);
	       alert.setTitle("Error");
	       alert.setContentText(e.getMessage());
	       alert.showAndWait();
//	   } catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
	}
	}
	
	public void cargarTabla() {
		  PersonaDao pd=new PersonaDao();
	         try {
				listaPersona = pd.cargarPersonas();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         tablaPersona.setItems(listaPersona);
	         tablaPersona.getSelectionModel().clearSelection();
		     tablaPersona.refresh();
	}
	public void eliminar(ActionEvent event) {
		if (tablaPersona.getSelectionModel().getSelectedItem()==null) {
     		this.Alert(btnModificar.getScene().getWindow());
     	}else {
     		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoUsuarioFXML.fxml"));
     		NuevoUsuarioFXMLController control= loader.getController();
     		
     		Persona perselect= (Persona) tablaPersona.getSelectionModel().getSelectedItem();
     		listaPersona.remove(perselect);
     		PersonaDao pd=new PersonaDao();
     		pd.eliminarPersona(perselect);
     		eliminarAlert(btnEliminar.getScene().getWindow());
     		   		
     	}
    
    }
	public void filtrar(KeyEvent event) {
		String nom="";
		ObservableList<Persona> listaFiltrada= FXCollections.observableArrayList();
		listaFiltrada=FXCollections.observableArrayList();
		nom=txtBuscar.getText();
		for (int i = 0; i < listaPersona.size(); i++) {
			if(listaPersona.get(i).getNombre().contains(nom)) {
				listaFiltrada.add(listaPersona.get(i));
				
			}
		}
		tablaPersona.refresh();
		tablaPersona.setItems(listaFiltrada);
		if(nom.isEmpty()){
			tablaPersona.setItems(listaPersona);
		}
		
	}
	
	private void Alert(Window win) {
		Alert alert;
		
		alert = new Alert(Alert.AlertType.ERROR);
		alert.setContentText("Debe de selecionar una persona");

		alert.setHeaderText(null);
		alert.initOwner(win);
		alert.setTitle("ERROR");
		alert.showAndWait();
	}
	public void eliminarAlert(Window win) {
		Alert alert;
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Eliminado correctamente");
		
		alert.setHeaderText(null);
		alert.initOwner(win);
		alert.setTitle("ELIMINADO");
		alert.showAndWait();
	}
	//PARA LEER BBDD
	
	public void initialize(URL arg0, ResourceBundle arg1) {
						
		
		
		//PARA QUE LA TABLA FUNCIONE BIEN
		
		try {
			PersonaDao pd=new PersonaDao();
			listaPersona=pd.cargarPersonas();

			tablaPersona.setItems(listaPersona);
			colNombre1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			colNombre1.prefWidthProperty().bind(tablaPersona.widthProperty().multiply(0.40));
			colApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido"));
			colApellido1.prefWidthProperty().bind(tablaPersona.widthProperty().multiply(0.40));
			colEdad1.setCellValueFactory(new PropertyValueFactory<>("edad"));
			colEdad1.setStyle("-fx-alignment: CENTER-RIGHT;");
			colEdad1.prefWidthProperty().bind(tablaPersona.widthProperty().multiply(0.20));
			//IDIOMAS
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma, region));
			bundle = ResourceBundle.getBundle("idiomas/messages");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}


}
