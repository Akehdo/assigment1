package org.uni.algos.metrics;

import org.uni.algos.metrics.Metrics;

public class MergeSortMeasured {
    private final Metrics metrics;
    private static final int CUTOFF = 15;

    public MergeSortMeasured(Metrics metrics) {
        this.metrics = metrics;
    }

    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        int[] buffer = new int[arr.length];
        metrics.incAllocations();
        sort(arr, buffer, 0, arr.length - 1);
    }

    private void sort(int[] arr, int[] buffer, int left, int right) {
        metrics.enterRecursion();

        if (right - left + 1 <= CUTOFF) {
            insertionSort(arr, left, right);
            metrics.exitRecursion();
            return;
        }

        int mid = left + (right - left) / 2;
        sort(arr, buffer, left, mid);
        sort(arr, buffer, mid + 1, right);

        metrics.incComparisons();
        if (arr[mid] <= arr[mid + 1]) {
            metrics.exitRecursion();
            return;
        }

        merge(arr, buffer, left, mid, right);
        metrics.exitRecursion();
    }

    private void merge(int[] arr, int[] buffer, int left, int mid, int right) {
        System.arraycopy(arr, left, buffer, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.incComparisons();
            if (buffer[i] <= buffer[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            arr[k++] = buffer[i++];
        }
    }

    private void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                metrics.incComparisons();
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else break;
            }
            arr[j + 1] = key;
        }
    }
}
