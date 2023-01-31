// GeneralTab.java
package control;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GeneralTab extends Tab {
	TextField firstNameFld;
	TextField lastNameFld;
	DatePicker dob;

	public GeneralTab(String text, Node graphic) {
		this.setText(text);
		this.setGraphic(graphic);
		init();
	}

	public void init() {
		firstNameFld = new TextField();
		lastNameFld = new TextField();
		dob = new DatePicker();
		dob.setPrefWidth(200);
		GridPane grid = new GridPane();
		grid.addRow(0, new Label("First Name:"), firstNameFld);
		grid.addRow(1, new Label("Last Name:"), lastNameFld);
		grid.addRow(2, new Label("DOB:"), dob);
		this.setContent(grid);
	}
}
