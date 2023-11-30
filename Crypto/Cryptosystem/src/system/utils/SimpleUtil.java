package system.utils;

import javafx.scene.control.*;

import java.util.Optional;

public class SimpleUtil {

    /**
     * ���������JavaFX�ж��Ƿ�Ϊ��
     *
     * @param str �ı�
     * @return boolean �����Ϊ�շ���true�����򷵻�false
     */

    public boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * ����������Զ���һ��JavaFX�ĶԻ���
     *
     * @param alterType �Ի�������
     * @param title     �Ի������
     * @param header    �Ի���ͷ��Ϣ
     * @param message   �Ի�����ʾ����
     * @return boolean �������ˡ�ȷ������ô�ͷ���true�����򷵻�false
     */
    public boolean informationDialog(Alert.AlertType alterType, String title, String header, String message) {
        // ��ť���ֿ����Լ� new һ��
        Alert alert = new Alert(alterType, message, new ButtonType("ȷ��", ButtonBar.ButtonData.YES));

        // ���öԻ���ı���
        alert.setTitle(title);
        alert.setHeaderText(header);
        // showAndWait() ���ڶԻ�����ʧ��ǰ����ִ��֮��Ĵ���
        Optional<ButtonType> buttonType = alert.showAndWait();
        // ���ݵ���������
        return buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES);
        // �������ˡ�ȷ�����ͷ���true
    }

}
