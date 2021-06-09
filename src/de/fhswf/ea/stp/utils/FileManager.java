package de.fhswf.ea.stp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.chart.XYChart;

public class FileManager {
	
	public static XYChart.Series<Number, Number> loadFile(String filePath) throws FileNotFoundException {
		File f = new File(filePath);
		if(!f.exists())
			throw new FileNotFoundException("Ung�ltiger Dateipfad! Datei existiert nicht.");
		
		XYChart.Series<Number, Number> cities = new XYChart.Series<Number, Number>();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String s = null;
		try {
			while((s = bufferedReader.readLine()) != null) {
				
				String[] args = s.split(" ");
				
				if(args[0].equals("NAME:")) {
					cities.setName(s.replaceFirst("NAME: ", ""));
				} else if(args[0].equals("NAME") && args[1].equals(":")) {
					cities.setName(s.replaceFirst("NAME : ", ""));
				} else {
					try {
						if(args.length < 3)
							continue;
						
						Integer.parseInt(args[0]);
						
						Double y = Double.parseDouble(args[1]);
						Double x = Double.parseDouble(args[2]);
						
						cities.getData().add(new XYChart.Data<Number, Number>(x, y));
					} catch (NumberFormatException e) {}
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Fertig!");
		
		return cities;
	}
	
}
