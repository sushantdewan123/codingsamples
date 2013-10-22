package com.mymap;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedValueMap {

	private static Map<Integer, Integer> originalMap = new HashMap<Integer, Integer>();
	private static final int CEILING = 5;
	private static final int TOTAL_ELEM = 1000;
	private static final Random RANDOM = new Random();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Put random 1000 key-values in the map,
		// make sure you have lot of duplicates values
		for (int i = 0; i < TOTAL_ELEM; i++) {
			originalMap.put(i, RANDOM.nextInt(CEILING));
		}

		SortedSet<Entry<Integer, Integer>> sortedSet = new TreeSet<Entry<Integer, Integer>>(
				new Comparator<Entry<Integer, Integer>>() {

					@Override
					public int compare(Entry<Integer, Integer> entry1,
							Entry<Integer, Integer> entry2) {
						Integer val1 = entry1.getValue();
						Integer val2 = entry2.getValue();
						if (val1 == val2) {
							// This is important otherwise a set will drop
							// duplicate values !!
							return -1;
						}

						// Prints the entries in the ascending order of values
						return entry1.getValue().compareTo(entry2.getValue());
					}
				});

		sortedSet.addAll(originalMap.entrySet());

		// Make sure we have not dropped any entries
		System.out.println("Sorted Size : " + sortedSet.size());

		// Now print the entries
		for (Entry<Integer, Integer> entry : sortedSet) {
			System.out.print("Key : " + entry.getKey() + ", Value : "
					+ entry.getValue());
			System.out.println();
		}
	}
}
