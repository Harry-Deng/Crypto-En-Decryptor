package system.utils;

import javafx.scene.control.*;

import java.util.Optional;

public class SimpleUtil {

    /**
     * 操作结果：JavaFX判断是否为空
     *
     * @param str 文本
     * @return boolean 如果不为空返回true，否则返回false
     */

    public boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 操作结果：自定义一个JavaFX的对话框
     *
     * @param alterType 对话框类型
     * @param title     对话框标题
     * @param header    对话框头信息
     * @param message   对话框显示内容
     * @return boolean 如果点击了”确定“那么就返回true，否则返回false
     */
    public boolean informationDialog(Alert.AlertType alterType, String title, String header, String message) {
        // 按钮部分可以自己 new 一个
        Alert alert = new Alert(alterType, message, new ButtonType("确定", ButtonBar.ButtonData.YES));

        // 设置对话框的标题
        alert.setTitle(title);
        alert.setHeaderText(header);
        // showAndWait() 将在对话框消失以前不会执行之后的代码
        Optional<ButtonType> buttonType = alert.showAndWait();
        // 根据点击结果返回
        return buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES);
        // 如果点击了“确定”就返回true
    }

}
