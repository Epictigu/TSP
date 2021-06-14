package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.MenuItem;

public class MenuItemYTurn extends MenuItem{
	
	public MenuItemYTurn(LocationChart chart) {
		super("Y-Achse drehen");
		
		setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				NumberAxis nA = (NumberAxis) chart.getYAxis();
				
				double min = nA.getLowerBound();
				double max = nA.getUpperBound();
				
				nA.setLowerBound(max);
				nA.setUpperBound(min);
				nA.setTickUnit(nA.getTickUnit() * -1.0);
				
			}
		});
	}
	
}
