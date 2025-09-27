package org.uni.algos.bench;

import org.uni.algos.metrics.Metrics;

import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    private final FileWriter writer;

    public CsvWriter(String filename) throws IOException {
        writer = new FileWriter(filename, false);
        writer.write("algo,n,time,comparisons,allocations,depth\n");
    }

    public void log(String algo, int n, long time, Metrics m) throws IOException {
        writer.write(String.format("%s,%d,%d,%d,%d,%d\n",
                algo, n, time, m.comparisons, m.allocations, m.maxDepth));
        writer.flush();
    }
}

