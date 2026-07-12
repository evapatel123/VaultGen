package com.securepassfx;

public class PasswordStrength {

    private PasswordStrength() {
        // Utility class
    }

    /**
     * Returns a score between 0.0 and 1.0
     * for use in the ProgressBar.
     */
    public static double calculateScore(String password) {

        if (password == null || password.isEmpty()) {
            return 0.0;
        }

        double score = 0.0;

        // Length contributes up to 40%
        score += Math.min(password.length(), 20) / 20.0 * 0.40;

        // Character types contribute 60%
        if (password.matches(".*[A-Z].*"))
            score += 0.15;

        if (password.matches(".*[a-z].*"))
            score += 0.15;

        if (password.matches(".*\\d.*"))
            score += 0.15;

        if (password.matches(".*[^A-Za-z0-9].*"))
            score += 0.15;

        return Math.min(score, 1.0);
    }

    /**
     * Returns the strength label.
     */
    public static String getLabel(double score) {

        if (score < 0.25)
            return "🔴 Very Weak";

        if (score < 0.50)
            return "🟠 Weak";

        if (score < 0.70)
            return "🟡 Medium";

        if (score < 0.90)
            return "🟢 Strong";

        return "💚 Very Strong";
    }

    /**
     * Calculates estimated entropy in bits.
     */
    public static double calculateEntropy(String password) {

        if (password == null || password.isEmpty())
            return 0;

        int pool = 0;

        if (password.matches(".*[a-z].*"))
            pool += 26;

        if (password.matches(".*[A-Z].*"))
            pool += 26;

        if (password.matches(".*\\d.*"))
            pool += 10;

        if (password.matches(".*[^A-Za-z0-9].*"))
            pool += 32;

        return password.length() * (Math.log(pool) / Math.log(2));
    }

    /**
     * Returns a user-friendly description of the entropy.
     */
    public static String getEntropyLabel(double entropy) {

        if (entropy < 40)
            return "Low Security";

        if (entropy < 60)
            return "Reasonable Security";

        if (entropy < 80)
            return "High Security";

        if (entropy < 100)
            return "Excellent Security";

        return "Exceptional Security";
    }

    /**
     * Returns a percentage (0–100)
     * for displaying strength if needed.
     */
    public static int getPercentage(double score) {

        return (int) Math.round(score * 100);

    }

    /**
     * Returns the number of character categories used.
     */
    public static int getCharacterVariety(String password) {

        int variety = 0;

        if (password.matches(".*[A-Z].*"))
            variety++;

        if (password.matches(".*[a-z].*"))
            variety++;

        if (password.matches(".*\\d.*"))
            variety++;

        if (password.matches(".*[^A-Za-z0-9].*"))
            variety++;

        return variety;
    }

}
