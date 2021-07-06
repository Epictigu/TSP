package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.Menu;

/**
 * Men� zum Anbieten von Beispielen.
 *
 * @author Timo R�der, Dominik M�ller, Marcus Nolzen
 * @version 1.0
 */
public class MenuExamples extends Menu{
	
	/**
	 * F�gt die einzelnen Beispieleintr�ge hinzu.
	 *
	 * @param chart
	 * 	�bergibt den Locationchart an die Men�eintr�ge weiter.
	 */
	public MenuExamples(LocationChart chart) {
		super("Beispiele");
		
		getItems().add(new MenuItemExample("Japan", "resources/ja9847.tsp", chart, this));
		getItems().add(new MenuItemExample("Djibouti", "resources/dj38.tsp", chart, this));
		getItems().add(new MenuItemExample("Irland", "resources/ei8246.tsp", chart, this));
		getItems().add(new MenuItemExample("Luxemburg", "resources/lu980.tsp", chart, this));
		getItems().add(new MenuItemExample("Argentinien", "resources/ar9152.tsp", chart, this));
		
		//Deaktiviert wegen Ram-Nutzung:
		
//		getItems().add(new MenuItemExample("Burma", "resources/bm33708.tsp", chart, this));
//		getItems().add(new MenuItemExample("Italien", "resources/it16862.tsp", chart, this));
//		getItems().add(new MenuItemExample("China", "resources/ch71009.tsp", chart));
	}
	
}
