package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Persona;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.PersonaDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class NuevoUsuarioFXMLController{
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtApellido;
	@FXML
	private TextField txtEdad;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnCancelar;
	private Persona p;
	
	private ObservableList<Persona> lista;
	private int id;
	// Event Listener on Button[#btnGuardar].onAction
	@FXML
	public void guardarPersona(ActionEvent event) {
		
		Stage stage = (Stage) btnGuardar.getScene().getWindow();
		
		PersonaDao pd=new PersonaDao();
		System.out.println(p);
			if(this.comprobar().length()==0) {
				if(p==null) {
					p= new Persona(id, txtNombre.getText(), txtApellido.getText(), txtEdad.getText());
					pd.insertarPersonas(p);
					stage.close();
					this.AniadirAlert(stage);
					
				}else {
					try {
						p= new Persona(id, txtNombre.getText(), txtApellido.getText(), txtEdad.getText());
						pd.modificarPersona(p);
						stage.close();
						this.AniadirAlert(stage);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
				
			}else {
				this.AniadirAlert(stage);
			}
		
		
		
		
		

	}
	// Event Listener on Button[#btnCancelar].onAction
	@FXML
	public void cerrar(ActionEvent event) {
		Stage stage = (Stage) btnGuardar.getScene().getWindow();
		stage.close();
	}
	
	
	
	public void AniadirAlert(Window win) {
		Alert alert;
		String texto=comprobar();
		if(texto.length()==0) {
			System.out.println("aaaaaa");
			alert = new Alert(Alert.AlertType.INFORMATION);
			
			alert.setContentText("Accion hecha correctamente");
			
		}else {
			System.out.println("bbbbb");
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText(texto);
		}
		alert.setHeaderText(null);
		alert.initOwner(win);
		alert.setTitle("TUS DATOS");
		alert.showAndWait();
	}
	public void error (Window win) {
		Alert alert;
		String texto=comprobar();

			System.out.println("bbbbb");
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("FALLO EN LA CONEXION A LA BASE DE DATOS ");
			
		alert.setHeaderText(null);
		alert.initOwner(win);
		alert.setTitle("TUS DATOS");
		alert.showAndWait();
	}
	public void cargarDatos(ObservableList<Persona> lista ) {
		
		
		this.lista=lista;
	}
	
	public void cargarDatos(ObservableList<Persona> lista , Persona p) {
		txtNombre.setText(p.getNombre());
		txtApellido.setText(p.getApellido());
		txtEdad.setText(p.getEdad());
		this.id=p.getId();
		this.p=p;
		this.lista=lista;

	}
	public String comprobar() {
	   
		String mal="";
		boolean fallos=false;
		//NOMBRE
		if (txtNombre.getText().length()==0) {
			mal+="El campo nombre es obligatorio";
		
		}
		//APELLIDO
		if (txtApellido.getText().length()==0) {
			mal+="\n El campo apellido es obligatorio";
		//	fallos++;
		}
		if (txtEdad.getText().length()==0) {
			mal+="\n El campo edad es obligatorio rellenado";
		//	fallos++;
		}
		try {
			int num=Integer.parseInt(txtEdad.getText());
		} catch (NumberFormatException e) {
			if (txtEdad.getText().length()!=0) {
				mal+="\n El campo edad tiene que ser numeros";
			}
		}
		Persona p1 = new Persona(id, txtNombre.getText(), txtApellido.getText(), txtEdad.getText());
		if(lista.contains(p1)) {
			mal+="\n ya existe esta persona en la tabla";
		}
		

		return mal;

	}
	
	
	

}
