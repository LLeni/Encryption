package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    @FXML
    Button buttonSave, buttonChoose, buttonEncrypt;

    @FXML
    TextField fieldKey;

    @FXML
    TextArea contentInitialFile, contentEncryptFile;

    @FXML
    AnchorPane anchorPane;

    private final String DEFAULT_ENCODING = "UTF-8";
    private BASE64Encoder enc = new BASE64Encoder();

    final FileChooser fileChooser = new FileChooser();


    public void initialize() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

    }


    public void encryptFile() {

        if(contentInitialFile.getText().length() == 0 || fieldKey.getText().length() == 0){
            Alert alertDialog = new Alert(Alert.AlertType.ERROR, "Или ключ отсуствует, или вы не выбрали файл");
            alertDialog.setTitle("Ошибка");
            alertDialog.setHeaderText("Произошла ошибка!");
            alertDialog.showAndWait();
        } else {

            try {

                char[] keyC = fieldKey.getText().toCharArray();
                char[] contentC = contentInitialFile.getText().toCharArray();

                int cl = contentC.length;
                int kl = keyC.length;
                char[] newContent = new char[cl];

                for (int i = 0; i < cl; i++) {
                    newContent[i] = (char)(contentC[i] ^ keyC[i % kl]);
                }

                contentEncryptFile.setText(base64encode(new String(newContent)));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public String base64encode(String text) {
        try {
            return enc.encode(text.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }//base64encode

    public void chooseFile() {
        configureFileChooser(fileChooser, "Выбор файла");
        File file = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        if (file != null) {
            openFile(file);
        }

    }

    public void saveFile() {
        if(contentEncryptFile.getText().length() == 0) {
            Alert alertDialog = new Alert(Alert.AlertType.ERROR, "Вначале зашифруйте файл");
            alertDialog.setTitle("Ошибка");
            alertDialog.setHeaderText("Произошла ошибка!");
            alertDialog.showAndWait();
        } else {
            configureFileChooser(fileChooser, "Сохранение файла");
            File file = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
            if (file != null) {
                saveTextToFile(contentEncryptFile.getText(), file);
            }
        }
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void configureFileChooser(final FileChooser fileChooser, String title) {
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    private void openFile(File file) {
        String content = "";
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                content += scanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        contentInitialFile.setText(content);
    }


}
