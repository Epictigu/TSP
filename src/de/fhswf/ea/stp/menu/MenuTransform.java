package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.Menu;

/**
 * Menü zum Darstellen der Achsendrehungsoptionen.
 *
 * @author Timo Röder, Dominik Müller, Marcus Nolzen
 * @version 1.0
 */
public class MenuTransform extends Menu{
	
	/**
	 * Erstellt die Menüeinträge zur Achsendrehung
	 *
	 * @param chart
	 * 	Locationchart zur Weitergabe an die Menüeinträge
	 */
	public MenuTransform(LocationChart chart) {
		super("Transformieren");
		
		getItems().add(new MenuItemXTurn(chart));
		getItems().add(new MenuItemYTurn(chart));
	}
	
}
