package Ejercicio_B;


	import javafx.application.Application;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	import javafx.geometry.Insets;
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
	import javafx.scene.layout.GridPane;
	import javafx.scene.layout.Priority;
	import javafx.scene.layout.VBox;


	public class Main extends Application {
		private Label lblNombre, lblApellido, lblEdad;
		private TextField tfNombre, tfApellido, tfEdad;
		private Button btnAgregar;
		private GridPane grid;
		private VBox izquierda;
		private TableView<Persona> tabla;
		private ObservableList<Persona> lista;
		
		@SuppressWarnings("unchecked")
		public void start(Stage stage) {
			lblNombre = new Label("Nombre");
			tfNombre = new TextField();
			lblApellido = new Label("Apellidos");
			tfApellido = new TextField();
			lblEdad = new Label("Edad");
			tfEdad = new TextField();
			btnAgregar =new Button("Agregar Persona");
			btnAgregar.setOnAction(e -> this.elBoton(stage));
			
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
			Insets margin = new Insets(10, 10, 10, 10); 
			GridPane.setMargin(tabla, margin);

			tabla.setStyle("-fx-margin: 1022;");
			
			grid = new GridPane();
			grid.add(izquierda, 0, 0);
			grid.add(tabla, 1, 0);
			izquierda.setAlignment(Pos.CENTER_LEFT);
			

			GridPane.setHgrow(tabla,Priority.ALWAYS);
			GridPane.setVgrow(tabla, Priority.ALWAYS);

			
			
			Scene scene = new Scene(grid);
			
			String img=getClass().getResource("/imagenes/agenda.png").toString();
			stage.getIcons().add(new Image(img));
			

			
			stage.setScene(scene);
			stage.setTitle("PERSONAS");
			stage.show();
		}
		

		private Object elBoton(Stage stage) {
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
					}
					
				}else {
					mostrarAlertError(stage,msg);
				}
				
			} catch (Exception e) {
				System.out.println("Algo salio mal");
			}
			return null;
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