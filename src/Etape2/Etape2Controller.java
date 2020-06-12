package Etape2;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Etape1Controller;
import sample.Main;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Etape2Controller implements Initializable {
    //create a Elements array lists
    public static List<String> ELements_nameList = new ArrayList<>();
    public static List<String> ELements_PriceList = new ArrayList<>();
    public static List<String> ELements_SizeList = new ArrayList<>();
    // this method will update the scene variables
    public int init_elem = 1;
    // get the Number of elements from Etape1Controller Class
    Etape1Controller controller = new Etape1Controller();
    public int CurrentValue = Integer.parseInt(controller.getNbOfElem());
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField Elem_name;
    @FXML
    private TextField Elem_size;


    // table configuration
    @FXML
    private TextField Elem_price;
    @FXML
    private Button Submit_elem;
    @FXML
    private Label Re_elem;
    @FXML
    private Label Cur_elem;
    @FXML
    private Button Final_submit;
    @FXML
    private TableView<Element> tableView;
    /*    Etape1Controller controller2 = new Etape1Controller();
         String max_cap = controller2.Max_cap.getText();*/
    @FXML
    private TableColumn<Element, String> ElementName;
    @FXML
    private TableColumn<Element, String> ElementSize;
    @FXML
    private TableColumn<Element, String> ElementPrice;

    public void Submit_elemPushed() {
        Element New_element = new Element(Elem_name.getText(), Elem_size.getText(), Elem_price.getText());
        if (CurrentValue > 0) {
            tableView.getItems().add(New_element);
            CurrentValue--;
            Cur_elem.setText(String.valueOf(init_elem++));
            Re_elem.setText(String.valueOf(CurrentValue));
            ElemName_ArrayList_add();
            ElemSize_ArrayList_add();
            ElemPrice_ArrayList_add();
        }

    }

    public List<String> ElemName_ArrayList_add(){

        ELements_nameList.add(Elem_name.getText());

        return ELements_nameList;

    }

    public List<String> GetNameList() {
        return ELements_nameList;

    }

    public List<String> GetPriceList() {
        return ELements_PriceList;

    }

    public List<String> GetSizeList() {
        return ELements_SizeList;

    }

    public List<String> ElemSize_ArrayList_add() {

        ELements_SizeList.add(Elem_size.getText());
        return ELements_SizeList;

    }

    public List<String> ElemPrice_ArrayList_add(){

        ELements_PriceList.add(Elem_price.getText());
        return ELements_PriceList;

    }

    //Append parameters into the dat file
    public void append_dat_file(){
        final File f = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String ProgPath = f.toString().replace("%20"," ");
        try(FileWriter fw = new FileWriter(ProgPath + "\\SacADos.dat", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println("valeur =" + ELements_PriceList + ";");
            out.println("poids = " + ELements_SizeList + ";");
        } catch (IOException e) {
        }




    }

    public void ExportCplexFiles() throws IOException {

        final File f = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        String ProgPath = f.toString().replace("%20"," ").replace("\\", "\\\\");
        System.out.println("cd " + ProgPath);



        ProcessBuilder pb = new ProcessBuilder("oplrun",
                "-e",
                "SacADos.lp",
                "SacADos.mod",
                "SacADos.dat");
        pb.directory(new File(ProgPath));
        Process pr = pb.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String str = null;
        while ((str = br.readLine()) != null) {
            System.out.println(str);
        }



    }

    public void Final_SubmitPushed() throws IOException {

    append_dat_file();
    ExportCplexFiles();


    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        ElementName.setCellValueFactory(new PropertyValueFactory<Element, String>("Name"));
        ElementSize.setCellValueFactory(new PropertyValueFactory<Element, String>("Size"));
        ElementPrice.setCellValueFactory(new PropertyValueFactory<Element, String>("Price"));


        Re_elem.setText(String.valueOf(CurrentValue));
        Cur_elem.setText(String.valueOf(0));


        Final_submit.setOnAction(new EventHandler<ActionEvent>() {

        public void handle(ActionEvent event) {
            System.out.println("Etape 2 " + ELements_nameList);
            try {
                Final_SubmitPushed();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                FXMLLoader loader = new FXMLLoader();
                Parent Etape4 = FXMLLoader.load(getClass().getResource("../Etape4/Etape4.fxml"));
                Scene ShowStep4 = new Scene(Etape4);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(ShowStep4);
                window.show();

                Etape2Controller controller = loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        });


    }

    public static class Element {
        private SimpleStringProperty Name;
        private SimpleStringProperty Size;
        private SimpleStringProperty Price;

        public Element(String name, String size, String price) {
            Name = new SimpleStringProperty(name);
            Size = new SimpleStringProperty(size);
            Price = new SimpleStringProperty(price);
        }

        public String getName() {
            return Name.get();
        }

        public void setName(String name) {
            this.Name.set(name);
        }

        public SimpleStringProperty nameProperty() {
            return Name;
        }

        public String getSize() {
            return Size.get();
        }

        public void setSize(String size) {
            this.Size.set(size);
        }

        public SimpleStringProperty sizeProperty() {
            return Size;
        }

        public String getPrice() {
            return Price.get();
        }

        public void setPrice(String price) {
            this.Price.set(price);
        }

        public SimpleStringProperty priceProperty() {
            return Price;
        }
    }





    }
