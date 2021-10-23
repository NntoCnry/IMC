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

	public void start(Stage primaryStage) throws Exception {
		Label pesoLabel = new Label("  Peso: ");

		TextField pesoTextField = new TextField();
		
		Label klLabel = new Label(" kg");
		
		Label altLabel = new Label("Altura: ");

		TextField altTextField = new TextField();

		Label cmLabel = new Label(" cm");

		Label valorLabel = new Label(" IMC:");

		Label statusLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso ");

		HBox pesoHBox = new HBox();
		pesoHBox.setSpacing(10);
		pesoHBox.getChildren().addAll(pesoLabel, pesoTextField, klLabel);

		HBox altHBox = new HBox();
		altHBox.setSpacing(10);
		altHBox.getChildren().addAll(altLabel, altTextField, cmLabel);

		VBox root = new VBox();
		root.setSpacing(10);
		root.setFillWidth(false);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(pesoHBox, altHBox, valorLabel, statusLabel);

		Scene escena = new Scene(root, 320, 200);
		
		primaryStage.setScene(escena);
		primaryStage.setTitle("IMC.fxml");
		primaryStage.show();

		SimpleDoubleProperty pesoProperty = new SimpleDoubleProperty();
		Bindings.bindBidirectional(pesoTextField.textProperty(), pesoProperty, new NumberStringConverter());

		SimpleDoubleProperty altProperty = new SimpleDoubleProperty();
		Bindings.bindBidirectional(altTextField.textProperty(), altProperty, new NumberStringConverter());
		DoubleBinding operacion=pesoProperty.divide((altProperty.divide(100)).multiply((altProperty.divide(100))));		
	
		
		SimpleDoubleProperty resultOp = new SimpleDoubleProperty();
		resultOp.bind(operacion);
		

		valorLabel.textProperty().bind(Bindings.concat("IMC: ").concat(Bindings.when(altProperty.isEqualTo(0)).then(" ").otherwise(resultOp.asString("%.2f"))));

		resultOp.addListener((o, ov, nv) -> {
			double imc = nv.doubleValue();
			if (imc < 18.5)
				statusLabel.setText("Bajo peso");
			else if (imc >= 18.5 && imc < 25)
				statusLabel.setText("Normal");
			else if (imc >= 25 && imc < 30)
				statusLabel.setText("Sobrepeso");
			else
				statusLabel.setText("Obeso");
		});

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
