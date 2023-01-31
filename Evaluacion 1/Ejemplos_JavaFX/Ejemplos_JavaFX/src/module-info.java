module Ejemplos_JavaFX {
//	requires javafx.controls;
//	requires javafx.web;
//	requires javafx.fxml;
//	requires javafx.swing;
//	requires javafx.media;
//	requires javafx.graphics;
//	requires javafx.base;
	
	
	requires javafx.controls;
	requires java.desktop;
	requires javafx.web;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.media;
	requires jdk.jsobject;

	opens binding to javafx.graphics, javafx.base;
	opens collections to javafx.graphics, javafx.base;
	opens container to javafx.graphics, javafx.base;
	opens control to javafx.graphics, javafx.base;
	opens event to javafx.graphics, javafx.base;
	opens image to javafx.graphics, javafx.base;
	opens intro to javafx.graphics, javafx.base;
	opens model to javafx.graphics, javafx.base;
	opens node to javafx.graphics, javafx.base;
	opens scene to javafx.graphics, javafx.base;
	opens shape to javafx.graphics, javafx.base;
	opens stage to javafx.graphics, javafx.base;
	opens style to javafx.graphics, javafx.base;
}
