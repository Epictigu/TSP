package de.fhswf.ea.stp.menu;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.scene.control.Menu;

public class MenuTransform extends Menu{
	
	public MenuTransform(LocationChart chart) {
		super("Transformieren");
		
		getItems().add(new MenuItemXTurn(chart));
		getItems().add(new MenuItemYTurn(chart));
	}
	
}
