import java.util.*;

public class Main {
	
	public final static String EXAMPLE_PWORD = "haS.6A";
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		//using getNumber method to get password length (MIN_CHARS - MAX_CHARS)
		String passwordLengthMessage = "Please enter the password length (" 
										+ Generator.MIN_CHARS + " - " 
										+ Generator.MAX_CHARS + "): ";
		int passwordLength = getNumber(input,
									   Generator.MIN_CHARS,
									   Generator.MAX_CHARS,
									   passwordLengthMessage);

		//using getNumber method to get number of pwords (0 - Integer.MAX_VALUE)
		String numPasswordsMessage = "Please enter the number of passwords: ";
		int numPasswords = getNumber(input, 0, Integer.MAX_VALUE, numPasswordsMessage);
		
		//generate all the desired passwords and print them
		Generator current = new Generator(passwordLength);
		String[] passwordsList = current.generateMany(numPasswords);
		System.out.println("\n" + numPasswords + " passwords of length " + passwordLength + " are:");
		for (String password : passwordsList) {
			System.out.println(password);
		}
		
		crackExamplePassword();
	}
	
	/**
	 * get number from console input and keep asking till number is not invalid
	 * @param input console scanner
	 * @param minValid minimum number that's valid
	 * @param maxValid maximum number that's valid
	 * @param message message to ask for the number
	 * @return the number
	 */
	public static int getNumber(Scanner input, int minValid, int maxValid, String message) {
		int number = minValid - 1;

		/*
		 * keep asking till valid number entered
		 * use parseInt to parse if invalid input entered
		 */
		while (!(minValid <= number && number <= maxValid)) {
			System.out.print(message);
			try {
				number = Integer.parseInt(input.nextLine().trim());
			} catch (Exception invalidNumber) {
				System.out.println();
			}
		}
		return number;
	}

	/**
	 * see how long it takes to crack the EXAMPLE_PWORD
	 * just as a proof of concept that longer password
	 * exponentially increases time (worst case) 
	 */
	public static void crackExamplePassword() {
		//verify all characters within range and length of password with range
		if (EXAMPLE_PWORD.length() < Generator.MIN_CHARS 
				|| EXAMPLE_PWORD.length() > Generator.MAX_CHARS) {
			return;
		}
		for (char character : EXAMPLE_PWORD.toCharArray()) {
			if (character > '~' || character < ' ') {
				return;
			}
		}
		System.out.println();
		
		
		//list of all generated passwords
		ArrayList<String> passwords = new ArrayList<String>();
		int repitions = 0;
		String generated = "";
		long startTime = System.currentTimeMillis();
		while (!generated.equals(EXAMPLE_PWORD)) {
			//new value for generated and check for repititions
			generated = generateRandPassword();
			if (!passwords.contains(generated)){
				passwords.add(generated);
			} else {
				repitions++;
			}
			
			//give statistics: elapsed time, passwords tested, number of repititions
			System.out.print("\r" + 
							(System.currentTimeMillis() - startTime) / 1000 
							+ " seconds elapsed and " + passwords.size() + " passwords tested with " + repitions + " repitions"
							+ "\tCurrently guessing: \"" + generated + "\" while actual is \"" + EXAMPLE_PWORD + "\"");
			
			//password has been "cracked"
			if (generated.equals(EXAMPLE_PWORD)) {
				break;
			}
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println("\nThe process took " + (endTime - startTime) / 1000 
							+ " seconds to guess " + EXAMPLE_PWORD);
		
	}

	/**
	 * generate a random password of set length each time trying to crack EXAMPLE_PWORD
	 * @return random password
	 */
	public static String generateRandPassword() {
		int lengthPword = EXAMPLE_PWORD.length();
		String output = "";
		
		/*
		 * allowed characters are between ' ' and '~'
		 * generate random character multiple times and add onto output string
		 * not using StringBuilder because difference will be negligible 
		 */
		
		for (int i = 0; i < lengthPword; i++) {
			output += (char) ((int)(Math.random() * ('~' - ' ' + 1) + ' '));
		}
		return output;
	}
}
