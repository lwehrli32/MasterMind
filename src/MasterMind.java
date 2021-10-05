
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (MasterMind)
// Files: (list of source files)
// Semester: CS 200 Spring 2018
//
// Author: (Lukas Wehrli)
// Email: (lwehrli@wisc.edu)
// CS Login: (wehrli)
// Lecturer's Name: (Jim Williams)
// Lab Section: (your lab section number)
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, roommates
// strangers, etc do.
//
// Persons: (identify each person and describe their help in detail)
// Online Sources: (identify each URL and describe its assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * MasterMind is supposed to test us on the class materials we have learned and
 * see if we have mastered them or not. We have to build test cases and call
 * other methods to try and build our game. It implements hints and guesses the next best answer.
 * 
 * 
 * @author Lukas Wehrli
 */
public class MasterMind {

	/**
	 * Prompts the user for a value by displaying prompt. Note: This method should
	 * not add a new line to the output of prompt.
	 *
	 * After prompting the user, the method will consume an entire line of input
	 * while reading an int. Leading whitespace is ignored. If the value read is
	 * between min and max (inclusive), that value is returned. Otherwise, output
	 * "Expected value between 0 and 10." where 0 and 10 are the values in the min
	 * and max parameters, respectively. Invalid input may be non-integer in which
	 * case the same error message is displayed and the user is prompted again.
	 * 
	 * Note: This is a general purpose method to prompt for, read and validate an
	 * int within the min and max. This method should be tested for any min and max.
	 *
	 * @param input  The Scanner instance to read from System.in.
	 * @param prompt Output to the user.
	 * @param min    The minimum acceptable int value (inclusive).
	 * @param min    The maximum acceptable int value (inclusive).
	 * @return Returns the value read from the user.
	 */
	public static int promptInt(Scanner input, String prompt, int min, int max) {
		int num = 0;
		boolean test = false;
		System.out.print(prompt);
		do {
			// System.out.print(prompt);
			if (!input.hasNextInt()) {
				System.out.println("Expected value between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + ".");
				System.out.print(prompt);
				input.nextLine();
			}
			if (input.hasNextInt()) {
				num = input.nextInt();
				input.nextLine();
				if (num <= max) {
					if (num >= min) {
						return num;
					}
				} else {
					System.out
							.println("Expected value between " + Integer.MIN_VALUE + " and " + Integer.MAX_VALUE + ".");
				}
			}
		} while (!test);
		return 0;
	}

	/**
	 * Returns the index within arr of the first occurrence of the specified
	 * character. If arr is null or 0 length then return -1. For all arrays, don't
	 * assume a length but use the array .length attribute.
	 * 
	 * @param arr The array to look through.
	 * @param ch  The character to look for.
	 * @return The index within the array of the first occurrence of the specified
	 *         character or -1 if the character is not found in the array.
	 */
	public static int indexOf(char[] arr, char ch) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == ch) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Generates the hidden code to be guessed by the user. The hidden code returned
	 * is an array of characters with length numPositions. The characters in the
	 * array are randomly chosen, in order starting at index 0, from the symbols
	 * array. rand.nextInt( symbols.length) is used to determine the index in the
	 * symbols array of each character in the code. For all arrays, don't assume a
	 * length but use the array .length attribute.
	 * 
	 * Example: if numPositions is 3 and symbols is the array {'A','B','C'} the
	 * returned array will have a length of 3 and may contain any selection of the
	 * available symbols such as {'B','C','B'} or {'C','A','B'}.
	 * 
	 * @param rand         A random number generator.
	 * @param numPositions The number of symbols in the code.
	 * @param symbols      The symbols to choose from.
	 * @return An array of length numPositions of randomly chosen symbols.
	 */
	public static char[] generateHiddenCode(Random rand, int numPositions, char[] symbols) {
		char[] characters = new char[numPositions];
		for (int i = 0; i < characters.length; ++i) {
			characters[i] = symbols[rand.nextInt(symbols.length)];
		}
		return characters;
	}

