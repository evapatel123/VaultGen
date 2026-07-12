package com.securepassfx;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

public class MainController {

    // ==========================
    // UI COMPONENTS
    // ==========================
    @FXML
    private Label strengthPercentLabel;
    
    @FXML
    private Label entropyLabel;
    
    @FXML
    private Label varietyLabel;
    
    @FXML
    private Label securityLabel;

    @FXML
    private Slider lengthSlider;

    @FXML
    private Label lengthLabel;

    @FXML
    private Spinner<Integer> amountSpinner;

    @FXML
    private CheckBox uppercaseCheck;

    @FXML
    private CheckBox lowercaseCheck;

    @FXML
    private CheckBox numbersCheck;

    @FXML
    private CheckBox symbolsCheck;

    @FXML
    private Button generateButton;

    @FXML
    private Button copyButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button exportTxtButton;

    @FXML
    private Button exportCsvButton;

    @FXML
    private Button exportJsonButton;

    @FXML
    private ToggleButton themeToggle;

    @FXML
    private ProgressBar strengthBar;

    @FXML
    private Label strengthLabel;

    @FXML
    private ListView<String> passwordList;

    @FXML
    private ListView<String> historyList;

    @FXML
    private TextField searchField;

    // ==========================

    private final ObservableList<String> passwords =
            FXCollections.observableArrayList();

    private final ObservableList<String> history =
            FXCollections.observableArrayList();

    // ==========================

    @FXML
    public void initialize() {

        passwordList.setItems(passwords);
        historyList.setItems(history);

        amountSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1,
                        100,
                        5
                )
        );

        updateLengthLabel();

        lengthSlider.valueProperty().addListener(
                (obs, oldValue, newValue) -> updateLengthLabel()
        );

        generateButton.setOnAction(e -> generatePasswords());

        copyButton.setOnAction(e -> copySelectedPassword());

        saveButton.setOnAction(e -> savePasswords());

        clearButton.setOnAction(e -> clearPasswords());

        exportTxtButton.setOnAction(e -> exportTXT());

        exportCsvButton.setOnAction(e -> exportCSV());

        exportJsonButton.setOnAction(e -> exportJSON());

        themeToggle.setOnAction(e -> toggleTheme());

        searchField.textProperty().addListener(
                (obs, oldText, newText) -> filterHistory(newText)
        );

        passwordList.setOnKeyPressed(event -> {

            if(event.getCode() == KeyCode.C && event.isControlDown())
                copySelectedPassword();

        });

    }

    // ==========================

    private void updateLengthLabel() {

        lengthLabel.setText(
                (int) lengthSlider.getValue() + " Characters"
        );

    }

    // ==========================

    private void generatePasswords() {

        passwords.clear();

        int amount = amountSpinner.getValue();

        for(int i = 0; i < amount; i++) {

            String password =
                    PasswordGenerator.generate(

                            (int) lengthSlider.getValue(),

                            uppercaseCheck.isSelected(),

                            lowercaseCheck.isSelected(),

                            numbersCheck.isSelected(),

                            symbolsCheck.isSelected()

                    );

            passwords.add(password);

            history.add(password);

        }

        updateStrength();

    }

    // ==========================

private void updateStrength() {

    if(passwords.isEmpty())
        return;

    String password = passwords.get(0);

    double score =
            PasswordStrength.calculateScore(password);

    strengthBar.setProgress(score);

    strengthLabel.setText(
            PasswordStrength.getLabel(score)
    );

    strengthPercentLabel.setText(
            PasswordStrength.getPercentage(score) + "%"
    );

    double entropy =
            PasswordStrength.calculateEntropy(password);

    entropyLabel.setText(
            String.format("%.1f bits", entropy)
    );

    varietyLabel.setText(
            PasswordStrength.getCharacterVariety(password)
                    + "/4"
    );

    securityLabel.setText(
            PasswordStrength.getEntropyLabel(entropy)
    );

}
    // ==========================

    private void copySelectedPassword() {

        String selected =
                passwordList.getSelectionModel().getSelectedItem();

        if(selected == null)
            return;

        ClipboardManager.copy(selected);

    }

    // ==========================

    private void savePasswords() {

        FileManager.saveTXT(passwords);

    }

    // ==========================

    private void exportTXT() {

        FileManager.exportTXT(passwords);

    }

    // ==========================

    private void exportCSV() {

        FileManager.exportCSV(passwords);

    }

    // ==========================

    private void exportJSON() {

        FileManager.exportJSON(passwords);

    }

    // ==========================

    private void clearPasswords() {

        passwords.clear();

        strengthBar.setProgress(0);

        strengthLabel.setText("Not Generated");

    }

    // ==========================

    private void toggleTheme() {

        if(themeToggle.isSelected()) {

            themeToggle.setText("☀ Light Mode");

        }

        else {

            themeToggle.setText(" Dark Mode");

        }

    }

    // ==========================

    private void filterHistory(String search) {

        if(search == null || search.isEmpty()) {

            historyList.setItems(history);

            return;

        }

        ObservableList<String> filtered =
                FXCollections.observableArrayList();

        for(String pass : history) {

            if(pass.toLowerCase().contains(search.toLowerCase()))
                filtered.add(pass);

        }

        historyList.setItems(filtered);

    }

}
