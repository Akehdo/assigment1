package org.uni.algos.closest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class ClosestPairTest {

    private ClosestPair.Point[] generatePoints(int n, long seed) {
        Random rnd = new Random(seed);
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
        }
        return pts;
    }

    // Наивное O(n^2) для проверки
    private static double bruteForce(ClosestPair.Point[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dist = Math.hypot(pts[i].x - pts[j].x, pts[i].y - pts[j].y);
                min = Math.min(min, dist);
            }
        }
        return min;
    }

    @Test
    void smallRandomMatchesBruteForce() {
        for (int n = 10; n <= 200; n += 50) {
            ClosestPair.Point[] pts = generatePoints(n, 42L);
            double expected = bruteForce(pts);
            double actual = ClosestPair.closestPair(pts);
            assertEquals(expected, actual, 1e-9, "Mismatch on n=" + n);
        }
    }

    @Test
    void mediumRandomMatchesBruteForce() {
        ClosestPair.Point[] pts = generatePoints(1000, 123L);
        double expected = bruteForce(pts);
        double actual = ClosestPair.closestPair(pts);
        assertEquals(expected, actual, 1e-9);
    }

    @Test
    void largeInputRunsFast() {
        ClosestPair.Point[] pts = generatePoints(100_000, 321L);
        double result = ClosestPair.closestPair(pts);
        assertTrue(result > 0, "Result must be positive");
    }
}
