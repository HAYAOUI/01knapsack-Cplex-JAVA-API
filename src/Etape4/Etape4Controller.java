package Etape4;

import Etape2.Etape2Controller;
import Etape2.Etape2Controller.Element;
import ilog.concert.IloException;
import ilog.concert.IloLPMatrix;
import ilog.cplex.IloCplex;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Etape1Controller;
import sample.Main;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Etape4Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Max_Price;

    @FXML
    private Label Cap_sum;

    @FXML
    private Label Max_Cap;

    @FXML
    private Button Restart;
    int ObjectValue;

    @FXML
    private TableView<Element> FinalTable;

    @FXML
    private TableColumn<Element, String> Final_Elements;

    @FXML
    private TableColumn<Element, String> Final_Prices;

    @FXML
    private TableColumn<Element, String> Final_Size;


    Etape2Controller controller1 = new Etape2Controller();
    public List<String> ListOfELemName = controller1.GetNameList();
    public List<String> ListOfELemPrice = controller1.GetPriceList();
    public List<String> ListOfELemSize = controller1.GetSizeList();


    IloCplex model;


    public List<String> ChosenELemNames = null;
    public List<String> ChosenELemPrice = null;
    public List<String> ChosenELemSize = null;
    public int NofChosenElem = 0;

    {
        try {
            final File f = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String ProgPath = f.toString().replace("%20", " ").replace("\\", "\\\\");

            String LPModel = ProgPath + "\\\\SacADos.lp";
            model = new IloCplex();
            model.importModel(LPModel);
            model.solve();

            IloLPMatrix lp = (IloLPMatrix) model.LPMatrixIterator().next();

            double[] incx = model.getValues(lp);

            List<Integer> ItemsMatrix = new ArrayList<>();
            System.out.println(Arrays.toString(incx));
            for (int j = 0; j < incx.length; j++) {


                if (incx[j] != 0) {
                    NofChosenElem++;

                    ItemsMatrix.add(j);
                }


                System.out.println("Variable " + j + ": Value = " + incx[j]);
            }


            ChosenELemNames = new ArrayList<>();
            ChosenELemPrice = new ArrayList<>();
            ChosenELemSize = new ArrayList<>();

            for (int i = 0; i < ItemsMatrix.size(); i++) {

                ChosenELemNames.add(ListOfELemName.get(ItemsMatrix.get(i)));
                ChosenELemPrice.add(ListOfELemPrice.get(ItemsMatrix.get(i)));
                ChosenELemSize.add(ListOfELemSize.get(ItemsMatrix.get(i)));

            }


            System.out.println();
            System.out.println("N of chosen elements " + NofChosenElem);
            System.out.println(" items matrix" + ItemsMatrix);
            System.out.println("List of all Elem Name " + ListOfELemName);
            System.out.println("List of all Elem Size " + ListOfELemSize);
            System.out.println("List of all Elem Price " + ListOfELemPrice);
            System.out.println("chosen elements names " + ChosenELemNames);
            System.out.println("chosen elements Prices " + ChosenELemPrice);
            System.out.println("Chosen elements sizes" + ChosenELemSize);
            ObjectValue = (int) model.getObjValue();


            System.out.println(ObjectValue); // this is the value of the filled with maximal price
            model.end(); // close process of model .


        } catch (IloException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() throws IloException {
        Etape1Controller controller = new Etape1Controller();
        String max_cap = controller.getMax_capacity();
        Max_Cap.setText(String.valueOf(max_cap));
        System.out.println(max_cap);
        Max_Price.setText(String.valueOf(ObjectValue));


        Final_Elements.setCellValueFactory(new PropertyValueFactory<Element, String>("Name"));
        Final_Size.setCellValueFactory(new PropertyValueFactory<Element, String>("Size"));
        Final_Prices.setCellValueFactory(new PropertyValueFactory<Element, String>("Price"));
        FinalTable.setItems(getItems());



    }

    public ObservableList<Element> getItems() {

        ObservableList<Element> Chosen = FXCollections.observableArrayList();
        for (int i = 0; i < NofChosenElem; i++) {
            Chosen.add(new Element(ChosenELemNames.get(i), ChosenELemSize.get(i), ChosenELemPrice.get(i)));

        }
        return Chosen;

    }
}