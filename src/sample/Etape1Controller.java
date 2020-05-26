package sample;

import Etape2.Etape2Controller;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Etape1Controller {
    public static String NbOfElem;
    public static String Max_capacity;


    @FXML
    public TextField nbOfElements;
    @FXML
    public  TextField Max_cap;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button submit;


    public String getNbOfElem() {
        return NbOfElem;
    }
    public String getMaxCap(){

        return Max_capacity;
    }

    public void setNbOfElem(String nbOfElem) {
        NbOfElem = nbOfElem;
    }

    public String getMax_capacity() {
        return Max_capacity;
    }

    public void setMax_capacity(String max_capacity) {
        Max_capacity = max_capacity;
    }

    public TextField getNbOfElements() {
        return nbOfElements;
    }

    public void setNbOfElements(TextField nbOfElements) {
        this.nbOfElements = nbOfElements;
    }

    public TextField getMax_cap() {
        return Max_cap;
    }

    public void setMax_cap(TextField max_cap) {
        Max_cap = max_cap;
    }

    public static void DataFileGenerator() {
        try {
            File myObj = new File("C:\\Users\\hp\\Desktop\\HultPrize 21\\GUI\\src\\sample\\bcs1.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public  String Read_Max_Cap(){
        // Listen for TextField text changes
        Max_capacity = Max_cap.getText();
        return Max_capacity;
            }



            public String Read_No_elements(){
        // Listen for TextField text changes
        NbOfElem = nbOfElements.getText();
        return NbOfElem;
}

    public void DataFileModify(){


        try{
            final File f = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String ProgPath = f.toString().replace("%20"," ");
            File file = new File(ProgPath +"\\SacADos.dat");
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("nbObjet = " + Read_No_elements() + "; // le nombre total des objects candidats");
            bw.write("\n");
            bw.write("poidsMax = " + Read_Max_Cap() + "; // le poid maximal que le sac peut porter");
            bw.write("\n");
            bw.flush();
            bw.close();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            br.close();


        }catch(IOException e){
            e.printStackTrace();
        }

    }
    @FXML
    public void initialize() {
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DataFileModify();

                try {
                    FXMLLoader loader = new FXMLLoader();
                    Parent Etape2 = FXMLLoader.load(getClass().getResource("../Etape2/Etape2.fxml"));
                    Scene ShowStep2 = new Scene(Etape2);
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(ShowStep2);
                    window.show();

                    Etape2Controller controller = loader.getController();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    //    assert nbOfElements != null : "fx:id=\"nbOfElements\" was not injected: check your FXML file 'Etape1.fxml'.";
  //      assert Max_cap != null : "fx:id=\"Max_cap\" was not injected: check your FXML file 'Etape1.fxml'.";
   //     assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'Etape1.fxml'.";

    }


    public void submitbuttonpushed(ActionEvent event){



    }
    private void handleButton1Action(ActionEvent event) {

        System.out.println(nbOfElements.getText());
        System.out.println(Max_cap.getText());


    }





    public void ReadData(InputMethodEvent inputMethodEvent) {
    }
}
