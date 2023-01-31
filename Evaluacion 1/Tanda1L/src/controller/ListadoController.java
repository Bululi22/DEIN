package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.AeropuertosDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
//import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;

import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Vuelos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ListadoController implements Initializable{
	@FXML
	private RadioButton rbPublico;
	@FXML
	private RadioButton rbPrivado;
	@FXML
	private TextField tfFiltro;
	@FXML
	private TableView<Vuelos> tabla;
	@FXML
	private TableColumn<Vuelos, Integer> colID;
	@FXML
	private TableColumn<Vuelos, String> colNombre;
	@FXML
	private TableColumn<Vuelos, String> colPais;
	@FXML
	private TableColumn<Vuelos, String> colCiudad;
	@FXML
	private TableColumn<Vuelos, String> colCalle;
	@FXML
	private TableColumn<Vuelos, Integer> colNumero;
	@FXML
	private TableColumn<Vuelos, Integer> colAnio;
	@FXML
	private TableColumn<Vuelos, Integer> colCapacidad;
	@FXML
	private TableColumn<Vuelos, Integer> colSocios;

	private ToggleGroup toggleGroup;
	private ObservableList<Vuelos> lista;
	private Vuelos v;
	
	// Event Listener on MenuItem.onAction
	@FXML
	public void addAeropuerto(ActionEvent event) {
		ventanaAeropuerto(true);
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void editarAeropuerto(ActionEvent event) {
		v = (Vuelos) tabla.getSelectionModel().getSelectedItem();
		if (v!=null) {
			ventanaAeropuerto(false);
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Selecciona un Aeropuerto");
			alert.showAndWait();
		}
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void borrarAeropuerto(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on MenuItem.onAction
	@FXML
	public void infoAeropuerto(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on TextField[#tfFiltro].onKeyTyped
	@FXML
	public void filtrar(KeyEvent event) {
		// TODO Autogenerated
		String nom="";
		ObservableList<Vuelos> listaFiltrada= FXCollections.observableArrayList();
		nom=tfFiltro.getText();
		for (int i = 0; i < lista.size(); i++) {
			if(lista.get(i).getNombre().contains(nom)) {
				listaFiltrada.add(lista.get(i));
			}
		}
		tabla.refresh();
		tabla.setItems(listaFiltrada);
		if(nom.isEmpty()){
			tabla.setItems(lista);
		}
	}

	public void recargarTabla (ObservableList<Vuelos> listaActualizada) {
		@SuppressWarnings("unused")
		AeropuertosDao dao = new AeropuertosDao();
		lista = listaActualizada;
		tabla.setItems(lista);
	}
	
	private void ventanaAeropuerto(Boolean add) {
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VenAeropuertos.fxml"));
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			VenAeropuertosController control = loader.getController();
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.tabla.getScene().getWindow());
			newStage.setResizable(false);
			newStage.setMaximized(false);
			newStage.setScene(newScene);
			if (add) {
				newStage.setTitle("AVIONES - AÑADIR AEROPUERTO");
				control.cargarDatos(lista, true);
				newStage.showAndWait();
				tabla.refresh();
			}else {
				newStage.setTitle("AVIONES - EDITAR AEROPUERTO");
				control.cargarDatos(lista, v, false);
				control.getTfNombre().setText(v.getNombre());
				control.getTfPais().setText(v.getPais());
				control.getTfCiudad().setText(v.getCiudad());
				control.getTfCalle().setText(v.getCalle());
				control.getTfNumero().setText(""+v.getNumero());
				control.getTfAnioInanguracion().setText(""+v.getAnio());
				control.getTfCapacidad().setText(""+v.getCapacidad());
				
				control.getRbPublico().setDisable(true);
				control.getRbPrivado().setDisable(true);
				/*control.getToggleGroup().getToggles().forEach(toggle -> {
				    Node node = (Node) toggle ;
				    node.setDisable(true);
				});*/
				if (rbPrivado.isSelected()) {
					control.getRbPrivado().setSelected(true);
					control.tipoAeropuerto();
				}
				
				
				newStage.showAndWait();
				tabla.refresh();
			}
	    } catch (IOException e) {
	    	e.printStackTrace();
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setHeaderText(null);
	        alert.setTitle("Error");
	        alert.setContentText(e.getMessage());
	        alert.showAndWait();
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lista = FXCollections.observableArrayList();
		AeropuertosDao dao = new AeropuertosDao();
		
		toggleGroup = new ToggleGroup();
		rbPublico.setToggleGroup(toggleGroup);
		rbPublico.setOnAction(e -> recargarTabla(dao.cargarVuelos(true)));
		rbPrivado.setToggleGroup(toggleGroup);
		rbPrivado.setOnAction(e -> recargarTabla(dao.cargarVuelos(false)));

		lista=dao.cargarVuelos(false);
		tabla.setItems(lista);

		colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colID.prefWidthProperty().bind(tabla.widthProperty().multiply(0.04));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colNombre.prefWidthProperty().bind(tabla.widthProperty().multiply(0.16));
		colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
		colPais.prefWidthProperty().bind(tabla.widthProperty().multiply(0.16));
		colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
		colCiudad.prefWidthProperty().bind(tabla.widthProperty().multiply(0.16));
		colCalle.setCellValueFactory(new PropertyValueFactory<>("calle"));
		colCalle.prefWidthProperty().bind(tabla.widthProperty().multiply(0.16));
		colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
		colNumero.prefWidthProperty().bind(tabla.widthProperty().multiply(0.04));
		colAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
		colAnio.prefWidthProperty().bind(tabla.widthProperty().multiply(0.04));
		colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
		colCapacidad.prefWidthProperty().bind(tabla.widthProperty().multiply(0.16));
		colSocios.setCellValueFactory(new PropertyValueFactory<>("numSocios"));
		colSocios.prefWidthProperty().bind(tabla.widthProperty().multiply(0.08));
	}
}
