package Ejercicio_A;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


//
public class Main extends Application {
	private ListView<String> lista;
	private CheckBox cbPractica;
	private Slider sldCompra, sldTele, sldCine;
	private Button btnAcceptar, btnCancelar;
	private HBox botones;
	private TextField txfProfesion, txfHermanos;
	private ComboBox<String> edades;
	private RadioButton rbHombre, rbMujer, rbOtro;
	private ToggleGroup tGroup;
	private Boolean fallo;
	@Override
	public void start(Stage stage) {
		Label lblTitulo = new Label("ENCUESTA");
		lblTitulo.setFont(Font.font("Arial", FontWeight.BLACK, 20));
		Label lblProfesion = new Label("Profesión: ");
		txfProfesion = new TextField();
		Label lblHermanos = new Label("Nº Hermanos: ");
		txfHermanos = new TextField();
		Label lblEdad = new Label("Edad: ");
		edades = new ComboBox<>();
		edades.getItems().addAll("Menores de 18","Entre 18 y 30","Entre 31 y 50","Entre 51 y 70","Mayores de 70");
		edades.setValue("Menores de 18");
		Label lblSexo = new Label("Sexo: ");
		rbHombre = new RadioButton("Hombre");
		rbMujer = new RadioButton("Mujer");
		rbOtro = new RadioButton("Otro");
		tGroup = new ToggleGroup();
		tGroup.getToggles().addAll(rbHombre,rbMujer,rbOtro);
		rbMujer.setSelected(true);
		
		cbPractica = new CheckBox("¿Práctica algún deporte?");
		cbPractica.selectedProperty().addListener(this::changed);
		
		Label lblCual = new Label("¿Cúal?");
		lista = new ListView<String>();
		lista.getItems().addAll("Tenis","Fútbol","Baloncesto","Natación","Ciclismo","Otro");
		lista.setPrefSize(0,70);
		lista.setDisable(true);
		
		
		
		Label lblMarca = new Label("Marque del 1 al 10 su grado de satisfacción");
		lblMarca.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		Label lblCompras = new Label("Compras");
		sldCompra = new Slider(0,10,5);
		sldCompra.setShowTickMarks(true);
		sldCompra.setShowTickLabels(true);
		sldCompra.setMajorTickUnit(1);
		
		Label lblTele = new Label("Ver televisión");
		sldTele= new Slider(0,10,5);
		sldTele.setShowTickMarks(true);
		sldTele.setShowTickLabels(true);
		sldTele.setMajorTickUnit(1);
		
		Label lblCine = new Label("Ir al cine");
		sldCine= new Slider(0,10,5);
		sldCine.setShowTickMarks(true);
		sldCine.setShowTickLabels(true);
		sldCine.setMajorTickUnit(1);
		
		Tooltip tipCompras=new Tooltip("Indica del 1 al 10 cuánto te gusta ir de compras");
		Tooltip.install(sldCompra, tipCompras);
		
		Tooltip tipTele=new Tooltip("Indica del 1 al 10 cuánto te gusta ver la televisión");
		Tooltip.install(sldTele, tipTele);
		
		Tooltip tipCine=new Tooltip("Indica del 1 al 10 cuánto te gusta ir al cine");
		Tooltip.install(sldCine, tipCine);
		
		
		botones = new HBox(30);
		btnAcceptar = new Button("Aceptar");
		btnAcceptar.setPrefSize(200, 20);
		btnAcceptar.setOnAction(e -> ventanaInfo(stage));
		btnCancelar = new Button("Cancelar");
		btnCancelar.setPrefSize(200, 20);
		btnCancelar.setOnAction(e -> Platform.exit());
		botones.getChildren().addAll(btnAcceptar,btnCancelar);
		botones.setAlignment(Pos.CENTER);

		
		//***
		GridPane grid = new GridPane();
		grid.add(lblTitulo, 0, 0, 4, 1);
		GridPane.setHalignment(lblTitulo, HPos.CENTER);
		
		grid.add(lblProfesion, 0, 1);
		GridPane.setHalignment(lblProfesion, HPos.RIGHT);
		grid.add(txfProfesion, 1, 1, 3, 1);
		
		grid.add(lblHermanos, 0, 2);
		GridPane.setHalignment(lblHermanos, HPos.RIGHT);
		grid.add(txfHermanos, 1, 2);
		grid.add(lblEdad, 2, 2);
		grid.add(edades, 3, 2);
		
		grid.add(lblSexo, 0, 3);
		HBox box = new HBox(20);
		box.getChildren().addAll(rbHombre,rbMujer,rbOtro);
		grid.add(box, 1, 3, 3, 1);
		
		grid.add(cbPractica, 0, 4, 2, 1);
		grid.add(lblCual, 2, 4, 2, 1);
		
		grid.add(lista, 2, 5, 2, 1);
		
		grid.add(lblMarca, 0, 6,4,1);
		GridPane.setHalignment(lblMarca, HPos.CENTER);
		
		grid.add(lblCompras, 0, 7);
		GridPane.setHalignment(lblCompras, HPos.RIGHT);
		grid.add(sldCompra, 1, 7,4,1);
		
		grid.add(lblTele, 0, 8);
		GridPane.setHalignment(lblTele, HPos.RIGHT);
		grid.add(sldTele, 1, 8,4,1);
		
		grid.add(lblCine, 0, 9);
		GridPane.setHalignment(lblCine, HPos.RIGHT);
		grid.add(sldCine, 1, 9,4,1);
		
		grid.add(botones, 0, 10,5,1);
		
		
		ColumnConstraints c1=new ColumnConstraints();
		ColumnConstraints c2=new ColumnConstraints();
		ColumnConstraints c3=new ColumnConstraints();
		ColumnConstraints c4=new ColumnConstraints();
		c4.setHgrow(Priority.ALWAYS);
		grid.getColumnConstraints().add(c1);
		grid.getColumnConstraints().add(c2);
		grid.getColumnConstraints().add(c3);
		grid.getColumnConstraints().add(c4);
		grid.setStyle("-fx-padding: 10;");
		grid.setHgap(10);
		grid.setVgap(10);
		
		Scene scene = new Scene(grid);
		stage.setScene(scene);
		stage.setTitle("ENCUESTA");
		stage.show();
	}
		
	
	public String Verificar() {
		String err = "";
		String info = "";
		fallo = false;
		int hermanos = 0;

		
		if (txfProfesion.getText().length()>0) {
			info+=txfProfesion.getText();
		}else {
			err=err+"Hay rellenar el campo profesión";
		}
		
		
		try {
			hermanos=Integer.parseInt(txfHermanos.getText());
			info+="\nNº de hermanos:"+hermanos;
		} catch (NumberFormatException e) {
			err+="\nHay rellenar el campo numero de hermanos";
		}
		
		
		info+="\nEdad: "+edades.getSelectionModel().getSelectedItem();
		

		if (rbHombre.isSelected()) {
			info+="\n"+"Sexo: Hombre";
		}else if (rbMujer.isSelected()) {
			info+="\n"+"Sexo: Mujer";
		}else if (rbOtro.isSelected()) {
			info+="\n"+"Sexo: Otro";
		}
		

		if (cbPractica.isSelected() && lista.getSelectionModel().getSelectedItems().isEmpty()) {
			err+="\nTienes que indicar los deportes que practicas";
		}else if (cbPractica.isSelected()) {
			info+="\n"+"Deportes que practicas: ";
			for (int i = 0; i <lista.getSelectionModel().getSelectedItems().size(); i++) {
				info+="\n"+"\t"+ lista.getSelectionModel().getSelectedItems().get(i);
			}
		}
		

		info+="\n"+"Grado de afición a las compras: "+sldCompra.getValue();
		info+="\n"+"Grado de afición a ver la televisión: "+sldTele.getValue();
		info+="\n"+"Grado de afición a ir al cine: "+sldCine.getValue();
		
		if (!err.equals("")) {
			fallo=true;
			return err;
		}else {
			return info;
		}
	}
	
	private void ventanaInfo(Stage stage) {
		Alert alert;
		this.Verificar();
		if (fallo) {
			alert = new Alert(Alert.AlertType.ERROR);
		}else {
			alert = new Alert(Alert.AlertType.INFORMATION);
		}
		alert.setHeaderText(null);
		
		alert.setContentText(this.Verificar());
		alert.initOwner(stage);
		alert.setTitle("TUS DATOS");
		alert.showAndWait();
	}

	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		if (cbPractica.isSelected()) {
			lista.setDisable(false);
		} else {
			lista.setDisable(true);
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}