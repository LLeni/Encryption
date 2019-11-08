package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private final int WIDTH_WINDOW = 810;
    private final int HEIGHT_WINDOW = 530;
    private final String TITLE_PROGRAM = "Encryption";
    private final String VERSION = "v1.0";

    // Ссылками, которыми пользовался
    // https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/structure.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle(TITLE_PROGRAM + " " + VERSION);
        primaryStage.setScene(new Scene(root, WIDTH_WINDOW, HEIGHT_WINDOW));
        primaryStage.setMinWidth(WIDTH_WINDOW);
        primaryStage.setMaxWidth(WIDTH_WINDOW);
        primaryStage.setMinHeight(HEIGHT_WINDOW);
        primaryStage.setMaxHeight(HEIGHT_WINDOW);
        primaryStage.show();
}


    public static void main(String[] args) {
        launch(args);
    }
}
