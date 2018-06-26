import java.util.*;

public class Main {
	
	public final static String EXAMPLE_PWORD = "NOe:";
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int passwordLength = getNumber(input,
									   Generator.MIN_CHARS,
									   Generator.MAX_CHARS,
									   "Please enter the password length (" + Generator.MIN_CHARS + " - " + Generator.MAX_CHARS + "): ");
		int numPasswords = getNumber(input, 0, Integer.MAX_VALUE, "Please enter the number of passwords: ");
		
		Generator current = new Generator(passwordLength);
		String[] passwordsList = current.generateMany(numPasswords);
		
		System.out.println("\n" + numPasswords + " passwords of length " + passwordLength + " are : ");
		for (String password : passwordsList) {
			System.out.println(password);
		}
		
		crackExamplePassword();
	}
	
	public static int getNumber(Scanner input, int minValid, int maxValid, String message) {
		int number = minValid - 1;
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

	public static void crackExamplePassword() {
		//verify all characters right
		if (EXAMPLE_PWORD.length() < Generator.MIN_CHARS 
				|| EXAMPLE_PWORD.length() > Generator.MAX_CHARS) {
			return;
		}
		for (char character : EXAMPLE_PWORD.toCharArray()) {
			if (character > '~' || character < ' ') {
				return;
			}
		}
		
		long startTime = System.currentTimeMillis();
		Generator test = new Generator(EXAMPLE_PWORD.length());
		System.out.println();
		int numPasswordsTested = 0;
		while (!test.generateOne().equals(EXAMPLE_PWORD)) {
			System.out.print("\r" + (System.currentTimeMillis() - startTime) / 1000 
							+ " seconds elapsed and " + ++numPasswordsTested + " passwords tested");
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println("The process took " + (startTime - endTime) + " MS");
		
	}
}
