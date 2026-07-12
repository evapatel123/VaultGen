package com.securepassfx;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public final class ClipboardManager {

    // Prevent instantiation
    private ClipboardManager() {
    }

    /**
     * Copies the given text to the system clipboard.
     *
     * @param text The text to copy.
     * @return true if the text was copied successfully, false otherwise.
     */
    public static boolean copy(String text) {

        if (text == null || text.isBlank()) {
            return false;
        }

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();
        content.putString(text);

        return clipboard.setContent(content);
    }

    /**
     * Returns the current clipboard text.
     *
     * @return Clipboard text, or an empty string if none exists.
     */
    public static String getClipboardText() {

        Clipboard clipboard = Clipboard.getSystemClipboard();

        if (clipboard.hasString()) {
            return clipboard.getString();
        }

        return "";
    }

    /**
     * Checks whether the clipboard currently contains text.
     *
     * @return true if text exists.
     */
    public static boolean hasText() {
        return Clipboard.getSystemClipboard().hasString();
    }

    /**
     * Clears the clipboard.
     */
    public static void clear() {

        Clipboard clipboard = Clipboard.getSystemClipboard();

        ClipboardContent content = new ClipboardContent();
        content.putString("");

        clipboard.setContent(content);
    }
}
