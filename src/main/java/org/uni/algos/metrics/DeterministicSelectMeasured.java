package org.uni.algos.metrics;

import java.util.Arrays;

public class DeterministicSelectMeasured {
    private final Metrics metrics;

    public DeterministicSelectMeasured(Metrics metrics) {
        this.metrics = metrics;
    }

    public int select(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k < 1 || k > arr.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        return select(arr, 0, arr.length - 1, k - 1);
    }

    private int select(int[] arr, int left, int right, int k) {
        metrics.enterRecursion();
        while (true) {
            if (left == right) {
                metrics.exitRecursion();
                return arr[left];
            }

            int pivotIndex = medianOfMedians(arr, left, right);
            pivotIndex = partition(arr, left, right, pivotIndex);

            if (k == pivotIndex) {
                metrics.exitRecursion();
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            metrics.incComparisons();
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        swap(arr, right, storeIndex);
        return storeIndex;
    }

    private int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;
        if (n < 5) {
            Arrays.sort(arr, left, right + 1);
            metrics.incAllocations(); // считаем sort как аллокацию
            return left + n / 2;
        }

        int numMedians = (int) Math.ceil((double) n / 5);
        for (int i = 0; i < numMedians; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            Arrays.sort(arr, subLeft, subRight + 1);
            metrics.incAllocations();
            int median = subLeft + (subRight - subLeft) / 2;
            swap(arr, left + i, median);
        }

        return medianOfMedians(arr, left, left + numMedians - 1);
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
