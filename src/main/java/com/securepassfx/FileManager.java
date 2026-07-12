package com.securepassfx;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

public final class FileManager {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private FileManager() {
    }

    //========================================================
    // SAVE
    //========================================================

    public static void saveTXT(List<String> passwords) {

        exportTXT(passwords);

    }

    //========================================================
    // EXPORT TXT
    //========================================================

    public static void exportTXT(List<String> passwords) {

        if (passwords == null || passwords.isEmpty()) {

            showAlert(
                    AlertType.WARNING,
                    "Nothing to Export",
                    "Generate at least one password first."
            );

            return;
        }

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Export Passwords");

        chooser.setInitialFileName(
                "Passwords_" +
                        FORMATTER.format(LocalDateTime.now()) +
                        ".txt"
        );

        chooser.getExtensionFilters().add(

                new FileChooser.ExtensionFilter(
                        "Text Files",
                        "*.txt"
                )

        );

        File file = chooser.showSaveDialog((Window) null);

        if (file == null)
            return;

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(file))) {

            writer.write("SecurePassFX");
            writer.newLine();

            writer.write("Generated: "
                    + LocalDateTime.now());

            writer.newLine();
            writer.newLine();

            for (String password : passwords) {

                writer.write(password);
                writer.newLine();

            }

            showSuccess(file);

        } catch (IOException e) {

            showError(e);

        }

    }

    //========================================================
    // EXPORT CSV
    //========================================================

    public static void exportCSV(List<String> passwords) {

        if (passwords == null || passwords.isEmpty()) {

            showAlert(
                    AlertType.WARNING,
                    "Nothing to Export",
                    "Generate at least one password first."
            );

            return;
        }

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Export CSV");

        chooser.setInitialFileName(
                "Passwords_" +
                        FORMATTER.format(LocalDateTime.now()) +
                        ".csv"
        );

        chooser.getExtensionFilters().add(

                new FileChooser.ExtensionFilter(
                        "CSV Files",
                        "*.csv"
                )

        );

        File file = chooser.showSaveDialog(null);

        if (file == null)
            return;

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(file))) {

            writer.write("Number,Password");
            writer.newLine();

            int count = 1;

            for (String password : passwords) {

                writer.write(count + ",\"" + password + "\"");

                writer.newLine();

                count++;

            }

            showSuccess(file);

        } catch (IOException e) {

            showError(e);

        }

    }

    //========================================================
    // EXPORT JSON
    //========================================================

    public static void exportJSON(List<String> passwords) {

        if (passwords == null || passwords.isEmpty()) {

            showAlert(
                    AlertType.WARNING,
                    "Nothing to Export",
                    "Generate at least one password first."
            );

            return;
        }

        FileChooser chooser = new FileChooser();

        chooser.setTitle("Export JSON");

        chooser.setInitialFileName(
                "Passwords_" +
                        FORMATTER.format(LocalDateTime.now()) +
                        ".json"
        );

        chooser.getExtensionFilters().add(

                new FileChooser.ExtensionFilter(
                        "JSON Files",
                        "*.json"
                )

        );

        File file = chooser.showSaveDialog(null);

        if (file == null)
            return;

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(file))) {

            writer.write("{");
            writer.newLine();

            writer.write("  \"generated\": \"" +
                    LocalDateTime.now() +
                    "\",");

            writer.newLine();

            writer.write("  \"passwords\": [");
            writer.newLine();

            for (int i = 0; i < passwords.size(); i++) {

                writer.write("    \"" +
                        passwords.get(i) +
                        "\"");

                if (i < passwords.size() - 1)
                    writer.write(",");

                writer.newLine();

            }

            writer.write("  ]");
            writer.newLine();

            writer.write("}");

            showSuccess(file);

        } catch (IOException e) {

            showError(e);

        }

    }

    //========================================================
    // SUCCESS
    //========================================================

    private static void showSuccess(File file) {

        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Export Successful");

        alert.setHeaderText("Passwords Saved");

        alert.setContentText(

                "Saved to:\n\n"

                        + file.getAbsolutePath()

        );

        alert.showAndWait();

    }

    //========================================================
    // ERROR
    //========================================================

    private static void showError(Exception e) {

        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle("Export Failed");

        alert.setHeaderText("Unable to save file");

        alert.setContentText(e.getMessage());

        alert.showAndWait();

    }

    //========================================================
    // GENERAL ALERT
    //========================================================

    private static void showAlert(
            AlertType type,
            String title,
            String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }

}
