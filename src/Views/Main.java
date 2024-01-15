package Views;

import bdProd.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    private static final String LOCK_FILE_PATH = "myapp.lock";
    private static Scene mainScene;

    public static void main(String[] args) {

        File lockFile = new File(LOCK_FILE_PATH);
        if (lockFile.exists()) {
            System.exit(0);
        }
        try {
            lockFile.createNewFile();
            lockFile.deleteOnExit();
        } catch (IOException e) {
            System.exit(0);
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainView.fxml"));

        ScrollPane scrollPane = loader.load();

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        mainScene = new Scene(scrollPane);
        primaryStage.setMaximized(true);
        primaryStage.setScene(mainScene);

        try {
            String caminho = DB.encontrarCaminho("config");
            Image logoImage = new Image(new FileInputStream(caminho));
            primaryStage.getIcons().add(logoImage);
        } catch (FileNotFoundException e) {
        } catch (RuntimeException d) {
        }
        try {
            primaryStage.setTitle(DB.encontrarCaminho("nome") + " -  LD Tech");
        } catch (Exception e) {
            primaryStage.setTitle("Aplicação -  LD Tech");
        }
        primaryStage.show();
    }

    public static Scene getMainScene() {
        return mainScene;
    }
}
