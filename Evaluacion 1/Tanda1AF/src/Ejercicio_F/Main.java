package Ejercicio_F;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;


	public class Main extends Application {
		private Label lblNombre, lblApellido, lblEdad, lblFiltrar;
		private TextField tfNombre, tfApellido, tfEdad, tfFiltrar;
		private Button btnAgregar, btnGuardar, btnCancelar;
		private Button btnModifi, btnEliminar, btnImportar, btnExportar;
		private GridPane grid, gNew;
		private HBox gBotones;
		private HBox gBotonesNew;
		private TableView<Persona> tabla;
		private ObservableList<Persona> lista, listaFiltrada;
		private Persona p;
		private FlowPane flow;
		
		@SuppressWarnings("unchecked")
		public void start(Stage stage) {
			
			flow = new FlowPane();
				lblFiltrar = new Label("Filtrar por nombre: ");
				tfFiltrar = new TextField();
				btnImportar = new Button("Importar");
				btnExportar = new Button("Exportar");
				tfFiltrar.setOnKeyTyped(e->filtrar(e));
				btnImportar.setOnAction(e->importar(stage));
				btnExportar.setOnAction(e->exportar(stage));
			flow.getChildren().addAll(lblFiltrar,tfFiltrar,btnImportar,btnExportar);
			
			
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
			grid.add(flow, 0, 0);
			grid.add(tabla, 0, 1);
			grid.add(gBotones, 0, 2);
			
			
			ColumnConstraints c1=new ColumnConstraints();
			RowConstraints r1=new RowConstraints();
			RowConstraints r2=new RowConstraints();
			
			c1.setHgrow(Priority.ALWAYS);
			r2.setVgrow(Priority.ALWAYS);
			grid.getRowConstraints().addAll(r1,r2);
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
		

		private void filtrar(KeyEvent e) {
			String nom="";
			listaFiltrada=FXCollections.observableArrayList();
			nom=tfFiltrar.getText();
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

		private void importar(Stage stage) {
			Persona p;
			String[] partes;
			FileChooser fc=new FileChooser();
			fc.setTitle("Elige el CSV");
			fc.getExtensionFilters().add(new ExtensionFilter("Archivo CSV", "*.csv", "*.csv"));
			fc.setSelectedExtensionFilter(fc.getExtensionFilters().get(0));
			File archivo=fc.showOpenDialog(stage);
			try {
				BufferedReader br=new BufferedReader(new FileReader(archivo));
				int cont=0;
				String linea=br.readLine();
				while(linea!=null) {
					if(cont!=0) {
						partes=linea.split(",");
						p=new Persona(partes[0],partes[1],partes[2]);
						tabla.getItems().add(p);
					}
					cont++;
					linea=br.readLine();
				}
				br.close();
				tabla.refresh();
				mostrarAlertInfo(stage, "Importado con exito");
			} catch (IOException e) {
				mostrarAlertError(stage, "Importacion fallida");
				e.printStackTrace();
			}
		}

		private void exportar(Stage stage) {
			PrintWriter pw;
			FileChooser fc=new FileChooser();
			fc.getExtensionFilters().add(new ExtensionFilter("Archivo CSV", "*.csv", "*.csv"));
			File archivo=fc.showSaveDialog(stage);
			try {
				pw=new PrintWriter(archivo);
				String msg="Nombre,Apellido,Edad\n";
				for (int i = 0; i < tabla.getItems().size(); i++) {
					msg+=tabla.getItems().get(i).getNombre()+","+tabla.getItems().get(i).getApellido()+","+tabla.getItems().get(i).getEdad()+"\n";
				}
				pw.write(msg);
				pw.close();
				mostrarAlertInfo(stage, "Exportado con exito");
			} catch (IOException e) {
				mostrarAlertError(stage, "Exportacion fallida");
				e.printStackTrace();
			}
		}

		private void ventanaAgregar(Stage stage) {
			gNew = new GridPane();
			Scene newScene = new Scene(gNew, 400, 150);
	        Stage newStage = new Stage();
	        
	        
			lblNombre = new Label("Nombre:");
			//GridPane.setHalignment(lblNombre, HPos.RIGHT);
			tfNombre = new TextField();
			//tfNombre.setPrefWidth(350);
			lblApellido = new Label("Apellidos:");
			//GridPane.setHalignment(lblApellido, HPos.RIGHT);
			tfApellido = new TextField();
			//tfApellido.setPrefWidth(350);
			lblEdad = new Label("Edad:");
			//GridPane.setHalignment(lblEdad, HPos.RIGHT);
			tfEdad = new TextField();
			//tfEdad.setPrefWidth(70);
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
			String msg=verificar();
			if(msg.equals("")) {
				tabla.getSelectionModel().getSelectedItem().setNombre(tfNombre.getText());
				tabla.getSelectionModel().getSelectedItem().setApellido(tfApellido.getText());
				tabla.getSelectionModel().getSelectedItem().setEdad(tfEdad.getText());
				mostrarAlertInfo(stage,"Persona modificada correctamente");
				//limpieza();	
				newStage.close();
				tabla.refresh();
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
				System.out.println("e");
			}
		}

		private void tablaSeleccionada(Stage stage) {
			p = new Persona();
			p = tabla.getSelectionModel().getSelectedItem();		
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
				System.out.println("e");
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
