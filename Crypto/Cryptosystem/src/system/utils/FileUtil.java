package system.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
    public static String initFileChooser(Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter
                ("Text Files", "*.txt"));
        String text = "";
        if (file!= null) {
            BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
            text = br.readLine();
        } else {
            text = "未打开文件！";
        }
        return text;
    }

}
