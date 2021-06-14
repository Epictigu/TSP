package de.fhswf.ea.stp.components;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import de.fhswf.ea.stp.data.CountryData;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LocationChart extends ScatterChart<Number, Number> {

	private CountryData cD;
	private Stage primaryStage;
	private ProgressBar progressBar;
	
	public LocationChart(Axis<Number> x, Axis<Number> y, Stage primaryStage, ProgressBar progressBar) {
		super(x, y);
		
		this.primaryStage = primaryStage;
		this.progressBar = progressBar;
		
		getXAxis().setTickLabelsVisible(false);
		getYAxis().setTickLabelsVisible(false);
		
		setMinHeight(250D);
		setMinWidth(250D);
		
		setId("locationchart1");
	}

	public void setCountry(String path, Menu menuToDisable) throws FileNotFoundException {
		progressBar.setProgress(0.0);
		getData().clear();
		menuToDisable.setDisable(true);
		new Thread() {
			public void run() {
				cD = null;
				System.gc();
				
				CountryData cDNew = null;
				try {
					cDNew = new CountryData(path, progressBar);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return;
				}
				long start = System.currentTimeMillis();
				cDNew.calcTSP();
				long end = System.currentTimeMillis() - start;
				progressBar.setProgress(1.0);
				System.out.println("Laufzeit: " + end + "ms");
				
				Double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
				Double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
				for (XYChart.Data<Number, Number> data : cDNew.getCities().getData()) {
					if (data.getXValue().doubleValue() < minX)
						minX = data.getXValue().doubleValue();
					if (data.getXValue().doubleValue() > maxX)
						maxX = data.getXValue().doubleValue();

					if (data.getYValue().doubleValue() < minY)
						minY = data.getYValue().doubleValue();
					if (data.getYValue().doubleValue() > maxY)
						maxY = data.getYValue().doubleValue();
				}
				Double unitTickX = (maxX - minX) / 10;
				unitTickX = (double) Math.round(unitTickX);
				Double unitTickY = (maxY - minY) / 10;
				unitTickY = (double) Math.round(unitTickY);

				minX = (double) Math.round(minX);
				maxX = (double) Math.round(maxX);

				minY = (double) Math.round(minY);
				maxY = (double) Math.round(maxY);

				((NumberAxis) getXAxis()).setLowerBound(minX - unitTickX);
				((NumberAxis) getXAxis()).setUpperBound(maxX + unitTickX);
				((NumberAxis) getXAxis()).setTickUnit(unitTickX);

				((NumberAxis) getYAxis()).setLowerBound(minY - unitTickY);
				((NumberAxis) getYAxis()).setUpperBound(maxY + unitTickY);
				((NumberAxis) getYAxis()).setTickUnit(unitTickY);

				Double differenceX = maxX - minX;
				Double differenceY = maxY - minY;
				
				Rectangle2D screenBounds = Screen.getPrimary().getBounds();
				double mult = 0.1;
				final int size = cDNew.getCities().getData().size();
				if(size > 1000 ) {
					mult = ((double) size) / 10000;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							setId("locationchart1");
						}
					});
				} else {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							setId("locationchart2");
						}
					});
				}
				if(mult > 0.8) mult = 0.8;
				
				
				System.out.println(differenceX + " " + differenceY);
				
				double minScreenX, minScreenY;
				if (differenceX / screenBounds.getWidth() > differenceY / screenBounds.getHeight()) {
					minScreenX = screenBounds.getWidth() * mult;
					minScreenY = minScreenX * (differenceX / differenceY);
				} else {
					minScreenY = screenBounds.getHeight() * mult;
					minScreenX = minScreenY * (differenceX / differenceY);
				}

				setMinWidth(minScreenX);
				setMinHeight(minScreenY);
				
				final CountryData cDSync = cDNew;
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						cD = cDSync;
						getData().add(cD.getCities());
						
						primaryStage.sizeToScene();
						menuToDisable.setDisable(false);
					}
				});
			}
		}.start();
	}
	
	final List<Path> paths = new ArrayList<Path>();
	
	@Override
	protected void layoutPlotChildren() {
		super.layoutPlotChildren();
		getPlotChildren().removeAll(paths);
		
		if(cD != null) {
			Series<Number, Number> cities = cD.getCities();
			for(int i = 0; i < cities.getData().size(); i++) {
				Data<Number, Number> cur = cities.getData().get(i);
				Data<Number, Number> next;
				if(i == cities.getData().size() - 1)
					next = cities.getData().get(0);
				else
					next = cities.getData().get(i + 1);
				
				Bounds curBounds = cur.getNode().getBoundsInParent();
				Bounds nextBounds = next.getNode().getBoundsInParent();
				
				final Path path = new Path();
				MoveTo moveTo = new MoveTo();
				moveTo.setX(nextBounds.getMinX() + nextBounds.getWidth() / 2);
				moveTo.setY(nextBounds.getMinY() + nextBounds.getHeight() / 2);

				LineTo lineTo = new LineTo();
				lineTo.setX(curBounds.getMinX() + curBounds.getWidth() / 2);
				lineTo.setY(curBounds.getMinY() + curBounds.getHeight() / 2);

				path.getElements().add(moveTo);
				path.getElements().add(lineTo);
				
				getPlotChildren().add(path);
				paths.add(path);
			}
		}
		
	}

}
