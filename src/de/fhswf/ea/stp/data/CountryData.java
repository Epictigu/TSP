package de.fhswf.ea.stp.data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhswf.ea.stp.utils.FileManager;
import de.fhswf.ea.stp.utils.QuickSort;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ProgressBar;

/**
 * Klasse zum Abspeichern von Länderdaten.
 * Führt den TSP-Algorithmus bei Bedarf aus.
 *
 * @author Timo Röder, Dominik Müller, Marcus Nolzen
 * @version 1.0
 */
public class CountryData {

	private XYChart.Series<Number, Number> cities;
	private List<Route> routes = new ArrayList<Route>();
	private List<Route> tspRoutes = new ArrayList<Route>();
	private Map<Data<Number, Number>, Integer> edgeAmount = new HashMap<Data<Number, Number>, Integer>();
	private ProgressBar progressBar;
	
	private QuickSort quickSort;
	
	/**
	 * Konstruktor zum Abspeichern und Einlesen von Länderdaten.
	 *
	 * @param filePath
	 * 	Pfad für die Länderdateien.
	 * @param progressBar
	 * 	Ladebalken zum Aktualisieren während des TSP-Algorithmus.
	 * @throws FileNotFoundException
	 * 	wenn filePath == Nicht gefunden!
	 */
	public CountryData(String filePath, ProgressBar progressBar) throws FileNotFoundException {
		this.progressBar = progressBar;
		this.quickSort = new QuickSort(progressBar);
		
		long start = System.currentTimeMillis();
		cities = FileManager.loadFile(filePath, progressBar);
		long end = System.currentTimeMillis() - start;
		System.out.println("Laufzeit von Daten einlesen: " + end + "ms");

	    long sumRoute = 0;
	    for (int i = 2; i <= cities.getData().size(); i++) {
	        sumRoute = sumRoute + (i - 1);
	    }
	    double sumRouteDiv = (double) sumRoute / 20;
		
		start = System.currentTimeMillis();
		
		int routesAdded = 0;
		for (int i = 0; i < cities.getData().size(); i++) {
			for (int j = i + 1; j < cities.getData().size(); j++) {
				routesAdded++;
				
				if(routesAdded >= sumRouteDiv) {
					routesAdded = 0;
					
					double progress = ((double) routes.size()) / sumRoute / 10 + 0.05;
					progressBar.setProgress(progress);
				}
				
				routes.add(new Route(cities.getData().get(i), cities.getData().get(j)));
			}
		}
		
		end = System.currentTimeMillis() - start;
		System.out.println("Laufzeit von Routen erstellen: " + end + "ms");
	}

	/**
	 * Sortiert alle gegebenen Routen.
	 */
	public void sortRoutes() {
		Route[] routeArray = routes.toArray(new Route[0]);
		quickSort.sort(routeArray);
		routes = Arrays.asList(routeArray);
	}
	
	/**
	 * Berechnet den TSP-Algorithmus für die gegebenen Daten.
	 */
	public void calcTSP() {
		long start = System.currentTimeMillis();
		sortRoutes();
		long end = System.currentTimeMillis() - start;
		System.out.println("Laufzeit von Routen sortieren: " + end + "ms");
		progressBar.setProgress(0.7);
		
		tspRoutes = new ArrayList<Route>();

		int takenRoutes = 0;
		int takenRoutesTotal = 0;
		double routeDiv = (double) routes.size() / 40;
		start = System.currentTimeMillis();
		for (Route r : routes) {
			if (checkRoute(r)) {
				if ((edgeAmount.containsKey(r.getSecondData()) && edgeAmount.containsKey(r.getFirstData()))
						&& (cities.getData().size() > tspRoutes.size() + 1)) {
					tspRoutes.add(r);
					if (isCircle(r)) {
						tspRoutes.remove(r);
						continue;
					}
				} else {
					tspRoutes.add(r);
				}
				if (edgeAmount.putIfAbsent(r.getFirstData(), 1) != null)
					edgeAmount.put(r.getFirstData(), edgeAmount.get(r.getFirstData()) + 1);
				if (edgeAmount.putIfAbsent(r.getSecondData(), 1) != null)
					edgeAmount.put(r.getSecondData(), edgeAmount.get(r.getSecondData()) + 1);
			}
			
			takenRoutes++;
			takenRoutesTotal++;
			if(takenRoutes >= routeDiv) {
				takenRoutes = 0;
				progressBar.setProgress((double) takenRoutesTotal / routes.size() / 5 + 0.7);
			}
		}
		end = System.currentTimeMillis() - start;
		System.out.println("Laufzeit von kürzeste Routen entnehmen: " + end + "ms");
		
		start = System.currentTimeMillis();
		String name = cities.getName();
		cities = new XYChart.Series<Number, Number>();
		cities.setName(name);
		cities.getData().add(tspRoutes.get(0).getFirstData());
		
		addRoutes();
		
		end = System.currentTimeMillis() - start;
		
		System.out.println("Laufzeit von Routen zusammenbauen: " + end + "ms");
	}

	/**
	 * Gibt alle Städte der eingelesen Daten zurück.
	 *
	 * @return
	 * 	Städte im Format XYChart.Series
	 */
	public XYChart.Series<Number, Number> getCities() {
		return cities;
	}

	/**
	 * Gibt alle Routen des TSP-Algorithmus zurück.
	 *
	 * @return
	 */
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

		while (true) {
			currentR = getOtherNeighbor(currentData, currentR);

			if (currentR == r)
				return true;
			if (currentR == null)
				break;

			if (currentR.getFirstData() == currentData)
				currentData = currentR.getSecondData();
			else
				currentData = currentR.getFirstData();
		}

		return false;
	}

	private Route getOtherNeighbor(Data<Number, Number> data, Route oldRoute) {
		for (Route r : tspRoutes) {
			if (r != oldRoute) {
				if (r.getFirstData() == data || r.getSecondData() == data)
					return r;
			}
		}

		return null;
	}

	private void addRoutes() {
		Route r = tspRoutes.get(0);
		
		while(r != null) {
			if (!(cities.getData().contains(r.getFirstData()))) {
				cities.getData().add(r.getFirstData());
				
				for (Route nR : tspRoutes) {
					if (nR == r)
						continue;
					if (nR.getFirstData() == r.getFirstData() || nR.getSecondData() == r.getFirstData()) {
						r = nR;
						break;
					}
				}
			} else if (!(cities.getData().contains(r.getSecondData()))) {
				cities.getData().add(r.getSecondData());
				
				for (Route nR : tspRoutes) {
					if (nR == r)
						continue;
					if (nR.getFirstData() == r.getSecondData() || nR.getSecondData() == r.getSecondData()) {
						r = nR;
						break;
					}
				}
			} else {
				r = null;
			}
			
			progressBar.setProgress((double) cities.getData().size() / tspRoutes.size() / 10 + 0.9);
		}
	}

}
