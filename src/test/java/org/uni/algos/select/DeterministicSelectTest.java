package org.uni.algos.select;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeterministicSelectTest {

    @Test
    void testSimpleArray() {
        int[] arr = {7, 2, 9, 4, 1, 6, 3, 8, 5};
        assertEquals(5, DeterministicSelect.select(arr.clone(), 5));
        assertEquals(1, DeterministicSelect.select(arr.clone(), 1));
        assertEquals(9, DeterministicSelect.select(arr.clone(), 9));
    }

    @Test
    void testRandomArray() {
        int[] arr = new Random().ints(1000, -1000, 1000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        for (int k = 1; k <= 5; k++) {
            assertEquals(expected[k - 1], DeterministicSelect.select(arr.clone(), k));
        }
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        assertEquals(42, DeterministicSelect.select(arr, 1));
    }

    @Test
    void testInvalidInput() {
        int[] arr = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(arr, 0));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(arr, 4));
    }
}
