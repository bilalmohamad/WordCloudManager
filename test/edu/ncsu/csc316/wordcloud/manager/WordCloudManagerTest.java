package edu.ncsu.csc316.wordcloud.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

/**
 * This class tests the WordCloudManager
 * @author Andrew Shon
 * @author Bilal Mohamad
 *
 */
public class WordCloudManagerTest {

	/** path to input file **/
	public static final String TEST_FILE1 = "input/babyshark.txt";
	/** path to filter file **/
	public static final String TEST_FILTER = "input/filterBabyShark.txt";
	/** path to invalid file **/
	public static final String INVALID = "invalid.txt";
	
	/**
	 * Tests WordCloud with only input file
	 */
	@Test
	public void testWordCloudManager() {
		
		WordCloudManager wcm = null;
		
		try{
			wcm = new WordCloudManager(TEST_FILE1);
		}
		catch(FileNotFoundException e) {
			fail();
		}
		
		String freq = wcm.getFrequencyOfWord("baby");
		String expected = "The word (baby) is contained in the text 4 times.";
		
		assertEquals(expected, freq);
		
		String topWords = wcm.getTopWordsReport(2);
		expected = "MostFrequentWords[\n   do - 18\n   baby - 4\n]";
		
		assertEquals(expected, topWords);
	}
	
	/**
	 * Tests WordCloud with both input and filter files
	 */
	@Test
	public void testWordCloudManagerWithFilter() {
		
		WordCloudManager wcm = null;
		
		try{
			wcm = new WordCloudManager(TEST_FILE1, TEST_FILTER);
		}
		catch(FileNotFoundException e) {
			fail();
		}
		
		String topWords = wcm.getTopWordsReport(2);
		String expected = "MostFrequentWords[\n   baby - 4\n]";
		
		assertEquals(expected, topWords);
	}
	
	/**
	 * Tests html functionality of Word Cloud
	 * @throws IOException
	 */
	@Test
	public void testHTML() throws IOException {
		WordCloudManager wcm = null;
		
		try{
			wcm = new WordCloudManager(TEST_FILE1);
		}
		catch(FileNotFoundException e) {
			fail();
		}
		
		String freq = wcm.getFrequencyOfWord("baby");
		String expected = "The word (baby) is contained in the text 4 times.";
		
		assertEquals(expected, freq);
		
		String topWords = wcm.getTopWordsReport(2);
		expected = "MostFrequentWords[\n   do - 18\n   baby - 4\n]";
		
		assertEquals(expected, topWords);
		
		//Confirm three words show up
		wcm.outputWordCloud(3);
		//Confirm one word shows up
		wcm.outputWordCloud(1);
	}
}
