package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.Menu;

public class MenuExamples extends Menu{
	
	public MenuExamples(LocationChart chart) {
		super("Beispiele");
		
		getItems().add(new MenuItemExample("Japan", "resources/ja9847.tsp", chart));
		getItems().add(new MenuItemExample("Djibouti", "resources/dj38.tsp", chart));
		getItems().add(new MenuItemExample("Irland", "resources/ei8246.tsp", chart));
		getItems().add(new MenuItemExample("Luxemburg", "resources/lu980.tsp", chart));
		getItems().add(new MenuItemExample("Burma", "resources/bm33708.tsp", chart));
		getItems().add(new MenuItemExample("Italien", "resources/it16862.tsp", chart));
		getItems().add(new MenuItemExample("Argentinien", "resources/ar9152.tsp", chart));
//		getItems().add(new MenuItemExample("China", "resources/ch71009.tsp", chart));
	}
	
}
