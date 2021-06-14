package de.fhswf.ea.stp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;

public class FileManager {
	
	public static XYChart.Series<Number, Number> loadFile(String filePath, ProgressBar progressBar) throws FileNotFoundException {
		File f = new File(filePath);
		if(!f.exists())
			throw new FileNotFoundException("Ungültiger Dateipfad! Datei existiert nicht.");
		
		XYChart.Series<Number, Number> cities = new XYChart.Series<Number, Number>();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String s = null;
		int dimension = -1;
		int cityLines = 0;
		int cityLinesTotal = 0;
		
		try {
			while((s = bufferedReader.readLine()) != null) {
				
				String[] args = s.split(" ");
				
				if(args[0].equals("NAME:")) {
					cities.setName(s.replaceFirst("NAME: ", ""));
				} else if(args[0].equals("NAME") && args[1].equals(":")) {
					cities.setName(s.replaceFirst("NAME : ", ""));
				} else if(args[0].equals("DIMENSION:")) {
					dimension = Integer.parseInt(s.replaceFirst("DIMENSION: ", ""));
				} else if(args[0].equals("DIMENSION") && args[1].equals(":")) {
					dimension = Integer.parseInt(s.replaceFirst("DIMENSION : ", ""));
				} else {
					try {
						if(args.length < 3)
							continue;
						
						Integer.parseInt(args[0]);
						
						cityLines++;
						cityLinesTotal++;
						if(dimension > 0) {
							if(cityLines >= (double) dimension / 10) {
								cityLines = 0;
								
								double progress = ((double) cityLinesTotal) / dimension / 20;
								progressBar.setProgress(progress);
							}
						}
						
						
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
		
		progressBar.setProgress(0.05);
		
		return cities;
	}
	
}
