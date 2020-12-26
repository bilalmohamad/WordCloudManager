package edu.ncsu.csc316.wordcloud.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
import edu.ncsu.csc316.dsa.queue.Queue;
import edu.ncsu.csc316.dsa.stack.Stack;
import edu.ncsu.csc316.dsa.wordcloud.io.InputFileReader;
import edu.ncsu.csc316.dsa.wordcloud.io.WordCloudGenerator;
import edu.ncsu.csc316.dsa.list.ArrayBasedList;
import edu.ncsu.csc316.wordcloud.factory.DSAFactory;

/**
 * The WordCloudManager processes input from txt files
 * and generates a word cloud based on the frequencies of
 * the words that appear in the input file. Optionally,
 * the program accepts an input txt file that contains
 * filter words that should be excluded from the frequency
 * analysis.
 * 
 * @author Andrew Shon
 * @author Bilal Mohamad
 *
 */
public class WordCloudManager {
	
	/** check to see if filter file is used **/
	private boolean filter;
	/** map of frequencies of all items **/
	private Map<String, Integer> frequencies;
	/** map of filter words **/
	private Map<String, String> filterWords;
	/** Map of sorted frequencies based on values **/
	private Map<String, Integer> sortedFrequencies;

	/**
	 * Constructs a new Word Cloud manager with the given
	 * input txt file and input filter file
	 * (See UC1)
	 * 
	 * @param pathToText - the path to the input text file
	 * @param pathToFilter - the path to the input filter file
	 * @throws FileNotFoundException if either file does not exist
	 */
	public WordCloudManager(String pathToText, String pathToFilter) throws FileNotFoundException {
		frequencies = loadFrequencies(InputFileReader.readFile(pathToText));
		filterWords = loadFilterWords(InputFileReader.readFilterFile(pathToFilter));
		filter = true;
	}
	
	/**
	 * Constructs a new Word Cloud manager with the given
	 * input txt file. No filter file is used.
	 * (See UC1)
	 * 
	 * @param pathToText path to input file
	 * @throws FileNotFoundException throws exception for file faulties
	 */
	public WordCloudManager(String pathToText) throws FileNotFoundException {
		frequencies = loadFrequencies(InputFileReader.readFile(pathToText));
		filter = false;
	}

	/**
	 * Loads the frequencies from file into map
	 * @param words stack of words
	 * @return map of frequencies
	 */
	private Map<String, Integer> loadFrequencies(Stack<String> words) {
		//filterWords
		//frequencies
	
		//Create a new Map that contains frequencies
		Map<String, Integer> newMap = DSAFactory.getSearchTableMap();
		
		while (!words.isEmpty()) {
			String current = words.pop().toLowerCase();
			int value = 1;
			
			if (newMap.get(current) == null) {
				newMap.put(current, value);
			}
			else {
				value = newMap.get(current);
				value++;
				newMap.put(current, value);
			}
		}
		
		return newMap;
	}

	/**
	 * Loads filter words into map
	 * @param words queue of filter words
	 * @return map of filter words
	 */
	@SuppressWarnings("static-access")
	private Map<String, String> loadFilterWords(Queue<String> words) {
		DSAFactory factory = new DSAFactory();
		Map<String, String> newMap = factory.getSearchTableMap();
		while(!words.isEmpty()) {
			String temp = words.dequeue();
			newMap.put(temp, temp);
		}
		return newMap;
	}
	
	/**
	 * Returns a frequency report for a given word.
	 * The frequency report is in the format:
	 *   "The word (w) is contained in the text X times."
	 * (See UC2)  
	 *   
	 * @param word - the word for which to return the frequency
	 * @return the frequency report
	 */
	public String getFrequencyOfWord(String word) {
		String lower = word.toLowerCase();
		
		/*if (!filter) {
			if (frequencies.get(lower) == null) {
				return "The word (" + word + ") is contained in the text "
						+ 0 + " times.";
			}
			return "The word (" + word + ") is contained in the text "
					+ frequencies.get(lower) + " times.";
		}*/
		
		if (filter && filterWords.get(lower) != null) {
				return "The word (" + word + ") is contained in the text "
						+ 0 + " times.";
			
		}
		
		if (frequencies.get(lower) == null) {
			return "The word (" + word + ") is contained in the text "
					+ 0 + " times.";
		}
		return "The word (" + word + ") is contained in the text "
				+ frequencies.get(lower) + " times.";
		
		
	}
	
