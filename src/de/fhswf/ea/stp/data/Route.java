package de.fhswf.ea.stp.data;

import javafx.scene.chart.XYChart.Data;

public class Route {

	private Data<Number, Number> firstData;
	
	private Data<Number, Number> secondData;
	
	private Double distance;
	
	public Route(Data<Number, Number> firstData, Data<Number, Number> secondData) {
		this.firstData = firstData;
		this.secondData = secondData;
		
		distance = getDistance(firstData, secondData);
	}
	
	public Data<Number, Number> getFirstData(){
		return firstData;
	}
	
	public Data<Number, Number> getSecondData(){
		return secondData;
	}
	
	public Double getDistance() {
		return distance;
	}
	
	private Double getDistance(Data<Number, Number> firstData, Data<Number, Number> secondData) {
		Double distX = secondData.getXValue().doubleValue() - firstData.getXValue().doubleValue();
		Double distY = secondData.getYValue().doubleValue() - firstData.getYValue().doubleValue();
		
		return Math.sqrt(Math.pow(distX, 2D) + Math.pow(distY, 2D));
	}
	
	@Override
	public String toString() {
		return "P1: " + firstData + " P2:" +  secondData + " Distanz: " + distance;
	}
	
	public int compareTo(Route compare) {
		if(distance < compare.distance)
			return -1;
		else if(distance > compare.distance)
			return 1;
		else
			return 0;
	}
	
}
