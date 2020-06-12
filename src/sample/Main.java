package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Etape1.fxml"));
        primaryStage.getIcons().add(new Image("sample/icon.png"));
        primaryStage.setTitle("Sac a dos");
        primaryStage.setScene(new Scene(root, 510, 207));
        primaryStage.show();
    }
}
