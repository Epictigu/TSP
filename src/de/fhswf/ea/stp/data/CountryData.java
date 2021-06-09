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
		for (Route r : routes) {
			if (checkRoute(r)) {
				if (edgeAmount.putIfAbsent(r.getFirstData(), 1) != null)
					edgeAmount.put(r.getFirstData(), edgeAmount.get(r.getFirstData()));
				if (edgeAmount.putIfAbsent(r.getSecondData(), 1) != null)
					edgeAmount.put(r.getSecondData(), edgeAmount.get(r.getSecondData()));
				tspRoutes.add(r);
			}
		}

		System.out.println("Baue Route zusammen ...");
		cities.getData().clear();
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

	private void addRoute(Route r) {
		System.out.println(r);
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
