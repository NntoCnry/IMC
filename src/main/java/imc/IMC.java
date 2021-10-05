package imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {
	//creas las variables que vas a usar
	private TextField pesoTextField, alturaTextField;
	private Label pesoLabel,kiloLabel,alturaLabel,centrimetroLabel,clasificacionLabel,estadoLabel;
	private SimpleDoubleProperty pesoSimpleDoubleProperty,alturaSimpleDoubleProperty;
	
	
	public void start(Stage primaryStage) throws Exception {
		pesoTextField= new TextField();
		alturaTextField= new TextField();
		pesoLabel= new Label("  Peso: ");
		kiloLabel= new Label(" kg");
		alturaLabel= new Label("Altura: ");
		centrimetroLabel= new Label(" cm");
		clasificacionLabel= new Label(" IMC: (peso * altura^2)");
		estadoLabel=new Label("Bajo peso | Normal | Sobrepeso | Obeso ");

		HBox pesoHBox=new HBox();
		pesoHBox.setSpacing(5);
		pesoHBox.getChildren().addAll(pesoLabel,pesoTextField,kiloLabel);
		
		HBox alturaHBox=new HBox();
		alturaHBox.getChildren().addAll(alturaLabel,alturaTextField,centrimetroLabel);
		alturaHBox.setSpacing(5);
		
		VBox root = new VBox();
		root.setSpacing(5);
		root.setFillWidth(false); 
		root.setAlignment(Pos.CENTER); 
		root.getChildren().addAll(pesoHBox,alturaHBox,clasificacionLabel,estadoLabel);
		
		//y crear la scena que contiene la ventana
		Scene escena = new Scene(root, 320, 200);
		primaryStage.setScene(escena);
		primaryStage.setTitle("IMC.fxml");
		primaryStage.show();
		
		pesoSimpleDoubleProperty = new SimpleDoubleProperty();
		Bindings.bindBidirectional(pesoTextField.textProperty(), pesoSimpleDoubleProperty, new NumberStringConverter());
		
		alturaSimpleDoubleProperty = new SimpleDoubleProperty();
		Bindings.bindBidirectional(alturaTextField.textProperty(), alturaSimpleDoubleProperty, new NumberStringConverter());
		
		DoubleBinding operacionDoubleBindings=alturaSimpleDoubleProperty.divide(100);
		operacionDoubleBindings=pesoSimpleDoubleProperty.divide(operacionDoubleBindings.multiply(operacionDoubleBindings));
		
		SimpleDoubleProperty resultado;
		resultado = new SimpleDoubleProperty();
		resultado.bind(operacionDoubleBindings);
		//clasificacionLabel.textProperty().bind(Bindings.concat());
		
		clasificacionLabel.textProperty().bind(Bindings.concat("IMC: ")
				.concat(Bindings.when(alturaSimpleDoubleProperty.isEqualTo(0)).then("(peso * altura^2)").otherwise(resultado.asString("%.2f"))));
		
		resultado.addListener((o, ov, nv)->{
			double valorIMC=nv.doubleValue();
			if(valorIMC < 18.5) {
				estadoLabel.setText("Bajo peso");
			}else if(valorIMC >=18.5 && valorIMC < 25) {
				estadoLabel.setText("Normal");
			}else if(valorIMC >=25 && valorIMC < 30) {
				estadoLabel.setText("Sobrepeso");
			}else {
				estadoLabel.setText("Obeso");
			}
		});
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
launch(args);
	}

}
