package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.Menu;

/**
 * Men� zum Darstellen der Achsendrehungsoptionen.
 *
 * @author Timo R�der, Dominik M�ller, Marcus Nolzen
 * @version 1.0
 */
public class MenuTransform extends Menu{
	
	/**
	 * Erstellt die Men�eintr�ge zur Achsendrehung
	 *
	 * @param chart
	 * 	Locationchart zur Weitergabe an die Men�eintr�ge
	 */
	public MenuTransform(LocationChart chart) {
		super("Transformieren");
		
		getItems().add(new MenuItemXTurn(chart));
		getItems().add(new MenuItemYTurn(chart));
	}
	
}