	/**
	 * Returns a report of the top X most frequent words.
	 * (See UC3)
	 * 
	 * @param numberOfWords - the number of words to include in the report
	 * @return the report of the top X most frequent words in the input text
	 */
	public String getTopWordsReport(int numberOfWords)
	{
		if (numberOfWords == 0 || numberOfWords < 0) {
			return "MostFrequentWords[\n   Number of words must be greater than 0.\n]";
		}
	
		Map<String, Integer> copyMap = DSAFactory.getSearchTableMap();
		//copyMap filtered
		/*if (filter) {
			Iterable<Entry<String, Integer>> originalIt = frequencies.entrySet();
			for (Entry<String, Integer> item: originalIt) {
				if (filterWords.get(item.getKey()) == null) {
					copyMap.put(item.getKey(), item.getValue());
				}
			}
			
		}*/
		Iterable<Entry<String, Integer>> originalIt = frequencies.entrySet();
		for (Entry<String, Integer> item: originalIt) {
			if (filter) {
				if (filterWords.get(item.getKey()) == null) {
					copyMap.put(item.getKey(), item.getValue());
				}
			} else {
				copyMap.put(item.getKey(), item.getValue());
			}
		}
		
		
		ArrayBasedList<Entry<String, Integer>> reportWords = new ArrayBasedList<Entry<String, Integer>>();
		
		
		String output = "MostFrequentWords[\n";
		Entry<String, Integer> maxEntry = null;
		for (int i = 0; i < numberOfWords; i++) {
			int num = -1;
			Iterable<Entry<String, Integer>> it = copyMap.entrySet();
			for (Entry<String, Integer> item : it) {
						
				if (item.getValue() > num) {
					num = item.getValue();
					maxEntry = item;
				}
			}
			
			copyMap.remove(maxEntry.getKey());
			
			reportWords.addLast(maxEntry);
			if (copyMap.isEmpty()) {
				break;
			}
	
		}
		sortedFrequencies = DSAFactory.getUnorderedLinkedMap();
		
		for (int i = 0; i < reportWords.size(); i++) {
			
			output += "   ";
			output += reportWords.get(i).getKey() + " - " + reportWords.get(i).getValue() + "\n";
			sortedFrequencies.put(reportWords.get(i).getKey(), reportWords.get(i).getValue());
		}
		
		output += "]";
		
		return output;
        
	}
	
    /**
     * Sorts a map for values and outputs into html in output folder
     * @param num number of values to display
     * @throws IOException exception for I/O
     */
	public void outputWordCloud(int num) throws IOException {
		//Create html file
		if (num <= 0) {
			System.out.println("Number of words must be greater than 0");
			return;
		}
		
		FileWriter html = new FileWriter(new File("output/wordcloud.html"));
//		if (num >= frequencies.size()) {
//			html.write(WordCloudGenerator.getWordCloudHTML("WordCloudUI", frequencies));
//			System.out.println("Word Cloud Generated\n");	
//			html.close();
//			return;
//		}
		
		//copyMap filtered
		/*if (filter) {
			Iterable<Entry<String, Integer>> originalIt = frequencies.entrySet();
			for (Entry<String, Integer> item: originalIt) {
				if (filterWords.get(item.getKey()) == null) {
					copyMap.put(item.getKey(), item.getValue());
				}
			}
			
		}*/
		//addition of my changes
		Map<String, Integer> copyMap = DSAFactory.getSearchTableMap();
		Iterable<Entry<String, Integer>> originalIt = frequencies.entrySet();
		for (Entry<String, Integer> item: originalIt) {
			if (filter) {
				if (filterWords.get(item.getKey()) == null) {
					copyMap.put(item.getKey(), item.getValue());
				}
			} else {
				copyMap.put(item.getKey(), item.getValue());
			}
		}
		
		
		ArrayBasedList<Entry<String, Integer>> reportWords = new ArrayBasedList<Entry<String, Integer>>();
		
		
		Entry<String, Integer> maxEntry = null;
		for (int i = 0; i < num; i++) {
			int check = -1;
			Iterable<Entry<String, Integer>> it = copyMap.entrySet();
			for (Entry<String, Integer> item : it) {
						
				if (item.getValue() > check) {
					check = item.getValue();
					maxEntry = item;
				}
			}
			
			copyMap.remove(maxEntry.getKey());
			
			reportWords.addLast(maxEntry);
			if (copyMap.isEmpty()) {
				break;
			}
	
		}
		sortedFrequencies = DSAFactory.getUnorderedLinkedMap();
		
		for (int i = 0; i < reportWords.size(); i++) {
			sortedFrequencies.put(reportWords.get(i).getKey(), reportWords.get(i).getValue());
		}
		//end of my changes
		
		
//		Map<String, Integer> copyMap = DSAFactory.getSearchTableMap();
//		//copyMap filtered
//		if (filter) {
//			Iterable<Entry<String, Integer>> originalIt = frequencies.entrySet();
//			for (Entry<String, Integer> item: originalIt) {
//				if (filterWords.get(item.getKey()) == null) {
//					copyMap.put(item.getKey(), item.getValue());
//				}
//			}
//			
//		} else {
//			Iterable<Entry<String, Integer>> originalIt = frequencies.entrySet();
//			for (Entry<String, Integer> item: originalIt) {
//				copyMap.put(item.getKey(), item.getValue());
//			}
//		}
//		
//		
//		ArrayBasedList<Entry<String, Integer>> reportWords = new ArrayBasedList<Entry<String, Integer>>();
//		
//		
//		Entry<String, Integer> maxEntry = null;
//		for (int i = 0; i < num; i++) {
//			int check = -1;
//			Iterable<Entry<String, Integer>> it = copyMap.entrySet();
//			for (Entry<String, Integer> item : it) {
//						
//				if (item.getValue() > check) {
//					check = item.getValue();
//					maxEntry = item;
//				}
//			}
//			
//			copyMap.remove(maxEntry.getKey());
//			
//			reportWords.addLast(maxEntry);
//			if (copyMap.isEmpty()) {
//				break;
//			}
//	
//		}
//		sortedFrequencies = DSAFactory.getUnorderedLinkedMap();
//		
//		for (int i = 0; i < reportWords.size(); i++) {
//			sortedFrequencies.put(reportWords.get(i).getKey(), reportWords.get(i).getValue());
//		}
		
		html.write(WordCloudGenerator.getWordCloudHTML("WordCloudUI", sortedFrequencies));
		System.out.println("Word Cloud Generated\n");	
		html.close();

	}
}