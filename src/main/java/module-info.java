module z.code.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens z.code.passwordmanager to javafx.fxml;
    exports z.code.passwordmanager;
}