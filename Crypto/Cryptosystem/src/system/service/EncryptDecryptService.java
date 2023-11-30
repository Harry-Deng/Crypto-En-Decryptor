package system.service;

import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputControl;
import system.cipher.*;
import system.utils.SimpleUtil;

public class EncryptDecryptService {
    public EncryptDecryptService() {
    }

    /*
     * 字符串加密
     *
     * */
    public static void isStart1(String algorithm, TextInputControl plaintext, TextInputControl ciphertext, TextInputControl key,
                                RadioButton encryptBT, RadioButton decryptBT) {
        SimpleUtil simpleUtil = new SimpleUtil();

        if (encryptBT.isSelected()) {
            if (!simpleUtil.isEmpty(plaintext.getText())) {
                if (!simpleUtil.isEmpty(key.getText())) {
                    ciphertext.setText(EncryptService(algorithm, plaintext.getText(), key.getText()));
                } else
                    simpleUtil.informationDialog(Alert.AlertType.ERROR, "提示", "警告", "密钥不能为空！");
            } else
                simpleUtil.informationDialog(Alert.AlertType.ERROR, "提示", "警告", "明文不能为空！");
        } else if (decryptBT.isSelected()) {
            if (!simpleUtil.isEmpty(ciphertext.getText())) {
                if (!simpleUtil.isEmpty(key.getText())) {
                    plaintext.setText(DecryptService(algorithm, ciphertext.getText(), key.getText()));
                } else
                    simpleUtil.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "密钥不能为空！");
            } else
                simpleUtil.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "密文不能为空！");
        } else
            simpleUtil.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "请选择加密或者解密！");
    }

    public static String EncryptService(String algorithm, String plaintext, String key) {
        String ciphertext = "";
        switch (algorithm) {
            case "Caesar":
                ciphertext = Caesar.Encryption(plaintext, Integer.parseInt(key));
                break;
            case "Keyword":
                ciphertext = Keyword.Encryption(plaintext, key);
                break;
            case "Multiliteral":
                ciphertext = Multiliteral.Encryption(plaintext, key);
                break;
            case "Vigenere":
                ciphertext = Vigenere.encryption(plaintext, key);
                break;
            case "Autokey ciphertext":
                ciphertext = AutokeyCiphertext.encryption(plaintext,key);
                break;
            case "Autokey plaintext":
                ciphertext = AutokeyPlaintext.encryption(plaintext,key);
                break;
            case "Playfair":
                ciphertext = Playfair.encrypt(plaintext, key);
                break;
            case "Permutation":
                ciphertext = Permutation.EorD(plaintext, Permutation.reverseKey(Permutation.sortKey(key)));
                break;
            case "Column permutation":
                ciphertext = ColPermutation.encryption(plaintext, key);
                break;
            case "RC4":
                ciphertext = RC4.encry_RC4_string(plaintext, key);
                break;
            case "DES":
                ciphertext = DES.encrypt(plaintext, key, "encrypt");
                break;

        }
        return ciphertext;
    }

    public static String DecryptService(String algorithm, String ciphertext, String key) {
        String plaintext = "";
        switch (algorithm) {
            case "Caesar":
                plaintext = Caesar.Decryption(ciphertext, Integer.parseInt(key));
                break;
            case "Keyword":
                plaintext = Keyword.Decryption(ciphertext, key);
                break;
            case "Multiliteral":
                plaintext = Multiliteral.Decryption(ciphertext, key);
                break;
            case "Vigenere":
                plaintext = Vigenere.decryption(ciphertext, key);
                break;
            case "Autokey ciphertext":
                plaintext = AutokeyCiphertext.decryption(ciphertext,key);
                break;
            case "Autokey plaintext":
                 plaintext = AutokeyPlaintext.decryption(ciphertext,key);
                break;
            case "Playfair":
                plaintext = Playfair.decrypt(ciphertext, key);
                break;
            case "Permutation":
                plaintext = Permutation.EorD(ciphertext, Permutation.sortKey(key));
                break;
            case "Column permutation":
                plaintext = ColPermutation.decryption(ciphertext, key);
                break;
            case "RC4":
                plaintext = RC4.decry_RC4_string(ciphertext, key);
                break;
            case "CA_1d":
                plaintext = CA_1d.decryption(ciphertext,key);
                break;
            case "DES":
                plaintext = DES.encrypt(ciphertext, key, "decrypt");
                break;


        }
        return plaintext;
    }

    public static String CA_1dEncrypt(TextInputControl length,TextInputControl initialization,TextInputControl rule,TextInputControl random,TextInputControl plaintext){
        String ciphertext = CA_1d.encryption(Integer.parseInt(length.getText()),initialization.getText(),Integer.parseInt(rule.getText()),
                Integer.parseInt(random.getText()),plaintext.getText());
        return ciphertext;
    }

    public static String getCA_key(){
        return CA_1d.getKeyStream(CA_1d.keyStream);
    }
}
