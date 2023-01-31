// EventRegistration.java
package event;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class EventRegistration extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		Circle circle = new Circle(100, 100, 50);
		circle.setFill(Color.CORAL);
		// Create a MouseEvent handler
		EventHandler<MouseEvent> mouseEventHandler = e -> System.out.println("Mouse event handler has been called.");
		// Register the MouseEvent handler to the Circle for mouse-clicked events
		circle.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandler);
		HBox root = new HBox();
		root.getChildren().add(circle);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Registering Event Handlers");
		stage.show();
		stage.sizeToScene();
	}
}
