package controllers;



import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import dao.EmailsDao;
import dao.PersonasDao;
import dao.TelefonosDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.Email;
import model.Persona;
import model.Telefono;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ConexionDB;

public class ControladorVentanaPrincipal implements Initializable{
	
	@FXML
	private Label lblTitulo;
	@FXML
	private ListView<Telefono> listViewTelefonos;
	@FXML
	private ListView<Email> listViewEmails;
	@FXML
	private Button btnTlfNuevo;
	@FXML
	private Button btnTlfBorrar;
	@FXML
	private Button btnEmailNuevo;
	@FXML
	private Button btnEmailBorrar;
	@FXML
	private Button btnPersonaAnterior;
	@FXML
	private Button btnPersonaSiguiente;
	@FXML
	private Label labelPosicion;
	@FXML
	private MenuItem miInforme1;
	@FXML
	private MenuItem miInforme2;
	@FXML
	private MenuItem miAyuda;
	@FXML
	private MenuItem miAyudaOnline;

	private final PersonasDao daoPersonas = new PersonasDao();
	private final EmailsDao daoEmails = new EmailsDao();
	private final TelefonosDao daoTelefonos = new TelefonosDao();
	
	private ArrayList<Persona> personas;

	private int pos;
	private Persona persona;
	private ObservableList<Telefono> listaTelefonos;
	private ObservableList<Email> listaEmails;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		personas = daoPersonas.getpersonas();
		btnPersonaAnterior.setDisable(true);
		if(personas.size()==0) {
			mostrarAlertError("No se han encontrado personas en la base");
		}
		
		pos = 1;
		persona = personas.get(pos-1);
		actualizarPos();
		
		listViewEmails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Email>() {
			@Override
			public void changed(ObservableValue<? extends Email> observable, Email oldvalue, Email newValue) {
				btnEmailBorrar.setDisable(false);
			}
		});

		listViewTelefonos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Telefono>() {
			@Override
			public void changed(ObservableValue<? extends Telefono> observable, Telefono oldvalue, Telefono newValue) {
				btnTlfBorrar.setDisable(false);
			}
		});
		
	}
	
	private void mostrarAlertError(String text) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setTitle("Error");
		alert.setContentText(text);
		alert.showAndWait();
	}
	private void mostrarAlertInfo(String text) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Aceptado");
		alert.setContentText(text);
		alert.showAndWait();
	}
	private void actualizarPos() {
		try {
			persona = personas.get(pos - 1);
			lblTitulo.setText(persona.toString());
			labelPosicion.setText(pos + "/" + personas.size());

			listaEmails = FXCollections.observableArrayList();
			listViewEmails.setItems(listaEmails);
			ArrayList<Email> emails = daoEmails.getEmailsPersona(persona);
			for (Email email : emails) {
				listaEmails.add(email);
			}

			listaTelefonos = FXCollections.observableArrayList();
			listViewTelefonos.setItems(listaTelefonos);
			ArrayList<Telefono> telefonos = daoTelefonos.getTelefonosPersona(persona);
			for (Telefono tel : telefonos) {
				listaTelefonos.add(tel);
			}
		} catch (IndexOutOfBoundsException e) {
			pos = personas.indexOf(persona) + 1;
			mostrarAlertError("No hay más alumnos");
		}

	}
	
	
	@FXML
	public void personaAnterior(ActionEvent event) {
		pos--;
		if (pos==1) {
			btnPersonaAnterior.setDisable(true);
			btnPersonaSiguiente.setDisable(false);
		}
		else {
			btnPersonaAnterior.setDisable(false);
			btnPersonaSiguiente.setDisable(false);
		}
		actualizarPos();
	}

	@FXML
	public void personaSiguiente(ActionEvent event) {
		pos++;
		if (pos==daoPersonas.getpersonas().size()) {
			btnPersonaSiguiente.setDisable(true);
			btnPersonaAnterior.setDisable(false);
		}
		else {
			btnPersonaSiguiente.setDisable(false);
			btnPersonaAnterior.setDisable(false);
		}
		actualizarPos();
	}
	
	private String mostrarTextInputDialog(String parametro) {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Nuevo " + parametro);
		dialog.setHeaderText(null);
		dialog.setContentText("Introduce el nuevo " + parametro);
		Optional<String> dato = dialog.showAndWait();
		if (dato.isPresent()) {
			String dato_sin_optional = dato.get();
			return dato_sin_optional;
		}
		else {
			System.out.println("hols");
			return "cancelar";
		}
	}
	
	@FXML
	public void crearTelefono(ActionEvent event) {
		String tel = mostrarTextInputDialog("teléfono");
		if(tel.equals("cancelar")) {
			return;
		}
		boolean valido = true;
		if (tel.length() != 9) {
			mostrarAlertError("La longitud del teléfono tiene que ser de 9");
			valido = false;
		} else {
			for (Telefono telefono : listaTelefonos) {
				if (telefono.getTelefono().equals(tel)) {
					mostrarAlertError("Telefono repetido");
					valido = false;
				}
			}
		}

		if (valido) {
			daoTelefonos.insertTelefono(tel, persona.getDni());
			actualizarPos();
			mostrarAlertInfo("Teléfono añadido");
		}
	}
	
	@FXML
	public void crearEmail(ActionEvent event) {
		String emailIntro = mostrarTextInputDialog("Email");
		if(emailIntro.equals("cancelar")) {
			return;
		}
		boolean valido = true;
		if (!emailIntro.contains("@")) {
			mostrarAlertError("Email inválido debe contener una @");
			valido = false;
		}
		else {
			for(Email email : listaEmails) {
				if(email.getEmail().equals(emailIntro)) {
					mostrarAlertError("Email repetido");
					valido = false;
				}
			}
		}

		if (valido) {
			daoEmails.insertEmail(emailIntro, persona.getDni());
			actualizarPos();
			mostrarAlertInfo("Email añadido");
		}
	}
	
	@FXML
	public void borrarTelefono(ActionEvent event) {
		Telefono tel = listViewTelefonos.getSelectionModel().getSelectedItem();
		if (daoTelefonos.deleteTelefono(tel.getTelefono(), tel.getPersona().getDni())) {
			actualizarPos();
			mostrarAlertInfo("Telefono eliminado correctamente");
		} else {
			mostrarAlertError("Error al eliminar el telefono");
		}
	}
	
	@FXML
	public void borrarEmail(ActionEvent event) {
		Email email = listViewEmails.getSelectionModel().getSelectedItem();
		if (daoEmails.deleteEmail(email.getEmail(), email.getPersona().getDni())) {
			actualizarPos();
			mostrarAlertInfo("Email eliminado correctamente");
		} else {
			mostrarAlertError("Error al eliminar el email");
		}
	}
	
	
	@FXML
	public void abrirMiAyuda(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VistaMiAyuda.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Ayuda");
			stage.show();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	@FXML
	public void abrirAyudaOnline(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VistaAyudaOnline.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Ayuda Online");
			stage.show();
		} catch (IOException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	@FXML
	public void abrirInforme1(ActionEvent event) {
		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informes/Informe1.jasper"));
			JasperPrint jprint;
			try {
				jprint = JasperFillManager.fillReport(report, parameters, new ConexionDB().getConexion());
				JasperViewer viewer = new JasperViewer(jprint, false);
				viewer.setVisible(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (JRException e) {
			e.printStackTrace();
		}
	}


}