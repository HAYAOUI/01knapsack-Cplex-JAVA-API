package sample;

//
//
import ilog.concert.*;
import ilog.cplex.IloCplex;
//
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {












    public static void main(String[] args) {
        launch(args);

        //Etape1Controller.DataFileGenerator();
       // Etape1Controller.DataFileModify();


       /* */
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Etape1.fxml"));
        primaryStage.setTitle("Sac a dos");
        primaryStage.setScene(new Scene(root, 510, 207));
        primaryStage.show();
    }
}
