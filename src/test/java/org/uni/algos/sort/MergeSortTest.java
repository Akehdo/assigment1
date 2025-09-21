package org.uni.algos.sort;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest {

    @Test
    void testRandomArray() {
        int[] arr = new Random().ints(1000, -1000, 1000).toArray();
        int[] expected = arr.clone();

        MergeSort.sort(arr);
        Arrays.sort(expected);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = arr.clone();

        MergeSort.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testReversedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = arr.clone();

        MergeSort.sort(arr);
        Arrays.sort(expected);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        int[] expected = {};

        MergeSort.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        int[] expected = {42};

        MergeSort.sort(arr);

        assertArrayEquals(expected, arr);
    }
}
