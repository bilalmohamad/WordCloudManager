package edu.ncsu.csc316.wordcloud.ui;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import edu.ncsu.csc316.wordcloud.manager.WordCloudManager;

/**
 * Class that represents UI for the WordCloud application
 * @author Andrew Shon
 * @author Bilal Mohamad
 *
 */
public class WordCloudUI {
	
	/** Instance of the Word Cloud Manager **/
	private WordCloudManager manager;

	/**
	 * Constructs a new WordCloud User Interface and 
	 * prompts user through menu options
	 * @param pathToText path for input file
	 * @param pathToFilter path for filter file
	 * @throws IOException exception for I/O
	 */
	public WordCloudUI(String pathToText, String pathToFilter) throws IOException {
		
		if (pathToFilter == null) {
			manager = new WordCloudManager(pathToText);
		}
		else {
			manager = new WordCloudManager(pathToText, pathToFilter);
		}
		
		
		Scanner console = new Scanner(System.in);
		
		boolean isQuit = false;
		while(!isQuit || console.hasNext()) {
			prompt();
			String answer = console.next();
			if (answer.equals("q")) {
				System.out.println("Thank you, have a nice day!\n");
				System.exit(0);
			} else if (answer.equals("f")) {
				System.out.println("Enter in a word:\n");
				String answerTwo = console.next();
				System.out.println(manager.getFrequencyOfWord(answerTwo) + "\n");
			} else if (answer.equals("g")) {
				System.out.println("Enter number of words\n");
				int num = console.nextInt();
				System.out.println(manager.getTopWordsReport(num) + "\n");
			} else if (answer.equals("s")) {
				System.out.println("Enter number of words for word cloud\n");
				int numTwo = console.nextInt();
				manager.outputWordCloud(numTwo);
				
			}
		}
		
		console.close();
		
	}
	
	/**
	 * Menu screen that gives user word cloud functionalities
	 */
	private static void prompt() {
		System.out.println("Word Cloud!");
		System.out.println("Select an option");
		System.out.println("(F)requency of a word");
		System.out.println("(G)enerate report of most frequent words");
		System.out.println("(S)how word cloud");
		System.out.println("(Q)uit\n");
		
	}
	
	/**
	 * Starts the whole program by asking user for file inputs and then
	 * creates the UI
	 * @param args arguments that user could pass in
	 * @throws IOException exception for I/O
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		
		Scanner console = new Scanner(System.in);
		
		System.out.println("What is the input file? ");
		String pathToText = "input/" + console.next();
		File file = new File(pathToText);
		while(!file.exists()) {
			System.out.println("This is not a valid file. Try again\n");
			pathToText = "input/" + console.next();
			file = new File(pathToText);
		}
		
		System.out.println("Is there a filter file (Y/N)? ");
		String answer = console.next().toLowerCase();
		
		String pathToFilter = null;
		if (answer.equals("y")) {
			System.out.println("What is the filter file? ");
			pathToFilter = "input/" + console.next();
			File fileTwo = new File(pathToFilter);
			while(!fileTwo.exists()) {
				System.out.println("This is not a valid filter file. Try again\n");
				pathToFilter = "input/" + console.next();
				fileTwo = new File(pathToFilter);
			}
		}
		
		
		new WordCloudUI(pathToText, pathToFilter);
	}

}
