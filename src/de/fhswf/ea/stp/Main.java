package de.fhswf.ea.stp;
	
import de.fhswf.ea.stp.components.MainComponent;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Main Klasse zum Ausf�hren der JavaFX Applikation.
 *
 * @author Timo R�der, Dominik M�ller, Marcus Nolzen
 * @version 1.0
 */
public class Main extends Application {
	
	/**
	 * Start Methode zum Ausf�hren der JavaFX Applikation.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			MainComponent com = new MainComponent(primaryStage);
			Scene scene = new Scene(com);
			scene.getStylesheets().add(this.getClass().getResource("css/main.css").toExternalForm());
			primaryStage.setTitle("TSP-Algorithmus (Greedy)");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main Methode zum Ausf�hren des Programmes
	 *
	 * @param args
	 * 	Kommandozeilenparameter
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
