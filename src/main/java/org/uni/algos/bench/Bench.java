package org.uni.algos.bench;

import org.uni.algos.metrics.*;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Bench {
    public static void main(String[] args) throws IOException {
        FileWriter csv = new FileWriter("results.csv", false);
        csv.write("algo,n,time,comparisons,allocations,depth\n");

        for (int n = 1000; n <= 20000; n += 1000) {
            int[] arr = generateRandomArray(n);

            runSort("MergeSort", arr.clone(), csv, (a, m) -> new MergeSortMeasured(m).sort(a));

            runSort("QuickSort", arr.clone(), csv, (a, m) -> new QuickSortMeasured(m).sort(a));

            runSelect("Select", arr.clone(), csv, (a, m) -> new DeterministicSelectMeasured(m).select(a, a.length / 2));

            runClosest("ClosestPair", n, csv);
        }

        csv.close();
    }


    private static int[] generateRandomArray(int n) {
        Random rnd = new Random(42);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rnd.nextInt();
        return arr;
    }

    private interface AlgoRunner {
        void run(int[] arr, Metrics metrics);
    }

    private static void runSort(String algo, int[] arr, FileWriter csv, AlgoRunner runner) throws IOException {
        Metrics metrics = new Metrics();
        long start = System.nanoTime();
        runner.run(arr, metrics);
        long end = System.nanoTime();
        csv.write(String.format("%s,%d,%d,%d,%d,%d\n",
                algo, arr.length, (end - start), metrics.comparisons, metrics.allocations, metrics.maxDepth));
    }

    private interface SelectRunner {
        int run(int[] arr, Metrics metrics);
    }

    private static void runSelect(String algo, int[] arr, FileWriter csv, SelectRunner runner) throws IOException {
        Metrics metrics = new Metrics();
        long start = System.nanoTime();
        runner.run(arr, metrics);
        long end = System.nanoTime();
        csv.write(String.format("%s,%d,%d,%d,%d,%d\n",
                algo, arr.length, (end - start), metrics.comparisons, metrics.allocations, metrics.maxDepth));
    }

    private static void runClosest(String algo, int n, FileWriter csv) throws IOException {
        Random rnd = new Random(42);
        ClosestPairMeasured.Point[] pts = new ClosestPairMeasured.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPairMeasured.Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
        }

        Metrics metrics = new Metrics();
        ClosestPairMeasured cp = new ClosestPairMeasured(metrics);

        long start = System.nanoTime();
        cp.closestPair(pts);
        long end = System.nanoTime();

        csv.write(String.format("%s,%d,%d,%d,%d,%d\n",
                algo, n, (end - start), metrics.comparisons, metrics.allocations, metrics.maxDepth));
    }
}
