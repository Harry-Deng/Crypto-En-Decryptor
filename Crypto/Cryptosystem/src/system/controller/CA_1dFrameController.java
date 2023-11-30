package system.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import system.service.EncryptDecryptService;

public class CA_1dFrameController {

    private EncryptDecryptFrameController encryptDecryptFrameController;

    private FileEncryptViewController fileEncryptViewController;

    @FXML
    private Stage caStage;

    public Stage getCaStage() {
        return caStage;
    }

    public void setCaStage(Stage stage) {
        this.caStage = stage;
    }

    @FXML
    private TextField length;

    @FXML
    private TextField initialization;

    @FXML
    private TextField rule;

    @FXML
    private TextField random;

    @FXML
    private TextField plaintext;

    @FXML
    private Button okBT;

    @FXML
    private Label exitButton;

    @FXML
    void bt_okEvent() {
        this.caStage.close();
        this.encryptDecryptFrameController.setPlaintext1(plaintext.getText());
        this.encryptDecryptFrameController.setCiphertext1(EncryptDecryptService.CA_1dEncrypt(length, initialization, rule, random, plaintext));
        this.encryptDecryptFrameController.setKey1(EncryptDecryptService.getCA_key());
    }

    public void setParentController1(EncryptDecryptFrameController controller) {
        encryptDecryptFrameController = controller;
    }

    public void setParentController2(FileEncryptViewController controller) {
        fileEncryptViewController = controller;
    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }

}
