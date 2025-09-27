package org.uni.algos.metrics;

import java.util.Random;

public class QuickSortMeasured {
    private final Metrics metrics;
    private final Random random = new Random();

    public QuickSortMeasured(Metrics metrics) {
        this.metrics = metrics;
    }

    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quicksort(arr, 0, arr.length - 1);
    }

    private void quicksort(int[] arr, int low, int high) {
        metrics.enterRecursion();

        while (low < high) {
            int pivotIndex = low + random.nextInt(high - low + 1);
            int pivot = arr[pivotIndex];

            int i = low, j = high;
            while (i <= j) {
                metrics.incComparisons();
                while (arr[i] < pivot) {
                    metrics.incComparisons();
                    i++;
                }
                metrics.incComparisons();
                while (arr[j] > pivot) {
                    metrics.incComparisons();
                    j--;
                }
                if (i <= j) {
                    swap(arr, i, j);
                    i++;
                    j--;
                }
            }

            if ((j - low) < (high - i)) {
                if (low < j) quicksort(arr, low, j);
                low = i;
            } else {
                if (i < high) quicksort(arr, i, high);
                high = j;
            }
        }

        metrics.exitRecursion();
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
