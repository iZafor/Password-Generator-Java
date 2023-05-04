package z.code.passwordmanager;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.Objects;

public class PasswordMangerController {
    private final Clipboard clipboard;
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
        String password = PasswordManger.generatePassword(digitsCount, upperCaseCount, lowerCaseCount, symbolsCount);
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
            showSuccessNotification("Copied to Clipboard");
        }
    }

    @FXML
    public void onReset() {
        setSliderValues(true);
        updateValues();
        setTxtValues();
        showSuccessNotification("Reset Values");
    }

    private void showSaveDialog(Stage primaryStage) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        // dialog message
        Text message = new Text("Enter File Name");
        message.setLayoutX(135);
        message.setLayoutY(48);
        message.setFont(new Font(18));

        // input field for file name
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
                QrcodeManager.saveAsQrcodeImage(fileName.getText(), passwordText.getText());
                showSuccessNotification("Save Qrcode");
            }
        });

        AnchorPane parent = new AnchorPane(message, fileName, saveBtn);
        Scene dialogScene = new Scene(parent, 400, 200);

        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void showSuccessNotification(String title) {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("assets/tick.png")));

        Notifications.create()
                .title(title)
                .text("Successful")
                .graphic(new ImageView(img))
                .position(Pos.TOP_RIGHT)
                .hideCloseButton()
                .hideAfter(Duration.seconds(2)).show();
    }
}