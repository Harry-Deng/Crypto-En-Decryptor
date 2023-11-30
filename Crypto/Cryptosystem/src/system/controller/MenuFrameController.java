package system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import system.Main;

import java.io.IOException;

public class MenuFrameController {

    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    @FXML
    private Button bt_Single;

    @FXML
    private Label exitButton;

    @FXML
    void bt_SingleEvent(ActionEvent event) throws IOException {
           primaryStage.close();
           new Main().initEnDeFrame();
    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void exitBtnToRed(MouseEvent event){
        exitButton.setTextFill(Paint.valueOf("Red"));
    }

    @FXML
    public void exitBtnToPurple(MouseEvent event){
        exitButton.setTextFill(Paint.valueOf("#e2deff"));
    }

}
