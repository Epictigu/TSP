package de.fhswf.ea.stp.utils;

import de.fhswf.ea.stp.data.Route;
import javafx.scene.control.ProgressBar;

/**
 * Algorithmus von:
 * https://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html#:~:text=Quicksort%20is%20a%20fast%2C%20recursive,O(n%2F2).
 */
public class QuickSort {

	private Route[] routes;
    private int curRoute;
    private ProgressBar progressBar;
	
	public QuickSort(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}
    
    public void sort(Route[] values) {
        if (values ==null || values.length==0){
            return;
        }
        this.routes = values;
        curRoute = values.length;
        quicksort(0, curRoute - 1, 0, 1);
    }
    
    private void quicksort(int low, int high, double progressCurrent, double progressMax) {
        int i = low, j = high;
        // Get the pivot element from the middle of the list
        Route pivot = routes[low + (high-low)/2];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller than the pivot
            // element then get the next element from the left list
            while (routes[i].compareTo(pivot) < 0) {
                i++;
            }
            // If the current value from the right list is larger than the pivot
            // element then get the next element from the right list
            while (routes[j].compareTo(pivot) > 0) {
                j--;
            }

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller than the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j)
            quicksort(low, j, progressCurrent, progressMax / 2);
        if (i < high)
            quicksort(i, high, progressCurrent + progressMax / 2, progressMax / 2);
        
        progressBar.setProgress(0.15 + 0.55 * (progressCurrent + progressMax));
    }

    private void exchange(int i, int j) {
        Route temp = routes[i];
        routes[i] = routes[j];
        routes[j] = temp;
    }
	
}
