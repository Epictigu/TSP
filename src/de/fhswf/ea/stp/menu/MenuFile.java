package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuFile extends Menu{

	public MenuFile(LocationChart chart) {
		super("Datei");
		
		MenuItem selectFile = new MenuItem("Datei auswählen");
		getItems().add(selectFile);
		getItems().add(new MenuExamples(chart));
	}
	
}
