package org.uni.algos.closest;

import java.util.*;

class ClosestPair {
    static class Point {
        double x, y;
        Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closestPair(Point[] points) {
        Arrays.sort(points, Comparator.comparingDouble(p -> p.x));
        return closestUtil(points, 0, points.length - 1);
    }

    private static double closestUtil(Point[] pts, int l, int r) {
        if (r - l <= 3) return bruteForce(pts, l, r);

        int mid = (l + r) / 2;
        double midX = pts[mid].x;

        double dLeft = closestUtil(pts, l, mid);
        double dRight = closestUtil(pts, mid + 1, r);
        double d = Math.min(dLeft, dRight);

        List<Point> strip = new ArrayList<>();
        for (int i = l; i <= r; i++) {
            if (Math.abs(pts[i].x - midX) < d) strip.add(pts[i]);
        }
        strip.sort(Comparator.comparingDouble(p -> p.y));

        return Math.min(d, stripClosest(strip, d));
    }

    private static double bruteForce(Point[] pts, int l, int r) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = l; i <= r; i++) {
            for (int j = i + 1; j <= r; j++) {
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        return min;
    }

    private static double stripClosest(List<Point> strip, double d) {
        double min = d;
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < min; j++) {
                min = Math.min(min, dist(strip.get(i), strip.get(j)));
            }
        }
        return min;
    }

    private static double dist(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }
}
