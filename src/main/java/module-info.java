module z.code.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing;
    requires com.google.zxing.javase;
                            
    opens z.code.passwordmanager to javafx.fxml;
    exports z.code.passwordmanager;
}