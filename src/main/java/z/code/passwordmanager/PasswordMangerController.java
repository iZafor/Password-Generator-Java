package z.code.passwordmanager;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        String password = PasswordUtility.generatePassword(digitsCount, upperCaseCount, lowerCaseCount, symbolsCount);
        passwordText.setText(password);
    }

    @FXML
    public void onGenerateQrcode() {
        if (!passwordText.getText().isEmpty()) {
            showSaveDialog((Stage) digitsSlider.getScene().getWindow());
        }
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

    private void showSaveDialog(Stage primaryStage) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        // dialog message
        Text message = new Text("Enter File Name");
        message.setLayoutX(135);
        message.setLayoutY(48);
        message.setFont(new Font(18));

        // input field
        TextField fileName = new TextField();
        fileName.setFont(new Font(16));
        fileName.setLayoutX(109);
        fileName.setLayoutY(79);
        fileName.setPrefWidth(183);
        fileName.setPrefHeight(30);

        // save button
        Button saveBtn = new Button("Save");
        saveBtn.setFont(new Font(18));
        saveBtn.setLayoutX(172);
        saveBtn.setLayoutY(150);
        saveBtn.setOnAction(actionEvent -> {
            if (!fileName.getText().isEmpty()) {
                dialog.close();
                generateQrCodeImg(fileName.getText());
            }
        });

        AnchorPane parent = new AnchorPane(message, fileName, saveBtn);
        Scene dialogScene = new Scene(parent, 400, 200);

        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void generateQrCodeImg(String fileName) {
        QrcodeManager.saveQrcodeImage(
                QrcodeManager.generateQrcodeBitMatrix(
                        passwordText.getText(),
                        "UTF-8", 200, 200
                ), "png", "src/main/images/" + fileName + ".png"
        );
    }
}