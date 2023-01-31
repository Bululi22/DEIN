package Ejercicio_D;

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
//import javafx.scene.layout.VBox;


	public class Main extends Application {
		private Label lblNombre, lblApellido, lblEdad;
		private TextField tfNombre, tfApellido, tfEdad;
		private Button btnAgregar, btnGuardar, btnCancelar;
		//private Button btnModifi, btnEliminar;
		private GridPane grid, gNew;
		//private HBox gBotones;
		private HBox gBotonesNew;
		//private VBox izquierda;
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
			
			btnAgregar =new Button("Agregar Persona");
			btnAgregar.setOnAction(e -> this.ventanaAgregar(stage));
			GridPane.setHalignment(btnAgregar, HPos.CENTER);;
			
			
			
			
			
			
			
			/*btnModifi = new Button("Modificar");
			btnModifi.setOnAction(e -> modificar());
			btnEliminar = new Button("Eliminar");
			btnEliminar.setOnAction(e -> borrar());
			
			gBotones = new HBox(50);
			gBotones.getChildren().addAll(btnModifi,btnEliminar);
			gBotones.setAlignment(Pos.CENTER);*/
			

			
			grid = new GridPane();
			grid.add(tabla, 0, 0);
			grid.add(btnAgregar, 0,1);
			
			
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
	        newStage.setTitle("Nuevo usuario");
	        newStage.initOwner(stage);
	        newStage.initModality(Modality.APPLICATION_MODAL);
	        newStage.show();
		}


		@SuppressWarnings("unused")
		private void modificar() {
			try {
				if (lista.contains(p)) {
					lista.remove(p);
					p = new Persona(tfNombre.getText(), tfApellido.getText(), tfEdad.getText());
					lista.add(p);
					p = new Persona();
					limpieza();
				}
			} catch (Exception e) {
				System.out.println("mal");
			}
		}


		@SuppressWarnings("unused")
		private void borrar() {
			try {
				if (lista.contains(p)) {
					lista.remove(p);
					p = new Persona();
					limpieza();
				}
			} catch (Exception e) {
				System.out.println("mal");
			}
		}

		private void tablaSeleccionada(Stage stage) {
			p = tabla.getSelectionModel().getSelectedItem();
			System.out.println(p);
			tfNombre.setText(p.getNombre());
			tfApellido.setText(p.getApellido());
			tfEdad.setText(p.getEdad());
		}

		private void limpieza() {
			tfNombre.setText("");
			tfApellido.setText("");
			tfEdad.setText("");
		}

		private void agregarPersona(Stage stage, Stage newStage) {
			try {
				String msg="";
				
				if (tfNombre.getText().equals("")) {
					msg+="El campo NOMBRE es obligatorio\n";
				}
				if(tfApellido.getText().equals("")){
					msg+="El campo APELLIDO es obligatorio\n";
				}
				try {
					Integer.parseInt(tfEdad.getText());
				} catch (Exception e) {
					msg+="El campo EDAD es obligatorio";
				}
				if(msg.equals("")) {
					Persona p = new Persona(tfNombre.getText(), tfApellido.getText(), tfEdad.getText());
					if (lista.contains(p)) {
						mostrarAlertError(stage, "Esta persona ya existe");
					}else {
						lista.add(p);
						mostrarAlertInfo(stage);
						limpieza();
						newStage.close();
					}
					
				}else {
					mostrarAlertError(stage,msg);
				}
				
			} catch (Exception e) {
				System.out.println("Algo salio mal");
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
		private void mostrarAlertInfo(Window win) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.initOwner(win);
			alert.setHeaderText(null);
			alert.setTitle("Info");
			alert.setContentText("Persona añadida correctamente");
			alert.showAndWait();
		}



		public static void main(String[] args) {
			launch(args);
		}
	}
