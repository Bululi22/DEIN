package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdfViewerFX.PDFViewer;

import conexionBD.ConexionBD;
import dao.EmailDao;
import dao.PersonaDao;
import dao.TelefonoDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Email;
import model.Persona;
import model.Telefono;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class AgendaController implements Initializable {
	@FXML
	private GridPane gridTabla;
	@FXML
	private Label lblTitulo;
	@FXML
	private ListView<Telefono> lvTelefonos;
	@FXML
	private ListView<Email> lvEmails;
	@FXML
	private Button btnNuevoTelefono;
	@FXML
	private Button btnBorrarTelefono;
	@FXML
	private Button btnAnterior;
	@FXML
	private Button btnNuevoEmail;
	@FXML
	private Button btnBorrarEmail;
	@FXML
	private Button btnSiguiente;
	@FXML
	private Label lblNumUsuarios;
	private ObservableList<Persona> listaPersonas;
	private Persona p;
	private int cont;
	private Telefono telefonoSelec;
	private Email emailSelec;
	private PDFViewer visorPDF;
	//Key: DNI, Value:ArrayList
	private HashMap<String, ObservableList<Email>> mapEmail;
	private HashMap<String, ObservableList<Telefono>> mapTelefono;

	
	@FXML
	public void informe1(ActionEvent event) {
		try {
			informe(1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void informe2(ActionEvent event) {
		try {
			informe(2);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void ayuda1(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ayuda.fxml"));
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
	public void ayudaOnline(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AyudaOnline.fxml"));
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
	public void ayudaPDF(ActionEvent event) {
		visorPDF = new PDFViewer();
		try {
			visorPDF.loadPDF(getClass().getResource("/pdf/nombreDelPDF.pdf"));
			Stage stage = new Stage();
			BorderPane borderPane = new BorderPane(visorPDF);
			Scene scene = new Scene(borderPane);
			stage.setTitle("Ayuda PDF");
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (PDFException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	public void telefonoSeleccionado(MouseEvent event) {
		btnBorrarTelefono.setDisable(false);
		telefonoSelec = lvTelefonos.getSelectionModel().getSelectedItem();
	}

	@FXML
	public void emailSeleccionado(MouseEvent event) {
		btnBorrarEmail.setDisable(false);
		emailSelec = lvEmails.getSelectionModel().getSelectedItem();
	}

	@FXML
	public void nuevoTelefono(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Nuevo telefono");
		dialog.setHeaderText(null);
		dialog.initOwner(this.btnAnterior.getScene().getWindow());
		dialog.setContentText("Nuevo telefono:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			TelefonoDao dao = new TelefonoDao();
			String msg = dao.crearTelefono(p.getDni(), result.get());
			if (msg.equals("")) {	
				Telefono t = new Telefono(p.getDni(), Integer.parseInt(result.get()));
				mapTelefono.get(p.getDni()).add(t);
				Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
				alert2.setHeaderText(null);
				alert2.setTitle("Añadir Telefono");
				alert2.setContentText("Telefono añadido");
				alert2.showAndWait();
				cambiarInformacionDeVentana();
			}else {
				Alert alert2 = new Alert(Alert.AlertType.ERROR);
				alert2.setHeaderText(null);
				alert2.initOwner(this.btnAnterior.getScene().getWindow());
				alert2.setTitle("ERROR");
				alert2.setContentText(msg);
				alert2.showAndWait();
			}
		}
	}
	

	@FXML
	public void borrarTelefono(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Eliminar Telefono");
		alert.setContentText("Seguro que quieres eliminar:\n" + telefonoSelec.getTelefono());
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				TelefonoDao dao = new TelefonoDao();
				dao.borrarTelefono(telefonoSelec);
				alert.close();
				Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
				alert2.setHeaderText(null);
				alert2.setTitle("Eliminar Telefono");
				alert2.setContentText("Telefono eliminado");
				alert2.showAndWait();
				lvTelefonos.getSelectionModel().clearSelection();
				btnBorrarTelefono.setDisable(true);
				mapTelefono.get(p.getDni()).remove(telefonoSelec);
				cambiarInformacionDeVentana();
			} catch (Exception e) {
				Alert alert2 = new Alert(Alert.AlertType.ERROR);
				alert2.setHeaderText(null);
				alert2.initOwner(this.btnAnterior.getScene().getWindow());
				alert2.setTitle("ERROR");
				alert2.setContentText("Ha ocurrido un error");
				alert2.showAndWait();
				e.printStackTrace();
			}
		}
	}
	

	@FXML
	public void nuevoEmail(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Nuevo Email");
		dialog.setHeaderText(null);
		dialog.initOwner(this.btnAnterior.getScene().getWindow());
		dialog.setContentText("Nuevo Email:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			EmailDao dao = new EmailDao();
			String msg = dao.crearEmail(p.getDni(), result.get());
			if (msg.equals("")) {
				Email e = new Email(p.getDni(), result.get());
				mapEmail.get(p.getDni()).add(e);
				Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
				alert2.setHeaderText(null);
				alert2.setTitle("Añadir Telefono");
				alert2.setContentText("Telefono añadido");
				alert2.showAndWait();
				cambiarInformacionDeVentana();
			}else {
				Alert alert2 = new Alert(Alert.AlertType.ERROR);
				alert2.setHeaderText(null);
				alert2.initOwner(this.btnAnterior.getScene().getWindow());
				alert2.setTitle("ERROR");
				alert2.setContentText(msg);
				alert2.showAndWait();
			}
		}
		
	}
	
	@FXML
	public void borrarEmail(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Eliminar Email");
		alert.setContentText("Seguro que quieres eliminar:\n" + emailSelec.getEmail());
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				EmailDao dao = new EmailDao();
				dao.borrarEmail(emailSelec);
				alert.close();
				Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
				alert2.setHeaderText(null);
				alert2.setTitle("Eliminar Email");
				alert2.setContentText("Email eliminado");
				alert2.showAndWait();
				btnBorrarEmail.setDisable(true);
				mapEmail.get(p.getDni()).remove(emailSelec);
				cambiarInformacionDeVentana();
			}catch (Exception e) {
				Alert alert2 = new Alert(Alert.AlertType.ERROR);
		        alert2.setHeaderText(null);
		        alert2.initOwner(this.btnAnterior.getScene().getWindow());
		        alert2.setTitle("ERROR");
		        alert2.setContentText("Ha ocurrido un error");
		        alert2.showAndWait();
				e.printStackTrace();
			}
		}
	}
	

	@FXML
	public void usuarioAnterior(ActionEvent event) {
		cont--;
		cambiarInformacionDeVentana();
	}

	
	@FXML
	public void usuarioSiguiente(ActionEvent event) {
		cont++;
		cambiarInformacionDeVentana();
	}
	

	public void cargarDatos() {
		for (int i = 0; i < listaPersonas.size(); i++) {
			Persona paux = listaPersonas.get(i);
	
			//Cargar telefonos
			try {
				TelefonoDao telDao = new TelefonoDao();
				ObservableList<Telefono> telefonos = telDao.cargarTelefonos(paux.getDni());
				mapTelefono.put(paux.getDni(), telefonos);
				System.out.println("Telefono bien");
			} catch (Exception e) {
				System.out.println(e);
			}
			
			//Cargar emails
			try {
				EmailDao emaDao = new EmailDao();
				ObservableList<Email> emails = emaDao.cargarEmail(paux.getDni());
				mapEmail.put(paux.getDni(), emails);
				System.out.println("Email bien");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		cambiarInformacionDeVentana();
	}
	
	public void cambiarInformacionDeVentana() {
		p = listaPersonas.get(cont);
		if (cont == 0) {
			btnAnterior.setDisable(true);
			btnSiguiente.setDisable(false);
		} else if (cont == listaPersonas.size() - 1) {
			btnAnterior.setDisable(false);
			btnSiguiente.setDisable(true);
		} else {
			btnAnterior.setDisable(false);
			btnSiguiente.setDisable(false);
		}
		lblTitulo.setText(p.getDni() + " - " + p.getNombre() + " " + p.getApellido1() + " " + p.getApellido1() + " ("
				+ p.getAnio_nacimiento() + ")");
		lblNumUsuarios.setText(cont + 1 + " / " + listaPersonas.size());

		lvTelefonos.getItems().clear();
		lvEmails.getItems().clear();
		lvTelefonos.getItems().addAll(mapTelefono.get(p.getDni()));
		lvEmails.getItems().addAll(mapEmail.get(p.getDni()));
	}

	public void informe(int num) throws FileNotFoundException {
		try {
			ConexionBD conexion = new ConexionBD();
			Connection con = conexion.getConexion();
			String aux = "";
			System.out.println(num);
			if (num == 1) {
				aux = "/reports/Informe1.jasper";
			} else {
				aux = "/reports/Informe2_padre.jasper";
			}
			JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource(aux));
			JasperPrint jprint = JasperFillManager.fillReport(report, null, con);
			JasperViewer viewer = new JasperViewer(jprint, false);
			viewer.setVisible(true);
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.initOwner(this.btnAnterior.getScene().getWindow());
			alert.setTitle("ERROR");
			alert.setContentText("Ha ocurrido un error");
			alert.showAndWait();
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			PersonaDao dao = new PersonaDao();
			listaPersonas = dao.cargarPersonas();
			mapEmail = new HashMap<String, ObservableList<Email>>();
			mapTelefono = new HashMap<String, ObservableList<Telefono>>();
			cont = 0;
			cargarDatos();
		} catch (Exception e) {
			System.out.println("ERROR AL EMPEZAR");
			System.out.println(e);

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("No se han encontrado personas");
			alert.showAndWait();
			Stage stage = (Stage) btnAnterior.getScene().getWindow();
			stage.close();
		}

	}
}
