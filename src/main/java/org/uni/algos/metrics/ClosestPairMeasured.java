package org.uni.algos.metrics;

import java.util.*;

public class ClosestPairMeasured {
    private final Metrics metrics;

    public ClosestPairMeasured(Metrics metrics) {
        this.metrics = metrics;
    }

    public double closestPair(Point[] points) {
        Arrays.sort(points, Comparator.comparingDouble(p -> p.x));
        metrics.incAllocations();
        return closestUtil(points, 0, points.length - 1);
    }

    private double closestUtil(Point[] pts, int l, int r) {
        metrics.enterRecursion();

        if (r - l <= 3) {
            double result = bruteForce(pts, l, r);
            metrics.exitRecursion();
            return result;
        }

        int mid = (l + r) / 2;
        double midX = pts[mid].x;

        double dLeft = closestUtil(pts, l, mid);
        double dRight = closestUtil(pts, mid + 1, r);
        double d = Math.min(dLeft, dRight);

        List<Point> strip = new ArrayList<>();
        for (int i = l; i <= r; i++) {
            metrics.incComparisons();
            if (Math.abs(pts[i].x - midX) < d) strip.add(pts[i]);
        }
        strip.sort(Comparator.comparingDouble(p -> p.y));
        metrics.incAllocations();

        double result = Math.min(d, stripClosest(strip, d));
        metrics.exitRecursion();
        return result;
    }

    private double bruteForce(Point[] pts, int l, int r) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = l; i <= r; i++) {
            for (int j = i + 1; j <= r; j++) {
                metrics.incComparisons();
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        return min;
    }

    private double stripClosest(List<Point> strip, double d) {
        double min = d;
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < min; j++) {
                metrics.incComparisons();
                min = Math.min(min, dist(strip.get(i), strip.get(j)));
            }
        }
        return min;
    }

    private double dist(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

    public static class Point {
        public double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }
}