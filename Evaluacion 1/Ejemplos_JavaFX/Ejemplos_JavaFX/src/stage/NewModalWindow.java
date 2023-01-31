//NewModalWindow.java
package stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewModalWindow extends Application {
	    Scene scene;

	    public static void main(String[] args) {
	        Application.launch(args);
	    }

	    @Override
	    public void start(Stage stage) {
	        Button btnNuevo = new Button("Nuevo");
	        btnNuevo.setOnAction(e -> nuevo());
	        HBox root = new HBox(btnNuevo);
	        root.setSpacing(15);
	        root.setStyle("-fx-padding: 20;");
	        scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("Abrir nueva ventana");
	        stage.show();
	    }

	    public void nuevo() {
	        TextField fNameField = new TextField();
	        TextField lNameField = new TextField();
	        GridPane root = new GridPane();
	        root.setHgap(10);
	        root.setVgap(5);
	        root.addRow(0, new Label("Nombre:"), fNameField);
	        root.addRow(1, new Label("Apellido:"), lNameField);
	        root.addRow(2, new Button("Añadir"));
	        root.setStyle("-fx-padding: 20;");
	        Scene newScene = new Scene(root);
	        Stage newStage = new Stage();
	        newStage.setScene(newScene);
	        newStage.setTitle("Nuevo usuario");
	        newStage.initModality(Modality.APPLICATION_MODAL);
	        newStage.show();
	    }
	}
