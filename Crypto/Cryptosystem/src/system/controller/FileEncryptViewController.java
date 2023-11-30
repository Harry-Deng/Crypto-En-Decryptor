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
import system.utils.FileUtil;
import system.utils.SimpleUtil;

import java.io.IOException;

public class FileEncryptViewController {

    @FXML
    private Stage EnDeStage;

    public void setCaStage(Stage stage) {
        this.EnDeStage = stage;
    }

    @FXML
    private TextArea ciphertext2;

    @FXML
    private TextArea plaintext2;

    @FXML
    private TextArea key2;

    @FXML
    private Button bt_start2;

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
    private Button menu_main;

    @FXML
    private Button bt_enClient;

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
        algorithmObservableList.add("DH");
        algorithmObservableList.add("DH2");
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
            controller.setParentController2(this);
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
    void toMainView() throws Exception {
        this.close();
        new Main().initEnDeFrame();
    }

    @FXML
    void bt_deServerEvent(ActionEvent event) throws IOException {
        this.close();
        new Main().initDServerFrame();
    }

    @FXML
    void bt_start2Event() throws Exception{
        if (this.algorithmComboBox.getSelectionModel().getSelectedItem() == null) {
            this.simpleUtil.informationDialog(Alert.AlertType.ERROR, "提示", "警告", "请选择加密算法!");
        }
//        ciphertext2.setText("sfgrgrsfbojkgmsbws");
               EncryptDecryptService.isStart1(this.algorithmComboBox.getSelectionModel().getSelectedItem(), this.plaintext2, this.ciphertext2,
               this.key2, this.encryptBT, this.decryptBT);
    }


    @FXML
    void cFileEvent() throws IOException {
        ciphertext2.setText(FileUtil.initFileChooser(EnDeStage));
    }

    @FXML
    void kFileEvent() throws IOException {
        key2.setText(FileUtil.initFileChooser(EnDeStage));
    }

    @FXML
    void pFileEvent() throws IOException {
        plaintext2.setText(FileUtil.initFileChooser(EnDeStage));
    }


    @FXML
    void close() {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }
}
