package de.fhswf.ea.stp.data;

import javafx.scene.chart.XYChart.Data;

/**
 * Objektklasse für Routen.
 * Speichert die Punkte sowie die Distanz.
 *
 * @author Timo Röder, Dominik Müller, Marcus Nolzen
 * @version 1.0
 */
public class Route {

	private Data<Number, Number> firstData;
	
	private Data<Number, Number> secondData;
	
	private Double distance;
	
	/**
	 * Konstruktor zum Erstellen einer Route.
	 *
	 * @param firstData
	 * @param secondData
	 */
	public Route(Data<Number, Number> firstData, Data<Number, Number> secondData) {
		this.firstData = firstData;
		this.secondData = secondData;
		
		distance = getDistance(firstData, secondData);
	}
	
	/**
	 * Getter-Funktion für den ersten Datensatz.
	 *
	 * @return
	 * 	Erster Datensatz
	 */
	public Data<Number, Number> getFirstData(){
		return firstData;
	}
	
	/**
	 * Getter-Funktion für den zweiten Datensatz.
	 *
	 * @return
	 * 	Zweiter Datensatz
	 */
	public Data<Number, Number> getSecondData(){
		return secondData;
	}
	
	/**
	 * Getter-Funktion für die Distanz
	 *
	 * @return
	 * 	Distanz zwischen den beiden Datensätzen.
	 */
	public Double getDistance() {
		return distance;
	}
	
	private Double getDistance(Data<Number, Number> firstData, Data<Number, Number> secondData) {
		Double distX = secondData.getXValue().doubleValue() - firstData.getXValue().doubleValue();
		Double distY = secondData.getYValue().doubleValue() - firstData.getYValue().doubleValue();
		
		return Math.sqrt(Math.pow(distX, 2D) + Math.pow(distY, 2D));
	}
	
	/**
	 * ToString Methode der Route.
	 */
	@Override
	public String toString() {
		return "P1: " + firstData + " P2:" +  secondData + " Distanz: " + distance;
	}
	
	/**
	 * CompareTo Methode für die Sortierung
	 *
	 * @param compare
	 * 	Route mit der vergliechen werden soll.
	 * @return
	 * 	wenn this < compare
	 * 	wenn this > compare
	 * 	wenn this = compare
	 */
	public int compareTo(Route compare) {
		if(distance < compare.distance)
			return -1;
		else if(distance > compare.distance)
			return 1;
		else
			return 0;
	}
	
}
