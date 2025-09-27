package org.uni.algos.metrics;

public class Metrics {
    public long comparisons = 0;
    public long allocations = 0;
    public int currentDepth = 0;
    public int maxDepth = 0;

    public void incComparisons() {
        comparisons++;
    }
    public void incAllocations() {
        allocations++;
    }

    public void enterRecursion() {
        currentDepth++;
        maxDepth = Math.max(maxDepth, currentDepth);
    }

    public void exitRecursion() {
        currentDepth--;
    }
}

