package system.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import system.cipher.DH.DHCoder2;

import java.math.BigInteger;

public class DH2FrameController {

    @FXML
    private Stage DH2Stage;

    public void setDH2Stage(Stage dh2Stage){
        this.DH2Stage = dh2Stage;
    }

    @FXML
    private FontAwesomeIconView exitButton;

    @FXML
    private TextField p;

    @FXML
    private TextField g;

    @FXML
    private TextField len;

    @FXML
    private TextField A;

    @FXML
    private TextField B;

    @FXML
    private TextField share1;

    @FXML
    private TextField share2;

    @FXML
    private Button bt_back;

    @FXML
    private Button bt_compute;

    @FXML
    private void initialize(){
        A.setText("01010101010101");
        B.setText("10101001010101");
        p.setText("24");
        g.setText("8");
    }


    @FXML
    void bt_backEvent(ActionEvent event) {
        Stage stage = (Stage)bt_back.getScene().getWindow();
        stage.close();
    }

    @FXML
    void bt_computeEvent(ActionEvent event) {
        BigInteger b1 = DHCoder2.get1(A.getText(),Integer.parseInt(g.getText()),Integer.parseInt(p.getText()));
        BigInteger b2 = DHCoder2.get1(B.getText(),Integer.parseInt(g.getText()),Integer.parseInt(p.getText()));
        share1.setText(DHCoder2.getkey1(b2,A.getText(),Integer.parseInt(p.getText()),Integer.parseInt(len.getText())));
        share2.setText(DHCoder2.getkey1(b1,B.getText(),Integer.parseInt(p.getText()),Integer.parseInt(len.getText())));
    }

    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }

}
