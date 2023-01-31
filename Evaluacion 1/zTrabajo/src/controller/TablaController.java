package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.util.Optional;

import dao.DeporteDao;
import dao.DeportistaDao;
import dao.EquipoDao;
import dao.EventoDao;
import dao.OlimpiadaDao;
import dao.ParticipacionDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Deporte;
import model.Deportista;
import model.Equipo;
import model.Evento;
import model.Olimpiada;
import model.Participacion;

public class TablaController{
	@FXML
	private GridPane gridTabla;
	@FXML
	private Label lblCampo;
	@FXML
	private Button btnCrear;
	@FXML
	private TableView tabla;

	private Deporte d;
	private Equipo eq;
	private Evento ev;
	private Olimpiada o;
	private Deportista de;
	private Participacion p;
	@SuppressWarnings("rawtypes")
	private ObservableList lista;
	private String seleccion, texto;
	
	// Event Listener on Button[#btnCrear].onAction
	@FXML
	public void crear(ActionEvent event) {
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ven"+seleccion+".fxml"));
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();
			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.btnCrear.getScene().getWindow());
			newStage.setResizable(false);
			newStage.setMaximized(false);
			newStage.setScene(newScene);
			newScene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			
			newStage.setTitle("Crear "+seleccion);
			newStage.showAndWait();
			try {
				if (seleccion.equals("Equipo")) {
					EquipoDao dao = new EquipoDao();
					lista = dao.cargarEquipo();
				}else if(seleccion.equals("Deporte")){
					DeporteDao dao = new DeporteDao();
					lista = dao.cargarDeportes();
				}else if(seleccion.equals("Evento")){
					EventoDao dao = new EventoDao();
					lista = dao.cargarEvento();
				}else if(seleccion.equals("Olimpiada")){
					OlimpiadaDao dao = new OlimpiadaDao();
					lista = dao.cargarOlimpiadas();
				}else if(seleccion.equals("Participacion")){
					ParticipacionDao dao = new ParticipacionDao();
					lista = dao.cargarParticipacion();
				}else if(seleccion.equals("Deportista")){
					DeportistaDao dao = new DeportistaDao();
					lista = dao.cargarDeportista();
				}
				tabla.setItems(lista);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
	// Event Listener on TableView[#tabla].onMouseClicked
	@FXML
	public void clickTabla(MouseEvent event) {
//		if(event.getClickCount() == 2){
//			try{
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Opciones.fxml"));
//				Parent root = loader.load();
//				Scene newScene = new Scene(root);
//				Stage newStage = new Stage();
//				OpcionesController control = loader.getController();
//				newStage.initModality(Modality.APPLICATION_MODAL);
//				newStage.initOwner(this.btnCrear.getScene().getWindow());
//				newStage.setResizable(false);
//				newStage.setMaximized(false);
//				newStage.setScene(newScene);
//				
//				newStage.setTitle("Opciones "+seleccion);
//				
//				if (seleccion.equals("Equipo")) {
//					Equipo e = (Equipo) tabla.getSelectionModel().getSelectedItem();
//					control.cargarDatos(seleccion,e,e.toString());
//				}else if(seleccion.equals("Deporte")){
//					Deporte e = (Deporte) tabla.getSelectionModel().getSelectedItem();
//					control.cargarDatos(seleccion,e,e.toString());
//				}else if(seleccion.equals("Evento")){
//					Evento e = (Evento) tabla.getSelectionModel().getSelectedItem();
//					control.cargarDatos(seleccion,e,e.toString());
//				}else if(seleccion.equals("Olimpiada")){
//					Olimpiada e = (Olimpiada) tabla.getSelectionModel().getSelectedItem();
//					control.cargarDatos(seleccion,e,e.toString());
//				}else if(seleccion.equals("Participacion")){
//					Participacion e = (Participacion) tabla.getSelectionModel().getSelectedItem();
//					control.cargarDatos(seleccion,e,e.toString());
//				}else if(seleccion.equals("Deportista")){
//					Deportista e = (Deportista) tabla.getSelectionModel().getSelectedItem();
//					control.cargarDatos(seleccion,e,e.toString());
//				}
//				
//				newStage.showAndWait();
//				try {
//					if (seleccion.equals("Equipo")) {
//						EquipoDao dao = new EquipoDao();
//						lista = dao.cargarEquipo();
//					}else if(seleccion.equals("Deporte")){
//						DeporteDao dao = new DeporteDao();
//						lista = dao.cargarDeportes();
//					}else if(seleccion.equals("Evento")){
//						EventoDao dao = new EventoDao();
//						lista = dao.cargarEvento();
//					}else if(seleccion.equals("Olimpiada")){
//						OlimpiadaDao dao = new OlimpiadaDao();
//						lista = dao.cargarOlimpiadas();
//					}else if(seleccion.equals("Participacion")){
//						ParticipacionDao dao = new ParticipacionDao();
//						lista = dao.cargarParticipacion();
//					}else if(seleccion.equals("Deportista")){
//						DeportistaDao dao = new DeportistaDao();
//						lista = dao.cargarDeportista();
//					}
//					
//					tabla.setItems(lista);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//		    } catch (IOException e) {
//		    	e.printStackTrace();
//		        Alert alert = new Alert(Alert.AlertType.ERROR);
//		        alert.setHeaderText(null);
//		        alert.setTitle("Error");
//		        alert.setContentText(e.getMessage());
//		        alert.showAndWait();
//		    }
//        }
		
		ContextMenu contextMenu = new ContextMenu();
		  
        // create menuitems
        MenuItem miEditar = new MenuItem("Editar");
        MenuItem miEliminar = new MenuItem("Eliminar");
  
        // add menu items to menu
        contextMenu.getItems().add(miEditar);
        contextMenu.getItems().add(miEliminar);

        tabla.setContextMenu(contextMenu);

        miEditar.setOnAction(e -> editar());
        miEliminar.setOnAction(e -> eliminar());
	}
	
	private void editar() {
		try {
			seleccionado();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ven" + seleccion + ".fxml"));
			Parent root = loader.load();
			Scene newScene = new Scene(root);
			Stage newStage = new Stage();

			newStage.initModality(Modality.APPLICATION_MODAL);
			newStage.initOwner(this.btnCrear.getScene().getWindow());
			newStage.setResizable(false);
			newStage.setMaximized(false);
			newStage.setScene(newScene);
			newScene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

			newStage.setTitle("Crear " + seleccion);

			if (seleccion.equals("Equipo")) {
				VenEquipoController control = loader.getController();
				 control.cargarDatos(eq);
			} else if (seleccion.equals("Deporte")) {
				VenDeporteController control = loader.getController();
				control.cargarDatos(d);
			}else if(seleccion.equals("Evento")){
				VenEventoController control = loader.getController();
				control.cargarDatos(ev);
			}else if(seleccion.equals("Olimpiada")){
				VenOlimpiadaController control = loader.getController();
				control.cargarDatos(o);
			}else if(seleccion.equals("Participacion")){
				VenParticipacionController control = loader.getController();
				control.cargarDatos(p);
			}else if(seleccion.equals("Deportista")){
				VenDeportistaController control = loader.getController();
				control.cargarDatos(de);
			}

			newStage.showAndWait();
			recargarTabla();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void eliminar() {
		try {
			seleccionado();
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setTitle("Eliminar Linea");
			alert.setContentText("Seguro que quieres eliminar:\n" + texto);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				System.out.println("ssss");
				if (seleccion.equals("Equipo")) {
					EquipoDao dao = new EquipoDao();
					dao.borrarEquipo(eq);
				} else if (seleccion.equals("Deporte")) {
					DeporteDao dao = new DeporteDao();
					dao.borrarDeporte(d);
				}else if(seleccion.equals("Evento")){
					EventoDao dao = new EventoDao();
					dao.borrarEvento(ev);
				}else if(seleccion.equals("Olimpiada")){
					OlimpiadaDao dao = new OlimpiadaDao();
					dao.borrarOlimpiada(o);
				}else if(seleccion.equals("Participacion")){
					ParticipacionDao dao = new ParticipacionDao();
					System.out.println("ssss");
					dao.borrarParticipacion(p);
				}else if(seleccion.equals("Deportista")){
					DeportistaDao dao = new DeportistaDao();
					dao.borrarDeportista(de);
				}
				
				alert.close();
				Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
				alert2.setHeaderText(null);
				alert2.setTitle("Eliminar Linea");
				alert2.setContentText("Linea eliminada");
				alert2.showAndWait();
				recargarTabla();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Error al eliminar");
			alert.showAndWait();
		}
	}

	public void seleccionado() {
		if (seleccion.equals("Equipo")) {
			eq = (Equipo) tabla.getSelectionModel().getSelectedItem();
			texto = eq.toString() + "\n\nTambien se eliminaran todas las participaciones en las que ha competido este equipo";
		}else if(seleccion.equals("Deporte")){
			d = (Deporte) tabla.getSelectionModel().getSelectedItem();
			texto = d.toString() + "\n\nTambien se eliminaran todas las participaciones y todos los eventos en los que se ha practicado este deporte";
		}else if(seleccion.equals("Evento")){
			ev = (Evento) tabla.getSelectionModel().getSelectedItem();
			texto = ev.toString() + "\n\nTambien se eliminaran todas las participaciones de este evento";
		}else if(seleccion.equals("Olimpiada")){
			o = (Olimpiada) tabla.getSelectionModel().getSelectedItem();
			texto = o.toString() + "\n\nTambien se eliminaran todas las participaciones y eventos de esta olimpada";
		}else if(seleccion.equals("Participacion")){
			p = (Participacion) tabla.getSelectionModel().getSelectedItem();
			texto = p.toString();
		}else if(seleccion.equals("Deportista")){
			de = (Deportista) tabla.getSelectionModel().getSelectedItem();
			texto = de.toString() + "\n\nTambien se eliminaran todas las participaciones en las que ha competido este deportista";
		}
	}
	
	public void recargarTabla() {
		try {
			if (seleccion.equals("Equipo")) {
				EquipoDao dao = new EquipoDao();
				lista = dao.cargarEquipo();
			}else if(seleccion.equals("Deporte")){
				DeporteDao dao = new DeporteDao();
				lista = dao.cargarDeportes();
			}else if(seleccion.equals("Evento")){
				EventoDao dao = new EventoDao();
				lista = dao.cargarEvento();
			}else if(seleccion.equals("Olimpiada")){
				OlimpiadaDao dao = new OlimpiadaDao();
				lista = dao.cargarOlimpiadas();
			}else if(seleccion.equals("Participacion")){
				ParticipacionDao dao = new ParticipacionDao();
				lista = dao.cargarParticipacion();
			}else if(seleccion.equals("Deportista")){
				DeportistaDao dao = new DeportistaDao();
				lista = dao.cargarDeportista();
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
		if (seleccion.equals("Equipo")) {
			tablaEquipo();
		}else if(seleccion.equals("Deporte")){
			tablaDeporte();
		}else if(seleccion.equals("Evento")){
			tablaEvento();
		}else if(seleccion.equals("Olimpiada")){
			tablaOlimpiada();
		}else if(seleccion.equals("Participacion")){
			tablaParticipacion();
		}else if(seleccion.equals("Deportista")){
			tablaDeportista();
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void tablaDeporte() {
		try {
			DeporteDao dao = new DeporteDao();
			lista = dao.cargarDeportes();
			
			TableColumn<Deporte, String> colNombre = new TableColumn<>("NOMBRE");
			colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			colNombre.prefWidthProperty().bind(tabla.widthProperty().multiply(1));
			tabla.getColumns().addAll(colNombre);
			tabla.setItems(lista);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void tablaEquipo() {
		try {
			EquipoDao dao = new EquipoDao();
			lista = dao.cargarEquipo();
			
			TableColumn<Equipo, String> colNombre = new TableColumn<>("NOMBRE");
			colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			colNombre.prefWidthProperty().bind(tabla.widthProperty().multiply(0.7));
			
			TableColumn<Equipo, Integer> colIniciales = new TableColumn<>("INICIALES");
			colIniciales.setCellValueFactory(new PropertyValueFactory<>("iniciales"));
			colIniciales.prefWidthProperty().bind(tabla.widthProperty().multiply(0.3));
			
			tabla.getColumns().addAll(colNombre,colIniciales);
			tabla.setItems(lista);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void tablaEvento() {
		try {
			EventoDao dao =new EventoDao();
			lista = dao.cargarEvento();
			
			TableColumn<Equipo, String> colNombre = new TableColumn<>("NOMBRE");
			colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			colNombre.prefWidthProperty().bind(tabla.widthProperty().multiply(0.4));
			
			TableColumn<Equipo, Integer> colIdOlimpiada = new TableColumn<>("OLIMPIADA");
			colIdOlimpiada.setCellValueFactory(new PropertyValueFactory<>("NomOlimpiada"));
			colIdOlimpiada.prefWidthProperty().bind(tabla.widthProperty().multiply(0.35));
			
			TableColumn<Equipo, Integer> colIdDeporte = new TableColumn<>("DEPORTE");
			colIdDeporte.setCellValueFactory(new PropertyValueFactory<>("NomDeporte"));
			colIdDeporte.prefWidthProperty().bind(tabla.widthProperty().multiply(0.25));
			
			tabla.getColumns().addAll(colNombre,colIdOlimpiada,colIdDeporte);
			tabla.setItems(lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tablaOlimpiada() {
		try {
			OlimpiadaDao dao = new OlimpiadaDao();
			lista = dao.cargarOlimpiadas();
			
			TableColumn<Olimpiada, String> colNombre = new TableColumn<>("NOMBRE");
			colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			colNombre.prefWidthProperty().bind(tabla.widthProperty().multiply(0.35));
			
			TableColumn<Olimpiada, Integer> colAnio = new TableColumn<>("AÑO");
			colAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
			colAnio.prefWidthProperty().bind(tabla.widthProperty().multiply(0.1));
			
			TableColumn<Olimpiada, String> colTemporada = new TableColumn<>("TEMPORADA");
			colTemporada.setCellValueFactory(new PropertyValueFactory<>("temporada"));
			colTemporada.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Olimpiada, String> colCiudad = new TableColumn<>("CIUDAD");
			colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
			colCiudad.prefWidthProperty().bind(tabla.widthProperty().multiply(0.35));
			
			tabla.getColumns().addAll(colNombre,colAnio,colTemporada,colCiudad);
			tabla.setItems(lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tablaDeportista() {
		try {
			DeportistaDao dao = new DeportistaDao();
			lista = dao.cargarDeportista();
			
			TableColumn<Deportista, String> colNombre = new TableColumn<>("NOMBRE");
			colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			colNombre.prefWidthProperty().bind(tabla.widthProperty().multiply(0.4));
			
			TableColumn<Deportista, String> colSexo = new TableColumn<>("SEXO");
			colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
			colSexo.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Deportista, Integer> colPeso = new TableColumn<>("PESO");
			colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
			colPeso.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Deportista, Integer> colAltura = new TableColumn<>("ALTURA");
			colAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));
			colAltura.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
//			TableColumn<Deportistas, String> colFoto = new TableColumn<>("FOTO");
//			colFoto.setCellValueFactory(new PropertyValueFactory<>("FOTO"));
//			colFoto.prefWidthProperty().bind(tabla.widthProperty().multiply(0.3));
			
			tabla.getColumns().addAll(colNombre,colSexo,colPeso,colAltura);
			tabla.setItems(lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tablaParticipacion() {
		try {
			ParticipacionDao dao = new ParticipacionDao();
			lista = dao.cargarParticipacion();
			System.out.println(lista.size());
			TableColumn<Participacion, String> colIdDeportista = new TableColumn<>("DEPORTISTA");
			colIdDeportista.setCellValueFactory(new PropertyValueFactory<>("NomDeportista"));
			colIdDeportista.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Participacion, String> colIdEvento = new TableColumn<>("EVENTO");
			colIdEvento.setCellValueFactory(new PropertyValueFactory<>("NomEvento"));
			colIdEvento.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Participacion, String> colIdEquipo = new TableColumn<>("EQUIPO");
			colIdEquipo.setCellValueFactory(new PropertyValueFactory<>("NomEquipo"));
			colIdEquipo.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Participacion, Integer> colEdad = new TableColumn<>("EDAD");
			colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
			colEdad.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			TableColumn<Participacion, String> colMedalla = new TableColumn<>("MEDALLA");
			colMedalla.setCellValueFactory(new PropertyValueFactory<>("medalla"));
			colMedalla.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			
			tabla.getColumns().addAll(colIdDeportista,colIdEvento,colIdEquipo,colEdad,colMedalla);
			
			tabla.setItems(lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
