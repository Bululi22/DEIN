package Ejercicio_E;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Persona;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;


	public class Main extends Application {
		private Label lblNombre, lblApellido, lblEdad;
		private TextField tfNombre, tfApellido, tfEdad;
		private Button btnAgregar, btnGuardar, btnCancelar;
		private Button btnModifi, btnEliminar;
		private GridPane grid, gNew;
		private HBox gBotones;
		private HBox gBotonesNew;
		private TableView<Persona> tabla;
		private ObservableList<Persona> lista;
		private Persona p;
		
		@SuppressWarnings("unchecked")
		public void start(Stage stage) {
			
			lista = FXCollections.observableArrayList(); 
			tabla = new TableView<>(lista);
			TableColumn<Persona, String> colNombre = new TableColumn<>("NOMBRE");
			colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			colNombre.prefWidthProperty().bind(tabla.widthProperty().multiply(0.4));
			TableColumn<Persona, String> colApellido = new TableColumn<>("APELLIDO");
			colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
			colApellido.prefWidthProperty().bind(tabla.widthProperty().multiply(0.4));
			TableColumn<Persona, String> colEdad = new TableColumn<>("EDAD");
			colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
			colEdad.prefWidthProperty().bind(tabla.widthProperty().multiply(0.2));
			tabla.getColumns().addAll(colNombre,colApellido,colEdad);
			tabla.setOnMouseClicked(e -> tablaSeleccionada(stage));
			
			
			gBotones = new HBox(50);
				btnAgregar =new Button("Agregar Persona");
				btnAgregar.setOnAction(e -> this.ventanaAgregar(stage));
				btnModifi = new Button("Modificar Persona");
				btnModifi.setOnAction(e -> ventanaModificar(stage));
				btnEliminar = new Button("Eliminar Persona");
				btnEliminar.setOnAction(e -> borrar(stage));
			gBotones.getChildren().addAll(btnAgregar,btnModifi,btnEliminar);
			gBotones.setAlignment(Pos.CENTER);
			

			
			grid = new GridPane();
			grid.add(tabla, 0, 0);
			grid.add(gBotones, 0,1);
			
			
			ColumnConstraints c1=new ColumnConstraints();
			RowConstraints r1=new RowConstraints();
			c1.setHgrow(Priority.ALWAYS);
			r1.setVgrow(Priority.ALWAYS);
			grid.getRowConstraints().add(r1);
			grid.getColumnConstraints().add(c1);
			grid.setStyle("-fx-padding: 10;");
			grid.setHgap(10);
			grid.setVgap(10);
			
			
			Scene scene = new Scene(grid,600,600);
			String img=getClass().getResource("/imagenes/agenda.png").toString();
			stage.getIcons().add(new Image(img));
			stage.setScene(scene);
			stage.setTitle("PERSONAS");
			stage.show();
		}
		

		private void ventanaAgregar(Stage stage) {
			gNew = new GridPane();
			Scene newScene = new Scene(gNew, 400, 150);
	        Stage newStage = new Stage();
	        
	        
			lblNombre = new Label("Nombre:");
			GridPane.setHalignment(lblNombre, HPos.RIGHT);
			tfNombre = new TextField();
			tfNombre.setPrefWidth(350);
			lblApellido = new Label("Apellidos:");
			GridPane.setHalignment(lblApellido, HPos.RIGHT);
			tfApellido = new TextField();
			tfApellido.setPrefWidth(350);
			lblEdad = new Label("Edad:");
			GridPane.setHalignment(lblEdad, HPos.RIGHT);
			tfEdad = new TextField();
			tfEdad.setPrefWidth(70);
			btnGuardar = new Button("Guardar");
			btnGuardar.setOnAction(e -> this.agregarPersona(stage,newStage));
			btnCancelar = new Button("Cancelar");
			btnCancelar.setOnAction(e -> newStage.close());
			
			gBotonesNew = new HBox(35);
			gBotonesNew.getChildren().addAll(btnGuardar, btnCancelar);
			gBotonesNew.setAlignment(Pos.CENTER);
			
			
			
			
			gNew.setStyle("-fx-padding: 20;");
			gNew.add(lblNombre, 0, 0);
			gNew.add(tfNombre, 1, 0, 2, 1);
			gNew.add(lblApellido, 0, 1);
			gNew.add(tfApellido, 1, 1, 2, 1);
			gNew.add(lblEdad, 0, 2);
			gNew.add(tfEdad, 1, 2);
			gNew.add(gBotonesNew, 0, 3, 4, 1);
			gNew.setHgap(10);
			gNew.setVgap(5);
			

			
	        
	        newStage.setScene(newScene);
	        newStage.setResizable(false);
	        newStage.setTitle("Nuevo usuario");
	        newStage.initOwner(stage);
	        newStage.initModality(Modality.APPLICATION_MODAL);
	        newStage.show();
		}

		private void ventanaModificar(Stage stage) {
			if (lista.contains(p)) {
				gNew = new GridPane();
				Scene newScene = new Scene(gNew, 400, 150);
		        Stage newStage = new Stage();
		        System.out.println("1");
		        
				lblNombre = new Label("Nombre");
				GridPane.setHalignment(lblNombre, HPos.RIGHT);
				tfNombre = new TextField();
				tfNombre.setPrefWidth(350);
				lblApellido = new Label("Apellidos");
				GridPane.setHalignment(lblApellido, HPos.RIGHT);
				tfApellido = new TextField();
				tfApellido.setPrefWidth(350);
				lblEdad = new Label("Edad");
				GridPane.setHalignment(lblEdad, HPos.RIGHT);
				tfEdad = new TextField();
				tfEdad.setPrefWidth(70);
				btnGuardar = new Button("Guardar");
				btnGuardar.setOnAction(e -> this.modificarPersona(stage,newStage));
				btnCancelar = new Button("Cancelar");
				btnCancelar.setOnAction(e -> newStage.close());
				
				gBotonesNew = new HBox(35);
				gBotonesNew.getChildren().addAll(btnGuardar, btnCancelar);
				gBotonesNew.setAlignment(Pos.CENTER);
				rellenar();
				
				
				
				gNew.setStyle("-fx-padding: 20;");
				gNew.add(lblNombre, 0, 0);
				gNew.add(tfNombre, 1, 0, 2, 1);
				gNew.add(lblApellido, 0, 1);
				gNew.add(tfApellido, 1, 1, 2, 1);
				gNew.add(lblEdad, 0, 2);
				gNew.add(tfEdad, 1, 2);
				gNew.add(gBotonesNew, 0, 3, 4, 1);
				gNew.setHgap(10);
				gNew.setVgap(5);
				
	
				
		        
		        newStage.setScene(newScene);
		        newStage.setResizable(false);
		        newStage.setTitle("Editar Persona");
		        newStage.initOwner(stage);
		        newStage.initModality(Modality.APPLICATION_MODAL);
		        newStage.show();
			}else {
				mostrarAlertError(stage, "Selecciona una persona de la lista");
			}
		}
		
		private void modificarPersona(Stage stage, Stage newStage) {
			Persona per = new Persona();
			per=p;
			String msg=verificar();
			if(msg.equals("")) {
				Persona p = new Persona(tfNombre.getText(), tfApellido.getText(), tfEdad.getText());
				lista.add(p);
				lista.remove(per);
				mostrarAlertInfo(stage,"Persona modificada correctamente");
				//limpieza();	
				newStage.close();
			}else {
				mostrarAlertError(stage,msg);
			}
		}
		
		private void borrar(Stage stage) {
			try {
				if (lista.contains(p)) {
					lista.remove(p);
					p = new Persona();
					mostrarAlertInfo(stage, "Persona eliminada correctamente");
					//limpieza();
				}else {
					mostrarAlertError(stage, "Selecciona una persona de la lista");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		private void tablaSeleccionada(Stage stage) {
			p = tabla.getSelectionModel().getSelectedItem();
			System.out.println("2");
			System.out.println(p);
			rellenar();
		}

		private void rellenar() {
			tfNombre.setText(p.getNombre());
			tfApellido.setText(p.getApellido());
			tfEdad.setText(p.getEdad());
		}
		
		/*private void limpieza() {
			tfNombre.setText("");
			tfApellido.setText("");
			tfEdad.setText("");
		}*/

		private String verificar() {
			String msg="";
			
			if (tfNombre.getText().equals("")) {
				msg+="El campo NOMBRE es obligatorio\n";
			}
			if(tfApellido.getText().equals("")){
				msg+="El campo APELLIDO es obligatorio\n";
			}
			try {
				if (tfEdad.getText().equals("")) {
					msg+="El campo EDAD es obligatorio";
				}else {
					Integer.parseInt(tfEdad.getText());
				}
			} catch (Exception e) {
				msg+="El campo EDAD es numerico";
			}
			if(msg.equals("")) {
				Persona p = new Persona(tfNombre.getText(), tfApellido.getText(), tfEdad.getText());
				if (lista.contains(p)) {
					msg+="Esta persona ya existe";
				}
			}
			return msg;
		}
		
		private void agregarPersona(Stage stage, Stage newStage) {
			try {
				String msg=verificar();
				if(msg.equals("")) {
					Persona p = new Persona(tfNombre.getText(), tfApellido.getText(), tfEdad.getText());
					lista.add(p);
					mostrarAlertInfo(stage,"Persona añadida correctamente");
					//limpieza();	
					newStage.close();
				}else {
					mostrarAlertError(stage,msg);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		private void mostrarAlertError(Window win, String msg) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.initOwner(win);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText(msg);
			alert.showAndWait();
		}
		
		private void mostrarAlertInfo(Window win, String msg) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.initOwner(win);
			alert.setHeaderText(null);
			alert.setTitle("Info");
			alert.setContentText(msg);
			alert.showAndWait();
		}



		public static void main(String[] args) {
			launch(args);
		}
	}
