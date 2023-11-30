package system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import system.Main;
import system.service.EncryptDecryptService;
import system.utils.SimpleUtil;

import java.io.IOException;

public class EncryptDecryptFrameController {

    @FXML
    private Stage EnDeStage;

    public void setEnDeStage(Stage stage) {
        this.EnDeStage = stage;
    }

    @FXML
    private TextArea plaintext1;

    @FXML
    private TextArea ciphertext1;

    @FXML
    private TextArea key1;

    @FXML
    private Button bt_start1;

    @FXML
    private RadioButton encryptBT;

    @FXML
    private RadioButton decryptBT;

    @FXML
    private Text algorithmText;

    @FXML
    private ImageView kFile;

    @FXML
    private ImageView cFile;

    @FXML
    private ImageView pFile;

    @FXML
    private ComboBox<String> algorithmComboBox;

    @FXML
    private Label exitButton;

    @FXML
    private Button bt_enClient;

    private FileEncryptViewController fileEncryptViewController;

    private SimpleUtil simpleUtil = new SimpleUtil();


    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        encryptBT.setToggleGroup(group);
        decryptBT.setToggleGroup(group);
        ObservableList<String> algorithmObservableList = FXCollections.observableArrayList();
        algorithmObservableList.add("Caesar");
        algorithmObservableList.add("Keyword");
        algorithmObservableList.add("Multiliteral");
        algorithmObservableList.add("Vigenere");
        algorithmObservableList.add("Autokey ciphertext");
        algorithmObservableList.add("Autokey plaintext");
        algorithmObservableList.add("Playfair");
        algorithmObservableList.add("Permutation");
        algorithmObservableList.add("Column permutation");
        algorithmObservableList.add("RC4");
        algorithmObservableList.add("CA_1d");
        algorithmObservableList.add("DES");
        algorithmObservableList.add("RSA");
        algorithmObservableList.add("DH2");
        algorithmObservableList.add("ECC");
        algorithmObservableList.add("MD5");
        algorithmObservableList.add("DH");
        algorithmComboBox.setItems(algorithmObservableList);
    }


    @FXML
    void bt_enClientEvent(ActionEvent event) throws IOException {
        this.close();
        new Main().initEClientFrame();
    }


    @FXML
    void algorithmEvent() throws Exception {
        String choice = this.algorithmComboBox.getSelectionModel().getSelectedItem();
        if (choice.equals("CA_1d")) {
            CA_1dFrameController controller = (CA_1dFrameController) Main.initCA_1dFrame();
            controller.setParentController1(this);
        }
        if (choice.equals("RSA")) {
            new Main().initRSAFrame();
        }
        if (choice.equals("DH")) {
            new Main().initDHFrame();
        }
        if (choice.equals("DH2")) {
            new Main().initDH2Frame();
        }
    }


    @FXML
    void bt_start1Event() {
        if (this.algorithmComboBox.getSelectionModel().getSelectedItem() == null) {
            this.simpleUtil.informationDialog(Alert.AlertType.ERROR, "提示",
                    "警告", "请选择加密算法!");
        }
        EncryptDecryptService.isStart1(this.algorithmComboBox.getSelectionModel().getSelectedItem(),
                this.plaintext1, this.ciphertext1, this.key1, this.encryptBT, this.decryptBT);
    }

    @FXML
    void bt_1Event() {
        if (this.algorithmComboBox.getSelectionModel().getSelectedItem() == null) {
            this.simpleUtil.informationDialog(Alert.AlertType.ERROR, "提示",
                    "警告", "请选择解密算法!");
        }
        EncryptDecryptService.isStart1(this.algorithmComboBox.getSelectionModel().getSelectedItem(),
                this.plaintext1, this.ciphertext1, this.key1, this.encryptBT, this.decryptBT);
    }

    @FXML
    void toFileView() throws Exception {
        this.close();
        new Main().initFileEncryptView();
    }

    @FXML
    void close() {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }

    public void setPlaintext1(String plaintext1) {
        this.plaintext1.setText(plaintext1);
    }

    public void setKey1(String key1) {
        this.key1.setText(key1);
    }

    public void setCiphertext1(String ciphertext1) {
        this.ciphertext1.setText(ciphertext1);
    }


}
