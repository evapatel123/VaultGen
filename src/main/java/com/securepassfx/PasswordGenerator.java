import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Scanner;

public class PasswordGenerator {

    // ANSI Colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}<>?/";

    private static final SecureRandom random = new SecureRandom();
    private static final ArrayList<String> history = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println(CYAN + "====================================");
        System.out.println("      JAVA PASSWORD GENERATOR");
        System.out.println("====================================" + RESET);

        System.out.print("Password length: ");
        int length = scanner.nextInt();

        System.out.print("How many passwords? ");
        int amount = scanner.nextInt();

        System.out.print("Include lowercase? (y/n): ");
        boolean lower = scanner.next().equalsIgnoreCase("y");

        System.out.print("Include uppercase? (y/n): ");
        boolean upper = scanner.next().equalsIgnoreCase("y");

        System.out.print("Include numbers? (y/n): ");
        boolean numbers = scanner.next().equalsIgnoreCase("y");

        System.out.print("Include symbols? (y/n): ");
        boolean symbols = scanner.next().equalsIgnoreCase("y");

        String characterPool = "";

        if (lower) characterPool += LOWER;
        if (upper) characterPool += UPPER;
        if (numbers) characterPool += NUMBERS;
        if (symbols) characterPool += SYMBOLS;

        if (characterPool.isEmpty()) {
            System.out.println(RED + "You must select at least one character type." + RESET);
            return;
        }

        System.out.println();

        for (int i = 1; i <= amount; i++) {

            String password = generatePassword(length, characterPool);

            history.add(password);

            System.out.println(GREEN + "Password #" + i + RESET);
            System.out.println(password);
            System.out.println("Strength: " + passwordStrength(password));
            System.out.println("----------------------------");
        }

        savePasswords();

        System.out.println(CYAN + "\nPassword History");
        System.out.println("----------------");

        for (String pass : history)
            System.out.println(pass);

        System.out.println("\nPasswords saved to passwords.txt");

        scanner.close();
    }

    private static String generatePassword(int length, String pool) {

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(pool.charAt(random.nextInt(pool.length())));
        }

        return password.toString();
    }

    private static String passwordStrength(String password) {

        int score = 0;

        if (password.length() >= 8)
            score++;

        if (password.length() >= 12)
            score++;

        if (password.matches(".*[A-Z].*"))
            score++;

        if (password.matches(".*[a-z].*"))
            score++;

        if (password.matches(".*\\d.*"))
            score++;

        if (password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}<>?/].*"))
            score++;

        if (score <= 2)
            return RED + "Weak" + RESET;

        if (score <= 4)
            return YELLOW + "Medium" + RESET;

        return GREEN + "Strong" + RESET;
    }

    private static void savePasswords() {

        try {

            FileWriter writer = new FileWriter("passwords.txt", true);

            writer.write("\nGenerated Passwords\n");
            writer.write("====================\n");

            for (String password : history)
                writer.write(password + "\n");

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving file.");

        }

    }
}
