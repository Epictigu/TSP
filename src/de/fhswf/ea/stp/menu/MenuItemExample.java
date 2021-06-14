package de.fhswf.ea.stp.menu;

import java.io.FileNotFoundException;

import de.fhswf.ea.stp.components.LocationChart;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;

public class MenuItemExample extends MenuItem{

	public MenuItemExample(String name, String path, LocationChart chart, Menu parent) {
		super(name);
		
		setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					chart.setCountry(path, parent);
				} catch (FileNotFoundException e1) {
					Alert alert = new Alert(AlertType.ERROR, "Datei konnte nicht geladen werden!\n" + e1.getMessage());
					alert.showAndWait();
					e1.printStackTrace();
				}
			}
		});
	}
	
}
