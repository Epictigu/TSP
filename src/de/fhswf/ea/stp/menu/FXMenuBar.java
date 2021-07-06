package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.MenuBar;

/**
 * Menüleiste zum Anzeigen der einzelnen Menüpunkte
 *
 * @author Timo Röder, Dominik Müller, Marcus Nolzen
 * @version 1.0
 */
public class FXMenuBar extends MenuBar {

	/**
	 * Konstruktor zum Anlegen des Hauptmenüs.
	 *
	 * @param chart
	 * 	Locationchart zur Weitergabe an die Menüeinträge.
	 */
	public FXMenuBar(LocationChart chart) {
		getMenus().add(new MenuFile(chart));
		getMenus().add(new MenuTransform(chart));
	}
	
}
