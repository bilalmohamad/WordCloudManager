package edu.ncsu.csc316.wordcloud.factory;

import edu.ncsu.csc316.dsa.map.SearchTableMap;
import edu.ncsu.csc316.dsa.map.UnorderedLinkedMap;

/**
 * Factory for creating new data structure and algorithm instances
 * 
 * @author Dr. King
 *
 */
public class DSAFactory {


	/**
	 * Returns an unordered linked map
	 * @param <K> key object of the map
	 * @param <V> value object of the map
	 * @return an unordered linked map
	 */
	public static <K, V> UnorderedLinkedMap<K, V> getUnorderedLinkedMap() {
		return new UnorderedLinkedMap<K, V>();
	}
	
	/**
	 * Returns a search table
	 * @param <K> key object of the map
	 * @param <V> value object of the map
	 * @return a search table
	 */
	public static <K extends Comparable<K>, V> SearchTableMap<K, V> getSearchTableMap() {
		return new SearchTableMap<K, V>();
	}


}
