package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

import dao.LibroDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Libro;

public class TablaController {
	@FXML
	private GridPane gridTabla;
	@FXML
	private Label lblCampo;
	@FXML
	private Button btnAlta;
	@FXML
	private Button btnModificacion;
	@FXML
	private Button btnBaja;
	@FXML
	private TableView tabla;
	@FXML
	private TextField tfFiltro;
	
	private Libro li;
	@SuppressWarnings("rawtypes")
	private ObservableList lista, listaFiltrada;
	private String seleccion, texto;


	// Event Listener on Button[#btnAlta].onAction
	@FXML
	public void alta(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ven" + seleccion + ".fxml"));
			Stage newStage = nuevaVentana(loader);
			
			
			newStage.setTitle("Crear " + seleccion);
	
			if (seleccion.equals("Libros")) {
				VenLibrosController control = loader.getController();
			}
//			} else if (seleccion.equals("Deporte")) {
//				VenDeporteController control = loader.getController();
//				control.cargarDatos(d);
//			}else if(seleccion.equals("Evento")){
//				VenEventoController control = loader.getController();
//				control.cargarDatos(ev);
//			}else if(seleccion.equals("Olimpiada")){
//				VenOlimpiadaController control = loader.getController();
//				control.cargarDatos(o);
//			}else if(seleccion.equals("Participacion")){
//				VenParticipacionController control = loader.getController();
//				control.cargarDatos(p);
//			}else if(seleccion.equals("Deportista")){
//				VenDeportistaController control = loader.getController();
//				control.cargarDatos(de);
//			}
	
			newStage.showAndWait();
			recargarTabla();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Event Listener on Button[#btnModificacion].onAction
	@FXML
	public void modificacion(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ven" + seleccion + ".fxml"));
			Stage newStage = nuevaVentana(loader);
			
			
			newStage.setTitle("Editar " + seleccion);
	
			if (seleccion.equals("Libros")) {
				VenLibrosController control = loader.getController();
				 control.cargarDatos(li);
			}

			newStage.showAndWait();
			recargarTabla();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Event Listener on Button[#btnBaja].onAction
	@FXML
	public void baja(ActionEvent event) {
		// TODO Autogenerated
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Dar de baja");
		alert.setContentText("Seguro que quieres dar de baja a:\n" + texto);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {		
			if (seleccion.equals("Equipo")) {
//				EquipoDao dao = new EquipoDao();
//				dao.borrarEquipo(eq);
			} else if (seleccion.equals("Libros")) {
				LibroDao dao = new LibroDao();
				li.setBaja(1);
				dao.editarLibro(li);
				tabla.refresh();
				System.out.println("sisisi");
			}
		}
	}
	// Event Listener on TextField[#tfFiltro].onKeyTyped
	@FXML
	public void filtroBasico(KeyEvent event) {
		// TODO Autogenerated
		String nom="";
		listaFiltrada=FXCollections.observableArrayList();
		nom=tfFiltro.getText();
		if (seleccion.equals("Libros")) {
			for (int i = 0; i < lista.size(); i++) {
				if(((Libro) lista.get(i)).getTitulo().contains(nom)) {
					listaFiltrada.add(lista.get(i));
				}
			}
		}
		tabla.refresh();
		tabla.setItems(listaFiltrada);
		if(nom.isEmpty()){
			tabla.setItems(lista);
		}
	}
	// Event Listener on TableView[#tabla].onMouseClicked
	@FXML
	public void clickTabla(MouseEvent event) {
		// TODO Autogenerated
		seleccionado();
	}
	
	
	public Stage nuevaVentana(FXMLLoader loader) throws IOException {
		Parent root = loader.load();
		Scene newScene = new Scene(root);
		Stage newStage = new Stage();

		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.initOwner(this.btnAlta.getScene().getWindow());
		newStage.setResizable(false);
		newStage.setMaximized(false);
		newStage.setScene(newScene);
		newScene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		return newStage;
	}
	
	
	public void seleccionado() {
		if (seleccion.equals("Alumnos")) {
			//
		}else if(seleccion.equals("Libros")){
			li = (Libro) tabla.getSelectionModel().getSelectedItem();
			if (li != null) {
				btnBaja.setDisable(false);
				btnModificacion.setDisable(false);
				texto = li.toString();
			}
		}
	}
	
	public void recargarTabla() {
		try {
			if (seleccion.equals("Alumnos")) {
//				EquipoDao dao = new EquipoDao();
//				lista = dao.cargarEquipo();
			}else if(seleccion.equals("Libros")){
				LibroDao dao = new LibroDao();
				lista = dao.cargarLibros();
			}
			
			tabla.setItems(lista);
		}catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error al recargar la tabla");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	public void cargarDatos(String s) {
		seleccion = s;
		inicio();
	}

	public void inicio() {
		lblCampo.setText(seleccion);
		if (seleccion.equals("Alumnos")) {
//			tablaAlumnos();
		}else if(seleccion.equals("Libros")){
			tablaLibros();
			btnBaja.setDisable(true);
			btnModificacion.setDisable(true);
		}else if(seleccion.equals("Prestamos")){
//			tablaPrestamos();
		}else if(seleccion.equals("GestionDeDevoluciones")){
//			tablaGestionDeDevoluciones();
		}else if(seleccion.equals("Historico_prestamos")){
//			tablaHistoricoPrestamos();
		}
	}
		
	
	public void tablaLibros() {
		try {
			LibroDao dao = new LibroDao();
			System.out.println("*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*");
			lista = dao.cargarLibros();
			
			
			TableColumn<Libro, Integer> colCodigo = new TableColumn<>("CODIGO");
			colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
			colCodigo.prefWidthProperty().bind(tabla.widthProperty().multiply(0.1));
			
			TableColumn<Libro, String> colTitulo = new TableColumn<>("TITULO");
			colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
			colTitulo.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Libro, String> colAutor = new TableColumn<>("AUTOR");
			colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
			colAutor.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Libro, String> colEditorial = new TableColumn<>("EDITORIAL");
			colEditorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));
			colEditorial.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Libro, String> colEstado = new TableColumn<>("ESTADO");
			colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
			colEstado.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Libro, Integer> colBaja = new TableColumn<>("BAJA");
			colBaja.setCellValueFactory(new PropertyValueFactory<>("baja"));
			colBaja.prefWidthProperty().bind(tabla.widthProperty().multiply(0.1));
			
			tabla.getColumns().addAll(colCodigo, colTitulo, colAutor, colEditorial, colEstado, colBaja);
			tabla.setItems(lista);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
