package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.Operaciones;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
 
public class CalculadoraController implements Initializable {
	
	@FXML
	private TextField txfoperador1;
	@FXML
	private TextField txfoperador2;
	@FXML
	private Button btnCalcular;
	@FXML
	private RadioButton rbSumar;
	@FXML
	private ToggleGroup operaciones;
	@FXML
	private RadioButton rbResta;
	@FXML
	private RadioButton rbMultiplicar;
	@FXML
	private RadioButton rbDividir;
	@FXML
	private TextField tfResultado;	
	
    // Event Listener on Button.onAction
    @FXML
    public void calcular(ActionEvent event) {
        try {
            double op1 = Double.parseDouble(this.txfoperador1.getText());
            double op2 = Double.parseDouble(this.txfoperador2.getText());
            Operaciones operaciones = new Operaciones(op1, op2);
            if (this.rbSumar.isSelected()) {
                this.tfResultado.setText(operaciones.sumar() + "");
            } else if (this.rbResta.isSelected()) {
                this.tfResultado.setText(operaciones.restar() + "");
            } else if (this.rbMultiplicar.isSelected()) {
                this.tfResultado.setText(operaciones.multiplicar() + "");
            } else if (this.rbDividir.isSelected()) {
                if (op2 == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("El operando 2 no puede ser 0");
                    alert.showAndWait();
                } else {
                    this.tfResultado.setText(operaciones.dividir() + "");
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Formato incorrecto");
            alert.showAndWait();
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}

