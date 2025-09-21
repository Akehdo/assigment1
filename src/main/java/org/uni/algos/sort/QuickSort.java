package org.uni.algos.sort;

import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(int[] arr) {
        quicksort(arr, 0, arr.length - 1);
    }

    private static void quicksort(int[] arr, int low, int high) {
        while (low < high) {
            int pivotIndex = low + random.nextInt(high - low + 1);
            int pivot = arr[pivotIndex];

            int i = low, j = high;
            while (i <= j) {
                while (arr[i] < pivot) i++;
                while (arr[j] > pivot) j--;
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
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
