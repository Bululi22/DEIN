module Tanda_A_S {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens Ejercicio_A to javafx.graphics, javafx.fxml;
	opens Ejercicio_B to javafx.graphics, javafx.fxml;
	opens Ejercicio_C to javafx.graphics, javafx.fxml;
	opens Ejercicio_D to javafx.graphics, javafx.fxml;
	opens Ejercicio_E to javafx.graphics, javafx.fxml;
	opens Ejercicio_F to javafx.graphics, javafx.fxml;
	opens model to javafx.graphics, javafx.fxml, javafx.base;
}
