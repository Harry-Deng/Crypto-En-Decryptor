package system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import system.cipher.RSA.RSA;
import system.cipher.RSA.RSASimple;
import system.utils.SimpleUtil;

import java.io.IOException;

public class RSAFrameController {
    @FXML
    private Label exitButton;
    @FXML
    void close(
            MouseEvent event) {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private Stage RSAStage;

    public void setRSAStage(Stage stage) {
        this.RSAStage = stage;
    }

    @FXML
    private Text text1;

    @FXML
    private TextArea textArea1;

    @FXML
    private Text text2;

    @FXML
    private TextArea textArea2;

    @FXML
    private Text text3;

    @FXML
    private TextArea textArea3;

    @FXML
    private TextField p;

    @FXML
    private TextField q;

    @FXML
    private TextField d;

    @FXML
    private TextArea plaintext;

    @FXML
    private TextArea encryption;

    @FXML
    private Button bt_encrypt;

    @FXML
    private TextArea decryption;

    @FXML
    private Button bt_decrypt;

    @FXML
    private Button bt_back;

    @FXML
    private ComboBox<String> RSAChoice;

    SimpleUtil simpleUtil = new SimpleUtil();

    @FXML
    private void initialize() {
        ObservableList<String> choiceObservableList = FXCollections.observableArrayList();
        choiceObservableList.add("��Կ����˽Կ���ܹ���");
        choiceObservableList.add("˽Կ���ܹ�Կ���ܹ���");
        choiceObservableList.add("˽Կǩ������");
        choiceObservableList.add("��ԿУ��ǩ��");
        RSAChoice.setItems(choiceObservableList);

        /*������ɴ�����p��q��g*/
        p.setText(String.valueOf(RSASimple.safePGen()));
        q.setText(String.valueOf(RSASimple.getQ()));
        d.setText(String.valueOf(RSASimple.getD()));
    }


    @FXML
    void RSAChoiceEvent() {
        String choice = this.RSAChoice.getSelectionModel().getSelectedItem();
        if (choice.equals("˽Կǩ������")) {
            text1.setText("ǩ��ԭ��");
            text2.setText("ǩ�����");
            text3.setVisible(false);
            textArea3.setVisible(false);
        } else if (choice.equals("��ԿУ��ǩ��")) {
            text3.setVisible(true);
            textArea3.setVisible(true);
            text1.setText("ǩ��ԭ��");
            text2.setText("ǩ�����");
            text3.setText("��ǩ���");
        } else {
            text3.setVisible(true);
            textArea3.setVisible(true);
        }
    }

    @FXML
    void bt_startEvent() throws Exception {
        String choice = this.RSAChoice.getSelectionModel().getSelectedItem();
        switch (choice) {
            case "��Կ����˽Կ���ܹ���":
                textArea2.setText(RSA.getCiphertext1(textArea1.getText()));
                textArea3.setText(RSA.getPlaintext1(RSA.getCiphertext1(textArea1.getText())));
                break;
            case "˽Կ���ܹ�Կ���ܹ���":
                textArea2.setText(RSA.getCiphertext2(textArea1.getText()));
                textArea3.setText(RSA.getPlaintext2(RSA.getCiphertext2(textArea1.getText())));
                break;
            case "˽Կǩ������":
                textArea2.setText(RSA.getSignStr(textArea1.getText()));
                break;
            case "��ԿУ��ǩ��":
                textArea2.setText(RSA.getSignStr(textArea1.getText()));
                textArea3.setText(RSA.getSignStr(textArea1.getText()));
                break;
            default:
                simpleUtil.informationDialog(Alert.AlertType.ERROR, "��ʾ", "����", "ѡ����Ϊ�գ�");
        }
    }


    @FXML
    void bt_backEvent(ActionEvent event) {
           RSAStage.close();
    }

    @FXML
    void bt_decryptEvent(ActionEvent event) throws IOException {
        decryption.setText(RSASimple.getPString(p.getText(), q.getText(), d.getText(), plaintext.getText()));
    }

    @FXML
    void bt_encryptEvent(ActionEvent event) throws IOException {
        encryption.setText(RSASimple.getCString(p.getText(), q.getText(), d.getText(), plaintext.getText()));
    }


}
