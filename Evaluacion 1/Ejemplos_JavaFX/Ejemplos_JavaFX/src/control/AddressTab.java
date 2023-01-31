// AddressTab.java
package control;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AddressTab extends Tab {
	TextField streetFld;
	TextField cityFld;
	TextField stateFld;
	TextField zipFld;

	public AddressTab(String text, Node graphic) {
		this.setText(text);
		this.setGraphic(graphic);
		init();
	}

	public void init() {
		streetFld = new TextField();
		cityFld = new TextField();
		stateFld = new TextField();
		zipFld = new TextField();
		GridPane grid = new GridPane();
		grid.addRow(0, new Label("Street:"), streetFld);
		grid.addRow(1, new Label("City:"), cityFld);
		grid.addRow(2, new Label("State:"), stateFld);
		grid.addRow(3, new Label("ZIP:"), zipFld);
		this.setContent(grid);
	}
}
