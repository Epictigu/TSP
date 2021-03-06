package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.MenuItem;

/**
 * Men?eintrag zum drehen der x-Achse
 *
 * @author Timo R?der, Dominik M?ller, Marcus Nolzen
 * @version 1.0
 */
public class MenuItemXTurn extends MenuItem{
	
	/**
	 * Konstruktor zum Erstellen des Men?eintrages.
	 *
	 * @param chart
	 * 	Locationchart zum Drehen der Achse
	 */
	public MenuItemXTurn(LocationChart chart) {
		super("X-Achse drehen");
		
		setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				NumberAxis nA = (NumberAxis) chart.getXAxis();
				
				double min = nA.getLowerBound();
				double max = nA.getUpperBound();
				
				nA.setLowerBound(max);
				nA.setUpperBound(min);
				nA.setTickUnit(nA.getTickUnit() * -1);
			}
		});
	}
	
}
