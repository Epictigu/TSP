package de.fhswf.ea.stp.data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhswf.ea.stp.utils.FileManager;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class CountryData {

	private XYChart.Series<Number, Number> cities;
	private List<Route> routes = new ArrayList<Route>();
	private List<Route> tspRoutes = new ArrayList<Route>();
	private Map<Data<Number, Number>, Integer> edgeAmount = new HashMap<Data<Number, Number>, Integer>();

	public CountryData(String filePath) throws FileNotFoundException {
		cities = FileManager.loadFile(filePath);

		for (int i = 0; i < cities.getData().size(); i++) {
			for (int j = i + 1; j < cities.getData().size(); j++) {
				routes.add(new Route(cities.getData().get(i), cities.getData().get(j), i, j));
			}
		}
	}

	public void sortRoutes() {
		Collections.sort(routes, new Comparator<Route>() {
			@Override
			public int compare(Route o1, Route o2) {
				return o1.getDistance() < o2.getDistance() ? -1 : o1.getDistance() == o2.getDistance() ? 0 : 1;
			}
		});
	}

	public void calcTSP() {
		System.out.println("Sortiere ...");
		sortRoutes();
		tspRoutes = new ArrayList<Route>();
		
		System.out.println("Entnehme kürzeste Routen ...");
//		while(!routes.isEmpty()) {
//			List<Route> remove = new ArrayList<Route>();
			
			for (Route r : routes) {
//				System.out.println(r);
				if (checkRoute(r)) {
					if((edgeAmount.containsKey(r.getSecondData()) && edgeAmount.containsKey(r.getFirstData())) && (cities.getData().size() > tspRoutes.size() + 1 )) {
						tspRoutes.add(r);
						if(isCircle(r)) {
							tspRoutes.remove(r);
							continue;
						}
					} else {
						tspRoutes.add(r);
					}
//					System.out.println(r.getFirstData() + ": " + edgeAmount.containsKey(r.getFirstData()) + " | " + r.getSecondData() + ": " + edgeAmount.containsKey(r.getSecondData()));
					if (edgeAmount.putIfAbsent(r.getFirstData(), 1) != null)
						edgeAmount.put(r.getFirstData(), edgeAmount.get(r.getFirstData()) + 1);
					if (edgeAmount.putIfAbsent(r.getSecondData(), 1) != null)
						edgeAmount.put(r.getSecondData(), edgeAmount.get(r.getSecondData()) + 1);
				}
			}
//			for(Route r : remove)
//				routes.remove(r);
//		}
		
//		for(Route r : tspRoutes)
//			System.out.println(r);

		System.out.println("Baue Route zusammen ...");
		cities = new XYChart.Series<Number, Number>();
		cities.getData().add(tspRoutes.get(0).getFirstData());
		addRoute(tspRoutes.get(0));
	}

	public XYChart.Series<Number, Number> getCities() {
		return cities;
	}

	public List<Route> getRoutes() {
		return Collections.unmodifiableList(routes);
	}

	private boolean checkRoute(Route r) {
		if (edgeAmount.containsKey(r.getFirstData()))
			if (edgeAmount.get(r.getFirstData()) >= 2)
				return false;
		if (edgeAmount.containsKey(r.getSecondData()))
			if (edgeAmount.get(r.getSecondData()) >= 2)
				return false;

		return true;
	}

	private boolean isCircle(Route r) {
		Route currentR = r;
		Data<Number, Number> currentData = r.getFirstData();
		
		while(true) {
			currentR = getOtherNeighbor(currentData, currentR);
			
			if(currentR == r)
				return true;
			if(currentR == null)
				break;
			
			if(currentR.getFirstData() == currentData)
				currentData = currentR.getSecondData();
			else
				currentData = currentR.getFirstData();
		}
		
		return false;
	}
	
	private Route getOtherNeighbor(Data<Number, Number> data, Route oldRoute) {
//		System.out.println(data + " | " + oldRoute);
		for(Route r : tspRoutes) {
			if(r != oldRoute) {
				if(r.getFirstData() == data || r.getSecondData() == data)
					return r;
			}
		}
		
		return null;
	}
	
	private void addRoute(Route r) {
//		System.out.println(r);
		if (!(cities.getData().contains(r.getFirstData()))) {
			cities.getData().add(r.getFirstData());
			for (Route nR : tspRoutes) {
				if(nR == r)
					continue;
				if (nR.getFirstData() == r.getFirstData() || nR.getSecondData() == r.getFirstData()) {
					addRoute(nR);
					break;
				}
			}
		} else if (!(cities.getData().contains(r.getSecondData()))) {
			cities.getData().add(r.getSecondData());
			for (Route nR : tspRoutes) {
				if(nR == r)
					continue;
				if (nR.getFirstData() == r.getSecondData() || nR.getSecondData() == r.getSecondData()) {
					addRoute(nR);
					break;
				}
			}
		}
	}

}
