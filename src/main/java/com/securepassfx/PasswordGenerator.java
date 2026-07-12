package com.securepassfx;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}<>?/";

    private PasswordGenerator() {
        // Prevent instantiation
    }

    public static String generate(
            int length,
            boolean includeUppercase,
            boolean includeLowercase,
            boolean includeNumbers,
            boolean includeSymbols
    ) {

        if (!includeUppercase &&
            !includeLowercase &&
            !includeNumbers &&
            !includeSymbols) {

            throw new IllegalArgumentException(
                    "Select at least one character type."
            );
        }

        // Must be long enough to include one of each selected type
        int requiredCharacters = 0;

        if (includeUppercase) requiredCharacters++;
        if (includeLowercase) requiredCharacters++;
        if (includeNumbers) requiredCharacters++;
        if (includeSymbols) requiredCharacters++;

        if (length < requiredCharacters) {
            length = requiredCharacters;
        }

        StringBuilder characterPool = new StringBuilder();

        List<Character> passwordCharacters = new ArrayList<>();

        if (includeUppercase) {
            characterPool.append(UPPERCASE);
            passwordCharacters.add(randomCharacter(UPPERCASE));
        }

        if (includeLowercase) {
            characterPool.append(LOWERCASE);
            passwordCharacters.add(randomCharacter(LOWERCASE));
        }

        if (includeNumbers) {
            characterPool.append(NUMBERS);
            passwordCharacters.add(randomCharacter(NUMBERS));
        }

        if (includeSymbols) {
            characterPool.append(SYMBOLS);
            passwordCharacters.add(randomCharacter(SYMBOLS));
        }

        while (passwordCharacters.size() < length) {

            passwordCharacters.add(
                    randomCharacter(characterPool.toString())
            );

        }

        Collections.shuffle(passwordCharacters, RANDOM);

        StringBuilder password = new StringBuilder();

        for (char c : passwordCharacters) {
            password.append(c);
        }

        return password.toString();

    }

    private static char randomCharacter(String source) {

        return source.charAt(
                RANDOM.nextInt(source.length())
        );

    }

}
