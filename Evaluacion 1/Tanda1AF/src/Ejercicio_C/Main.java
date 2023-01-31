package Ejercicio_C;

import javafx.application.Application;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	//import javafx.geometry.Insets;
	import javafx.geometry.Pos;
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
import javafx.scene.layout.VBox;


	public class Main extends Application {
		private Label lblNombre, lblApellido, lblEdad;
		private TextField tfNombre, tfApellido, tfEdad;
		private Button btnAgregar, btnModifi, btnEliminar;
		private GridPane grid;
		private HBox gBotones;
		private VBox izquierda;
		private TableView<Persona> tabla;
		private ObservableList<Persona> lista;
		private Persona p;
		
		@SuppressWarnings("unchecked")
		public void start(Stage stage) {
			lblNombre = new Label("Nombre");
			tfNombre = new TextField();
			lblApellido = new Label("Apellidos");
			tfApellido = new TextField();
			lblEdad = new Label("Edad");
			tfEdad = new TextField();
			btnAgregar =new Button("Agregar Persona");
			btnAgregar.setOnAction(e -> this.agregarPersona(stage));
			
			izquierda = new VBox(10);
			izquierda.getChildren().addAll(lblNombre,tfNombre,lblApellido,tfApellido,lblEdad,tfEdad,btnAgregar);
			izquierda.setStyle("-fx-padding: 10;");
			
			lista = FXCollections.observableArrayList(); 
			tabla = new TableView<>(lista);
			TableColumn<Persona, String> colNombre = new TableColumn<>("NOMBRE");
			colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			colNombre.prefWidthProperty().bind(tabla.widthProperty().multiply(0.333333333));
			TableColumn<Persona, String> colApellido = new TableColumn<>("APELLIDO");
			colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
			colApellido.prefWidthProperty().bind(tabla.widthProperty().multiply(0.333333333));
			TableColumn<Persona, String> colEdad = new TableColumn<>("EDAD");
			colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
			colEdad.prefWidthProperty().bind(tabla.widthProperty().multiply(0.333333333));
			tabla.getColumns().addAll(colNombre,colApellido,colEdad);
			tabla.setOnMouseClicked(e -> tablaSeleccionada(stage));
			
			
			
			btnModifi = new Button("Modificar");
			btnModifi.setOnAction(e -> modificar());
			btnEliminar = new Button("Eliminar");
			btnEliminar.setOnAction(e -> borrar());
			
			gBotones = new HBox(50);
			gBotones.getChildren().addAll(btnModifi,btnEliminar);
			gBotones.setAlignment(Pos.CENTER);
			

			
			grid = new GridPane();
			grid.add(izquierda, 0, 0,1,2);
			grid.add(tabla, 1, 0,1,1);
			grid.add(gBotones, 1,1,1,1);
			izquierda.setAlignment(Pos.CENTER_LEFT);
			
			
			ColumnConstraints c1=new ColumnConstraints();
			ColumnConstraints c2=new ColumnConstraints();
			RowConstraints r1=new RowConstraints();
			RowConstraints r2=new RowConstraints();
			c2.setHgrow(Priority.ALWAYS);
			r1.setVgrow(Priority.ALWAYS);
			grid.getRowConstraints().add(r1);
			grid.getRowConstraints().add(r2);
			grid.getColumnConstraints().add(c1);
			grid.getColumnConstraints().add(c2);
			grid.setStyle("-fx-padding: 10;");
			grid.setHgap(10);
			grid.setVgap(10);
			
			
			Scene scene = new Scene(grid);
			String img=getClass().getResource("/imagenes/agenda.png").toString();
			stage.getIcons().add(new Image(img));
			stage.setScene(scene);
			stage.setTitle("PERSONAS");
			stage.show();
		}
		

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

		private String verificar() {
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
					msg+="Esta persona ya existe";
				}
			}
			return msg;
		}
		
		private void agregarPersona(Stage stage) {
			try {
				String msg=verificar();
				if(msg.equals("")) {
					Persona p = new Persona(tfNombre.getText(), tfApellido.getText(), tfEdad.getText());
					lista.add(p);
					mostrarAlertInfo(stage);
					limpieza();				
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