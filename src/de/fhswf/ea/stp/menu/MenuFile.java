package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.Menu;

/**
 * Men� zum Anbieten der Beispiele
 *
 * @author Timo R�der, Dominik M�ller, Marcus Nolzen
 * @version 1.0
 */
public class MenuFile extends Menu{

	/**
	 * Konstruktor zum Erstellen des Men�s
	 *
	 * @param chart
	 * 	Locationchart zum weiterleiten an das Beispielmen�
	 */
	public MenuFile(LocationChart chart) {
		super("Datei");
		
		getItems().add(new MenuExamples(chart));
	}
	
}
