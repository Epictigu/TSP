package de.fhswf.ea.stp.components;

import de.fhswf.ea.stp.menu.FXMenuBar;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Klasse abgeleitet von BorderPane zum Anzeigen der UI-Elemente.
 *
 * @author Timo Röder, Dominik Müller, Marcus Nolzen
 * @version 1.0
 */
public class MainComponent extends BorderPane{
	
	/**
	 * Konstruktor zum Erstellen und Einfügen der UI-Elemente.
	 *
	 * @param primaryStage
	 * 	Stage für das UI, weitergeleitet an LocationChart.
	 */
	public MainComponent(Stage primaryStage) {
		ProgressBar testBar = new ProgressBar(0.0);
		testBar.setMaxWidth(Double.MAX_VALUE);
		LocationChart lChart = new LocationChart(new NumberAxis("", 0, 20, 5), new NumberAxis("", 0, 20, 5), primaryStage, testBar);
		
		setTop(new FXMenuBar(lChart));
		setCenter(lChart);
		setBottom(testBar);
		
	}
	
}
