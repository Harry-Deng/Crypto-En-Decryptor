package system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import system.cipher.DH.DH_DES;

public class DHFrameController {

    @FXML
    private Stage DHStage;

    public Stage getDHStage() {
        return this.DHStage;
    }

    public void setDHStage(Stage stage) {
        this.DHStage = stage;
    }


    @FXML
    private Label exitButton;

    @FXML
    private TextArea publicKey1;

    @FXML
    private TextArea privateKey1;

    @FXML
    private TextArea localKey1;

    @FXML
    private TextArea send1;

    @FXML
    private TextArea ciphertext1;

    @FXML
    private Button bt_encrypt1;

    @FXML
    private TextArea plaintext1;

    @FXML
    private Button bt_decrypt1;

    @FXML
    private TextArea publicKey2;

    @FXML
    private TextArea privateKey2;

    @FXML
    private TextArea localKey2;

    @FXML
    private TextArea send2;

    @FXML
    private TextArea ciphertext2;

    @FXML
    private Button bt_encrypt2;

    @FXML
    private TextArea plaintext2;

    @FXML
    private Button bt_decrypt2;

    @FXML
    private void initialize() throws Exception {
        publicKey1.setText(DH_DES.getPuKey1());
        publicKey2.setText(DH_DES.getPuKey2());
        privateKey1.setText(DH_DES.getPrKey1());
        privateKey2.setText(DH_DES.getPrKey2());
        localKey1.setText(DH_DES.getKey1());
        localKey2.setText(DH_DES.getKey2());
    }

    @FXML
    void bt_encrypt1Event(ActionEvent event) throws Exception {
        ciphertext1.setText(DH_DES.cipher1(send1.getText()));
    }

    @FXML
    void bt_encrypt2Event() throws Exception {
        ciphertext2.setText(DH_DES.cipher2(send2.getText()));
    }

    @FXML
    void bt_decrypt1Event() throws Exception {
        plaintext1.setText(DH_DES.decpher1(ciphertext1.getText()));
    }

    @FXML
    void bt_decrypt2Event() throws Exception {
        plaintext2.setText(DH_DES.decpher2(ciphertext2.getText()));
    }

    @FXML
    void close() {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }

}
