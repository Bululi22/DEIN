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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Persona;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.PersonaDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

public class EjercicioHController implements Initializable{
	@FXML
	private TableView<Persona> tablaPersona;
	@FXML
	private TableColumn<Persona, String> colNombre1;
	@FXML
	private TableColumn<Persona, String> colApellido1;
	@FXML
	private TableColumn<Persona, String> colEdad1;
	@FXML
	private Button btnAñadir;
	@FXML
	private Button btnModificar;
	@FXML
	private Button btnEliminar;
	@FXML
	private TextField txtBuscar;
	
	private ObservableList<Persona> listaPersona;

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
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoUsuarioFXML.fxml"));
			 Parent root = loader.load();
	         Scene newScene = new Scene(root);
	         Stage newStage = new Stage();
	         
	         NuevoUsuarioFXMLController control= loader.getController();
	         control.cargarLista(listaPersona);
	         newStage.initModality(Modality.APPLICATION_MODAL);
	         newStage.initOwner(this.btnAñadir.getScene().getWindow());
	         newStage.setResizable(false);
	         newStage.setMaximized(false);
	         newStage.setScene(newScene);
	         if(tipo==1) {
	        	 newStage.setTitle("Nueva Persona");
		         tablaPersona.refresh();
		         newStage.showAndWait ();
		         if(control.comprobar().length()==0) {
		        	  Persona p= control.getP();
		        	  PersonaDao pd=new PersonaDao();
		        	  pd.insertarPersonas(p);
			          listaPersona.add(p);
			          tablaPersona.refresh();
		         }
	         }
	         if(tipo==0) {
	        	 if (tablaPersona.getSelectionModel().getSelectedItem()==null) {
	         		this.Alert(btnModificar.getScene().getWindow());
        	 }else {
	         		 Persona p =(Persona)tablaPersona.getSelectionModel().getSelectedItem();
	         		 control.getTxtNombre().setText(p.getNombre());
			         control.getTxtApellido().setText(p.getApellido());
			         control.getTxtEdad().setText(p.getEdad());	    
	         		 
		        	 newStage.setTitle("Modificar Persona");
			         tablaPersona.refresh();
			         newStage.showAndWait ();
			         
			         if(control.comprobar().length()==0) {
			        	  p.setNombre(control.getTxtNombre().getText());
			        	  p.setApellido(control.getTxtApellido().getText());
			        	  p.setEdad(control.getTxtEdad().getText());
			        	  PersonaDao pd=new PersonaDao();
			        	  pd.modificarPersona(p);
				          tablaPersona.refresh();
			         }
	         	}		
	         }
	   } catch (IOException e) {
		   e.printStackTrace();
	       Alert alert = new Alert(Alert.AlertType.ERROR);
	       alert.setHeaderText(null);
	       alert.setTitle("Error");
	       alert.setContentText(e.getMessage());
	       alert.showAndWait();
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   }
	}
	
	public void eliminar(ActionEvent event) {
		if (tablaPersona.getSelectionModel().getSelectedItem()==null) {
     		this.Alert(btnModificar.getScene().getWindow());
     	}else {
     		Persona perselect= (Persona) tablaPersona.getSelectionModel().getSelectedItem();
     		listaPersona.remove(perselect);
     		PersonaDao pd=new PersonaDao();
     		pd.eliminarPersona(perselect);
     		eliminarAlert(btnEliminar.getScene().getWindow());
     		tablaPersona.getSelectionModel().clearSelection();
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
	
	public void initialize(URL arg0, ResourceBundle arg1) {	
		//PARA QUE LA TABLA FUNCIONE BIEN
		try {
			PersonaDao pd=new PersonaDao();
			if(pd.cargarPersonas()==null) {
				listaPersona= FXCollections.observableArrayList();
			}else {
				listaPersona = pd.cargarPersonas();
			}
			tablaPersona.setItems(listaPersona);
			colNombre1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			colNombre1.prefWidthProperty().bind(tablaPersona.widthProperty().multiply(0.40));
			colApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido"));
			colApellido1.prefWidthProperty().bind(tablaPersona.widthProperty().multiply(0.40));
			colEdad1.setCellValueFactory(new PropertyValueFactory<>("edad"));
			colEdad1.prefWidthProperty().bind(tablaPersona.widthProperty().multiply(0.20));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}


}
