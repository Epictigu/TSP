package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.MenuBar;

/**
 * Men�leiste zum Anzeigen der einzelnen Men�punkte
 *
 * @author Timo R�der, Dominik M�ller, Marcus Nolzen
 * @version 1.0
 */
public class FXMenuBar extends MenuBar {

	/**
	 * Konstruktor zum Anlegen des Hauptmen�s.
	 *
	 * @param chart
	 * 	Locationchart zur Weitergabe an die Men�eintr�ge.
	 */
	public FXMenuBar(LocationChart chart) {
		getMenus().add(new MenuFile(chart));
		getMenus().add(new MenuTransform(chart));
	}
	
}