	/**
	 * Checks whether the code is the correct length and only contains valid
	 * symbols. Uses the indexOf method you wrote to check whether each character in
	 * the input is in the symbols array. If code or symbols are null then false is
	 * returned. For all arrays, don't assume a length but use the array .length
	 * attribute.
	 * 
	 * @param numPositions The required number of symbols in the code.
	 * @param symbols      The allowed symbols in the code.
	 * @param code         The code that is being checked.
	 * @return true if the code is the correct length and has only valid symbols
	 *         otherwise returns false.
	 */
	public static boolean isValidCode(int numPositions, char[] symbols, char[] code) {
		if (code == null || symbols == null) {
			return false;
		}
		if (code.length != numPositions) {
			System.out.println("Invalid code.");
			return false;
		}
		for (int i = 0; i < code.length; ++i) {
			if (indexOf(symbols, code[i]) == -1) {
				System.out.println("Invalid code.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Prompts the user for a string value by displaying prompt. Note: This method
	 * should not add a new line to the output of prompt.
	 *
	 * After prompting the user, the method will read an entire line of input and
	 * remove leading and trailing whitespace. If the line equals the single
	 * character '?' then return null. If the line is a valid code (determine with
	 * isValidCode) return the code, otherwise print "Invalid code." and prompt
	 * again.
	 *
	 * @param input        The Scanner instance to read from System.in
	 * @param prompt       The user prompt.
	 * @param numPositions The number of code positions.
	 * @param symbols      The valid symbols.
	 * @return Returns null or a valid code.
	 */
	public static char[] promptForGuess(Scanner input, String prompt, int numPositions, char[] symbols) {
		boolean test = false;
		do {
			System.out.print(prompt);
			String user = input.nextLine();
			char[] character = new char[user.length()];

			for (int i = 0; i < user.length(); ++i) {
				character[i] = user.charAt(i);
			}
			if (user.equals("?")) {
				return null;
			} else if (isValidCode(numPositions, symbols, character)) {
				test = true;
				return character;
			}
		} while (!test);

		return null;
	}

	/**
	 * Returns the sum of "black hits" and "white hits" between the hiddenCode and
	 * guess. A "black hit" indicates a matching symbol in the same position in the
	 * hiddenCode and guess. A "white hit" indicates a matching symbol but different
	 * position in the hiddenCode and guess that is not already accounted for with
	 * other hits.
	 * 
	 * Algorithm to determine the total number of hits:
	 * 
	 * Count the number of each symbol in the hiddenCode, and separately count the
	 * number of each symbol in the guess. For each symbol, determine the minimum of
	 * the count of that symbol in the hiddenCode and the count of that symbol found
	 * in the guess. The total number of hits, black and white, is the sum of these
	 * minimums for all the symbols.
	 * 
	 * Algorithm based on Donald Knuth, 1976, The Computer As Master Mind, J.
	 * Recreational Mathematics, Vol. 9(1)
	 * 
	 * Suggestion: To do the count, create an array of int the length of the number
	 * of symbols. For each symbol use the indexOf method you wrote to determine the
	 * index in the array to increment the symbols count.
	 * 
	 * @param hiddenCode The code hidden from the user.
	 * @param guess      The user's guess of the code.
	 * @param symbols    The possible symbols in the hiddenCode and guess.
	 * @return The total number of hits.
	 */
	public static int countAllHits(char[] hiddenCode, char[] guess, char[] symbols) {
		int[] countHiddenCode = new int[symbols.length];
		int sumOfMin = 0;
		int i;
		for (i = 0; i < hiddenCode.length; i++) {
			int index = indexOf(symbols, hiddenCode[i]);
			countHiddenCode[index]++;
		}
		int[] countGuess = new int[symbols.length];
		for (int j = 0; j < guess.length; j++) {
			int index = indexOf(symbols, guess[j]);
			countGuess[index]++;
		}
		for (i = 0; i < symbols.length; i++) {
			sumOfMin += Math.min(countHiddenCode[i], countGuess[i]);
		}
		return sumOfMin;
	}

	/**
	 * Returns the number of each kind of hit the guess has with the code. The
	 * results are an array of length Config.HITS_ARRAY_LENGTH. The count of the
	 * number of symbols in the guess that correspond in position and symbol with
	 * the hidden code are recorded in the Config.BLACK_HITS_INDEX position within
	 * the result array. The number of symbols that match between the guess and the
	 * hidden code but are in different positions and not otherwise counted are
	 * recorded in the Config.WHITE_HITS_INDEX within the result array.
	 * 
	 * Algorithm: Count the number of black hits - the number of positions in the
	 * guess and hidden code that have the same symbol. Count the total number of
	 * hits using countAllHits and subtract to find the number of white hits. White
	 * hits are symbols that match between guess and hiddenCode that are not in the
	 * same position and not already accounted for with other hits.
	 * 
	 * @param hiddenCode The code the user is trying to guess.
	 * @param guess      The user's guess.
	 * @param symbols    The possible symbols in the hiddenCode and guess.
	 * @return The array containing the number of "black hits" and "white hits".
	 */
	public static int[] determineHits(char[] hiddenCode, char[] guess, char[] symbols) {
		int i;
		int[] blackAndWhite = new int[Config.HITS_ARRAY_LENGTH];

		for (i = 0; i < hiddenCode.length; i++) {
			if (hiddenCode[i] == guess[i]) {
				blackAndWhite[Config.BLACK_HITS_INDEX] += 1;
			}
		}
		int whiteHits = countAllHits(hiddenCode, guess, symbols) - blackAndWhite[Config.BLACK_HITS_INDEX];
		blackAndWhite[Config.WHITE_HITS_INDEX] = whiteHits;
		return blackAndWhite;
	}

	/**
	 * Prints out the game board showing the guesses and the corresponding hits. See
	 * output examples. Game board example: 6) [4, 5, 2, 4] BBBB 5) [4, 4, 2, 5]
	 * BBWW 4) [4, 4, 2, 4] BBB 3) [1, 3, 3, 3] 2) [2, 3, 3, 3] W 1) [1, 1, 2, 2] B
	 * 
	 * Only rows with non-null guesses are shown. The number on the left is the
	 * guess, so the guesses are shown from last to first. Looking at one line in
	 * detail: 5) [4, 4, 2, 5] BBWW ^^ 2 white hits, the 2nd 4 and 5 (we don't know
	 * which until solved) ^^ 2 black hits, the 1st 4 and 2 (we don't know which
	 * until solved) ^^^^^^^^^^^^ the guess output using Arrays.toString() ^^ the
	 * guess number The symbols B and W are the characters from
	 * Config.BLACK_HIT_SYMBOL and Config.WHITE_HIT_SYMBOL. All the black hits will
	 * be shown before the white hits. The length of all arrays should be determined
	 * using the array .length attribute, not assumed from a constant.
	 * 
	 * @param guesses The array of guesses. Each row is a guess or null (meaning no
	 *                guess yet).
	 * @param hits    The array of hits. Each row is the hits from determineHits for
	 *                the corresponding guess in the parallel guesses array, or
	 *                null.
	 */
	public static void printBoard(char[][] guesses, int[][] hits) {
		int looper = 0;
		for (int i = guesses.length - 1; i >= 0; --i) {
			if (guesses[i] != null) {
				looper++;
			}
		}
		for (int i = guesses.length - 1; i >= 0; --i) {
			if (guesses[i] != null) {
				System.out.print(" ");
				// System.out.print("[" + looper + "]");
				System.out.print(looper + ") " + Arrays.toString(guesses[i]) + " ");
				for (int j = 0; j < hits[i].length; ++j) {
					for (int l = 0; l < hits[i][j]; l++) {
						if (j == Config.BLACK_HITS_INDEX) {
							System.out.print(Config.BLACK_HITS_SYMBOL);
						} else {
							System.out.print(Config.WHITE_HITS_SYMBOL);
						}
					}
				}
				System.out.println();
				looper--;
			}
		}
	}

	/**
	 * The MasterMind main method that contains the welcome and the main game loop.
	 * Carefully examine example output to help answer questions on prompts and how
	 * this program should work.
	 * 
	 * Algorithm: Use appropriate constants from Config. For example, to create an
	 * array use Config.MAX_GUESSES, but once an array exists don't use the
	 * constants but use the array .length attribute. Determine seed or not (call
	 * promptInt with Integer.MIN_VALUE, Integer.MAX_VALUE) Display welcome message.
	 * Generate the hidden code (call generateHiddenCode) Create 2D arrays for
	 * guesses and corresponding hits. Initially every row is null until guesses are
	 * made and hits are determined for a guess. (milestone 3) enumerate all the
	 * possibilities (call enumeratePossibilities) Loop Prompt for guess (call
	 * promptForGuess) (milestone 3) If guess is null then call computerGuess
	 * Determine how many black and white hits (call determineHits) Output the board
	 * (call printBoard) (milestone 3) Output number of remaining possibilities End
	 * loop when won or lost Output won or lost message.
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean win = false;
		Random rand;
		int hints = 0;
		int seed = promptInt(input, "Enter seed (0 for unrepeatable): ", Integer.MIN_VALUE, Integer.MAX_VALUE);
		rand = new Random(seed);
		System.out.println("Welcome to Master Mind!");
		System.out.println("I have a " + Config.CODE_POSITIONS + " symbol code using "
				+ Arrays.toString(Config.CODE_SYMBOLS) + ".");
		System.out.println("Can you guess my code within " + Config.MAX_GUESSES + " guesses?");
		char[] code = generateHiddenCode(rand, Config.CODE_POSITIONS, Config.CODE_SYMBOLS);
		char[][] guesses = new char[Config.MAX_GUESSES][];
		int[][] correspondingHits = new int[Config.MAX_GUESSES][];
		char[] guess = null;
		int[] hits = null;
		int maxGuesses = Config.MAX_GUESSES;
		int looper = 0;
		char [][] possibilities = enumeratePossibilities(Config.CODE_POSITIONS, Config.CODE_SYMBOLS);
		for (int i = 0; i < maxGuesses; i++) {
			guess = promptForGuess(input, "Enter guess (? for help): ", Config.CODE_POSITIONS, Config.CODE_SYMBOLS);
			if (guess == null) {
				guess = computerGuess(possibilities);
				hints = hints + 1;
			}
			guesses[i] = guess;
			hits = determineHits(code, guess, Config.CODE_SYMBOLS);
			correspondingHits[i] = hits;
			printBoard(guesses, correspondingHits);
			System.out.println("remaining possibilities: " + updatePossibilities(guess, correspondingHits[i],
					possibilities, Config.CODE_SYMBOLS));
			if (guesses[i] != null) {
				looper++;
			}
			if (Arrays.equals(guess, code)) {
				win = true;
				System.out.println("Congratulations! You guessed code with only " + looper + " guesses and " + hints + " hints!");
				break;
			}
		}
		if (!win) {
			System.out.println("You lost, the code was: " + Arrays.toString(code));
		}

	}

	/**
	 * Determine the next code in sequence given the ordered symbols and a code. See
	 * MasterMindTests.testNextCode() method for various test cases. Most
	 * significant positions are t he left most, just like for a number such as
	 * 1234, where 1 is the most significant digit.
	 * 
	 * Consider how you would add 1 to 199. Work out on paper. Now try with the
	 * symbols A, B, C in that order. If you added B to BC what would the result be?
	 * CA?
	 * 
	 * Algorithm: Loop from least significant position to the most significant Find
	 * the index of the symbol if least significant position if last symbol then set
	 * to first symbol and carry else set next symbol else if carry and last symbol
	 * set to first symbol and keep carry set else if carry and not last symbol set
	 * next symbol, clear carry else no carry, so keep symbol end if end if End loop
	 * If carry then prepend an additional symbol. Since, in decimal, leading 0's
	 * are assumed then we assume the same for any symbols. So, we would prepend the
	 * 2nd symbol, in decimal a 1.
	 * 
	 * @param code    A code with the symbols.
	 * @param symbols The symbols to use for the code.
	 * @return The next code in the sequence based on the order of the symbols.
	 */
	public static char[] nextCode(char[] code, char[] symbols) {
		boolean carry = false;
		char[] replacedCode = new char[code.length];
		for (int i = 0; i < code.length; ++i) {
			replacedCode[i] = code[i];
		}
		for (int i = replacedCode.length - 1; i >= 0; --i) {
			int k = indexOf(symbols, code[i]);
			if (i == replacedCode.length - 1) {
				if (k == symbols.length - 1) {
					replacedCode[i] = symbols[0];
					carry = true;
				} else {
					replacedCode[i] = symbols[k + 1];
				}
			} else {
				if (carry && k == symbols.length - 1) {
					replacedCode[i] = symbols[0];
				} else if (carry && k != symbols.length - 1) {
					carry = false;
					replacedCode[i] = symbols[k + 1];
				}
			}
		}
		if (carry) {
			char[] newCode = new char[replacedCode.length + 1];
			newCode[0] = symbols[1];
			for (int i = 0; i < code.length; ++i) {
				newCode[i + 1] = replacedCode[i];
			}
			return newCode;
		}
		return replacedCode;

	}

	/**
	 * List all the possibilities (permutations) of codes for the specified number
	 * of positions and the provided codes.
	 * 
	 * Algorithm: Create an array the length being the number of possibilities
	 * (permutations). For example, 3 symbols in each of 4 positions means there are
	 * 3*3*3*3 or 3^4 which equals 81 permutations. Determine the "first" code (all
	 * positions having the same first symbol). For each permutation call nextCode
	 * to generate the next code from the current.
	 * 
	 * If numPositions is less than or equal to 0 or symbols is 0 length or null
	 * then return null.
	 * 
	 * @param numPositions The number of positions in a code.
	 * @param symbols      The possible symbols used in a code.
	 * @return An array of all the possible codes that can be generated from the
	 *         symbols for the numPositions.
	 */
	public static char[][] enumeratePossibilities(int numPositions, char[] symbols) {
		int possibilities = 1;
		if (numPositions <= 0 || symbols.length == 0 || symbols == null) {
			return null;
		}
		for (int i = 0; i < numPositions; ++i) {
			possibilities = possibilities * symbols.length;
		}
		char[][] numPos = new char[possibilities][];
		numPos[0] = new char[numPositions];
		for (int i = 0; i < numPositions; ++i) {
			numPos[0][i] = symbols[0];
		}
		for (int i = 1; i < possibilities; ++i) {
			numPos[i] = nextCode(numPos[i - 1], symbols);
		}
		return numPos;

	}

	/**
	 * Updates the remaining possibilities array and returns the number of
	 * possibilities. The hiddenCodeHits parameter contains the black and white hits
	 * when the guess is compared to the code. The possibilities parameter contains
	 * all the possible remaining candidates for the hidden code. Determine the hits
	 * for each non-null guess when compared to each possibility and only keep the
	 * possibilities that match the result parameter hits. Remove a possibility from
	 * the array of possibilities by setting it to null.
	 * 
	 * @param guess          The current guess
	 * @param hiddenCodeHits The hits when guess is compared to hiddenCode.
	 * @param possibilities  The remaining codes that contain the hidden code.
	 * @param symbols        The potential symbols in the codes.
	 * @return The number of remaining possibilities.
	 */
	public static int updatePossibilities(char[] guess, int[] hiddenCodeHits, char[][] possibilities, char[] symbols) {
		int numOfPos = 0;
		int[] totalHits = new int[hiddenCodeHits.length];
		if (guess != null) {
			for (int i = 0; i < possibilities.length; ++i) {
				if (possibilities[i] == null) {
					continue;
				}
				totalHits = determineHits(possibilities[i], guess, symbols);
				if (!Arrays.equals(totalHits, hiddenCodeHits)) {
					possibilities[i] = null;
				} else {
					numOfPos = numOfPos + 1;
				}
			}
		}
		return numOfPos;
	}

	/**
	 * Select the first remaining code (lowest index) from possibilities. If no
	 * codes are left then return null.
	 * 
	 * @param possibilities The array of remaining possible codes.
	 * @return A code from the array.
	 */
	public static char[] computerGuess(char[][] possibilities) {

		for (int i = 0; i < possibilities.length; ++i) {
			if (possibilities[i] != null) {
				return possibilities[i];
			}
		}

		return null;
	}

}
