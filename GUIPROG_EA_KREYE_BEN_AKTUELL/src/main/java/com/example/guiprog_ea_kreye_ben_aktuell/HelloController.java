package com.example.guiprog_ea_kreye_ben_aktuell;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
//import vecmath

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}