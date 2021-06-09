package de.fhswf.ea.stp.components;

import de.fhswf.ea.stp.menu.FXMenuBar;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainComponent extends BorderPane{
	
	public MainComponent(Stage primaryStage) {
		LocationChart lChart = new LocationChart(new NumberAxis("X", 0, 20, 5), new NumberAxis("Y", 0, 20, 5), primaryStage);
		
		setTop(new FXMenuBar(lChart));
		setCenter(lChart);
	}
	
}
