package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.Menu;

/**
 * Menü zum Anbieten der Beispiele
 *
 * @author Timo Röder, Dominik Müller, Marcus Nolzen
 * @version 1.0
 */
public class MenuFile extends Menu{

	/**
	 * Konstruktor zum Erstellen des Menüs
	 *
	 * @param chart
	 * 	Locationchart zum weiterleiten an das Beispielmenü
	 */
	public MenuFile(LocationChart chart) {
		super("Datei");
		
		getItems().add(new MenuExamples(chart));
	}
	
}
