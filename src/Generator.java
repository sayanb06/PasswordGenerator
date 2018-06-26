
public class Generator {
	/**
	 * length of password if not specified in constructor
	 */
	final int DEFAULT_LENGTH = 5;
	/**
	 * min number of characters in each password
	 */
	public static final int MIN_CHARS = 4;	
	/**
	 * max number of characters in each password
	 */
	public static final int MAX_CHARS = 40;	
	/**
	 * 1/NUM_MODULO is probability that for that single try, Math.random() is 0
	 */
	private final int NUM_MODULO = 104;
	/**
	 * ascii value of 32
	 */
	private final int LEAST_ASCII = ' ';
	/**
	 * ascii value of 126
	 */
	private final int LARGEST_ASCII = '~';
	
	private StringBuilder CURRENT_PASSWORD = new StringBuilder();
	/**
	 * length of each password
	 */
	private int lengthPasswords;	
	
	/**
	 * default constructor
	 */
	public Generator() {
		lengthPasswords = DEFAULT_LENGTH;
	}
	
	/**
	 * password generator initializer with certain number of characters 
	 * @param numChars number of characters
	 */
	public Generator(int numChars) {
		if (!changeLength(numChars)) {
			lengthPasswords = DEFAULT_LENGTH;
		}
	}
	
	/**
	 * change value of lengthPasswords if necessary
	 * @param numChars number of characters in new password
	 * @return whether change was successful
	 */
	public boolean changeLength(int numChars) {
		//if valid length, then change length
		if (numChars >= MIN_CHARS && numChars <= MAX_CHARS) {
			lengthPasswords = numChars;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * generate a single password
	 * @return that password
	 */
	public String generateOne() {
		
		char currentChar = singleCharGenerator();
		
		for (int index = 0; index < lengthPasswords; index++) {
			while ((int)(Math.random() * NUM_MODULO) != 0) {
				currentChar = singleCharGenerator();
			}
			CURRENT_PASSWORD.append(currentChar);
		}
		
		String finalPassword = new String(CURRENT_PASSWORD.toString());
		//clear buffer
		CURRENT_PASSWORD.setLength(0);
		return finalPassword;
	}
	
	/**
	 * generates characters between LEAST_ASCII and LARGEST_ASCII
	 * @return that character
	 */
	public char singleCharGenerator() {
		return (char) ((int)(Math.random() * (LARGEST_ASCII - LEAST_ASCII + 1)) + LEAST_ASCII);
	}

	/**
	 * generate many passwords
	 * @param numPasswords number of passwords
	 * @return the passwords array
	 */
	public String[] generateMany(int numPasswords) {
		if (numPasswords < 0) {
			return new String[] {generateOne()};
		}
		
		String[] passwords = new String[numPasswords];
		for (int index = 0; index < numPasswords; index++) {
			passwords[index] = generateOne();
		}
		return passwords;
	}
}
