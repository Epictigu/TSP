package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.MenuBar;

public class FXMenuBar extends MenuBar {

	public FXMenuBar(LocationChart chart) {
		getMenus().add(new MenuFile(chart));
		getMenus().add(new MenuTransform(chart));
	}
	
}
