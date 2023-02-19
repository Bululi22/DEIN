//package controller;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import model.Deporte;
//import dao.DeporteDao;
//import javafx.event.ActionEvent;
//
//public class VenDeporteController {
//	@FXML
//	private Label lblTexto;
//	@FXML
//	private TextField tfNombre;
//	@FXML
//	private Button btnEjecuta;
//	
//	private Boolean editar = false;
//
//	@SuppressWarnings("unused")
//	private Deporte d;
//	private DeporteDao dao;
//	private String nombre;
//	
//	// Event Listener on Button[#btnEjecuta].onAction
//	@FXML
//	public void ejecuta(ActionEvent event) {
//		String msg = "";
//		
//		try {
//			msg = validacion();
//
//			if(msg.equals("")) {
//				
//				if(!editar) {
//					//Crear
//					Deporte d = new Deporte(null, nombre);
//					msg = dao.crearDeporte(d);
//				}else {
//					//Editar
//					Deporte de = new Deporte(d.getId_deporte(), nombre);
//					msg = dao.editarDeporte(de);
//				}
//			}
//			if(msg.equals("")) {
//				Alert alert = new Alert(Alert.AlertType.INFORMATION);
//		        alert.setHeaderText(null);
//		        alert.setTitle("Hecho :)");
//		        if(editar) {
//		        	alert.setContentText("Editado correctamente \n");
//		        }else {
//		        	alert.setContentText("Creado correctamente \n");
//		        }
//		        alert.showAndWait();
//		        this.cerrar(event);
//			}else {
//				Alert alert = new Alert(Alert.AlertType.ERROR);
//		        alert.setHeaderText(null);
//		        alert.setTitle("Error");
//		        alert.setContentText(msg);
//		        alert.showAndWait();
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//	        Alert alert = new Alert(Alert.AlertType.ERROR);
//	        alert.setHeaderText(null);
//	        alert.setTitle("Error");
//	        alert.setContentText("Se a producido un error, vuelva más tarde");
//	        System.out.println(e.getMessage());
//	        alert.showAndWait();
//	    }
//	}
//	// Event Listener on Button.onAction
//	@FXML
//	public void cerrar(ActionEvent event) {
//		Stage stage = (Stage) btnEjecuta.getScene().getWindow();
//		stage.close();
//	}
//	
//	public void cargarDatos(Deporte d) {
//		this.d=d;
//		editar = true;
//		lblTexto.setText("EDITAR");
//		tfNombre.setText(d.getNombre());
//	}
//	
//	public String validacion() {
//		String msg = "";
//		dao = new DeporteDao();
//		
//		nombre = tfNombre.getText().toString();
//		
//		
//		if (nombre.equals("")) {
//			msg+="Campo 'nombre' no puede estar vacio \n";
//		}
//		
//		
//		try {
//			int resp = dao.verificadorRepetidos(nombre);
//			if(editar) {
//				if (d.getNombre().equals(nombre)) {
//					if (resp>1) {
//						System.out.println(resp);
//						msg+="Este deporte ya existe";
//					}
//				}else {
//					if (resp>0) {
//						msg+="Este deporte ya existe";
//					}
//				}
//				if (resp<0) {
//					msg ="Error al verificar los datos";
//				}
//			}else {
//				if (resp>0) {
//					msg+="Este deporte ya existe";
//				}
//				if (resp<0) {
//					msg ="Error al verificar los datos";
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			msg = "Error al verificar los datos";
//		}
//		
//		return msg;
//	}
//	
//}
