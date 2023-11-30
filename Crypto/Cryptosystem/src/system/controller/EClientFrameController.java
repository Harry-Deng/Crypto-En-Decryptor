package system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import system.Main;
import system.service.EncryptDecryptService;
import system.utils.FileUtil;
import system.utils.SimpleUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class EClientFrameController {

    @FXML
    private ImageView bt_pFile2;

    @FXML
    private Label exitButton;

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private Stage clientStage;

    public void setClientStage(Stage stage) {
        this.clientStage = stage;
    }

    @FXML
    private ComboBox<String> algorithmComboBox;

    @FXML
    private TextField ip;

    @FXML
    private TextField c_txtInfo;

    @FXML
    private TextArea ePlaintext;

    @FXML
    private TextArea eKey;

    @FXML
    private TextArea eCiphertext;

    private SimpleUtil simpleUtil = new SimpleUtil();

    @FXML
    private void initialize() {
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
        algorithmObservableList.add("DH");
        algorithmComboBox.setItems(algorithmObservableList);
    }


    @FXML
    void toFileView() throws Exception {
        this.close();
        new Main().initFileEncryptView();
    }


    @FXML
    void bt_encryptEvent() {
        encryption();
    }

    private void encryption() {
        String choice = this.algorithmComboBox.getSelectionModel().getSelectedItem();
        if (choice != null) {
            if (!simpleUtil.isEmpty(ePlaintext.getText())) {
                if (!simpleUtil.isEmpty(eKey.getText())) {
                    eCiphertext.setText(EncryptDecryptService.EncryptService(choice, ePlaintext.getText(), eKey.getText()));
                } else
                    simpleUtil.informationDialog(Alert.AlertType.ERROR, "提示", "警告", "密钥不能为空！");
            } else
                simpleUtil.informationDialog(Alert.AlertType.ERROR, "提示", "警告", "明文不能为空！");
        } else
            this.simpleUtil.informationDialog(Alert.AlertType.ERROR, "提示", "警告", "请选择加密算法!");
    }

    @FXML
    void close() {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
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
    void bt_pFile1Event(ActionEvent event) throws IOException {
        ePlaintext.setText(FileUtil.initFileChooser(clientStage));
    }

    @FXML
    void bt_pFile2Event(ActionEvent event) throws IOException {
        eKey.setText(FileUtil.initFileChooser(clientStage));
    }


    private Socket client = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;

    @FXML
    void bt_connectEvent() throws IOException {
        String[] ipStr = ip.getText().split("\\.");
        byte[] ipAddressTemp = new byte[4];
        for (int i = 0; i < 4; i++) {
            ipAddressTemp[i] = (byte) (Integer.parseInt(ipStr[i]) & 0xff);
        }
        InetAddress ipAddress = InetAddress.getByAddress(ipAddressTemp);
        client = new Socket(ipAddress, 7788);
        out = new DataOutputStream(client.getOutputStream());
        in = new DataInputStream(client.getInputStream());
    }

    @FXML
    void bt_stopEvent() {
        try {//向服务器发送断开连接字符
            out.writeUTF("!stop connect!");
            out.close();
            in.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void sendEvent() throws IOException {
        //向服务器端发送信息
        out.writeUTF(this.algorithmComboBox.getSelectionModel().getSelectedItem());//算法
        out.writeUTF(this.eCiphertext.getText());//密文
        out.writeUTF(this.eKey.getText());//密钥
        // 读取服务器端信息
        c_txtInfo.appendText(in.readUTF());
    }

}
