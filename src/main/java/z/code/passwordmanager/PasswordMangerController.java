package z.code.passwordmanager;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;

public class PasswordMangerController {
    @FXML
    private Slider digitsSlider;
    @FXML
    private Slider upperCaseSlider;
    @FXML
    private Slider lowerCaseSlider;
    @FXML
    private Slider symbolsSlider;
    @FXML
    private Text digitsTxt;
    @FXML
    private Text uppersTxt;
    @FXML
    private Text lowersTxt;
    @FXML
    private Text symbolsTxt;
    @FXML
    private Text passwordText;

    private final PasswordManager passwordManager;
    private final Clipboard clipboard;

    private int digitsCount;
    private int upperCaseCount;
    private int lowerCaseCount;
    private int symbolsCount;

    public PasswordMangerController() {
        this.digitsCount = 2;
        this.upperCaseCount = 2;
        this.lowerCaseCount = 2;
        this.symbolsCount = 2;
        this.passwordManager = new PasswordManager();
        this.clipboard = Clipboard.getSystemClipboard();
    }

    private void updateValues() {
        digitsCount = (int) Math.floor(digitsSlider.getValue());
        upperCaseCount = (int) Math.floor(upperCaseSlider.getValue());
        lowerCaseCount = (int) Math.floor(lowerCaseSlider.getValue());
        symbolsCount = (int) Math.floor(symbolsSlider.getValue());
    }

    private void setSliderValues(boolean reset) {
        if (!reset) {
            digitsSlider.setValue(digitsCount);
            upperCaseSlider.setValue(upperCaseCount);
            lowerCaseSlider.setValue(lowerCaseCount);
            symbolsSlider.setValue(symbolsCount);
        } else {
            digitsSlider.setValue(2);
            upperCaseSlider.setValue(2);
            lowerCaseSlider.setValue(2);
            symbolsSlider.setValue(2);
        }
    }

    private void setTxtValues() {
        digitsTxt.setText(String.valueOf(digitsCount));
        uppersTxt.setText(String.valueOf(upperCaseCount));
        lowersTxt.setText(String.valueOf(lowerCaseCount));
        symbolsTxt.setText(String.valueOf(symbolsCount));
    }

    @FXML
    public void onValueChange() {
        updateValues();
        setSliderValues(false);
        setTxtValues();
    }

    @FXML
    public void onGenerate() {
        updateValues();
        String password = passwordManager.generatePassword(digitsCount, upperCaseCount, lowerCaseCount, symbolsCount);
        passwordText.setText(password);
    }

    @FXML
    public void onCopyToClipboard() {
        if (!passwordText.getText().isEmpty()) {
            ClipboardContent content = new ClipboardContent();
            content.putString(passwordText.getText());
            clipboard.setContent(content);
        }
    }

    @FXML
    public void onReset() {
        setSliderValues(true);
        updateValues();
        setTxtValues();
    }
}