package Etape4;

import Etape2.Etape2Controller;
import ilog.concert.IloException;
import ilog.concert.IloLPMatrix;
import ilog.cplex.IloCplex;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label Max_Cap ;

    @FXML
    private Button Restart;
    int ObjectValue;

    Etape2Controller controller = new Etape2Controller();
     List<String> ListOfELemName = controller.GetNameList();
     List<String> ListOfELemPrice = controller.GetPriceList();
     List<String> ListOfELemSize = controller.GetSizeList();




    IloCplex model;


    {
        try {
            final File f = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String ProgPath = f.toString().replace("%20"," ").replace("\\", "\\\\");

            String LPModel = ProgPath + "\\\\SacADos.lp";
            model = new IloCplex();
            model.importModel(LPModel);
            model.solve();

            IloLPMatrix lp = (IloLPMatrix)model.LPMatrixIterator().next();

            double[] incx = model.getValues(lp);
            int NofChosenElem = 0;
            List<Integer> ItemsMatrix = new ArrayList<>();
            System.out.println(Arrays.toString(incx));
            for (int j = 0; j < incx.length; j++) {


                if (incx[j] != 0 ) {
                    NofChosenElem++;

                    ItemsMatrix.add(j);
                }




                System.out.println("Variable " + j + ": Value = " + incx[j]);
            }





                List<String> ChosenELemNames = new ArrayList<>();
                List<String> ChosenELemPrice = new ArrayList<>();
                List<String> ChosenELemSize = new ArrayList<>();

                for (int i = 0; i < ItemsMatrix.size() ;i++) {

                    ChosenELemNames.add(ListOfELemName.get(ItemsMatrix.get(i)));
                    ChosenELemPrice.add(ListOfELemPrice.get(ItemsMatrix.get(i)));
                    ChosenELemSize.add(ListOfELemSize.get(ItemsMatrix.get(i)));

                }



            System.out.println();
            System.out.println("N of chosen elements "+ NofChosenElem);
            System.out.println(" items matrix"  + ItemsMatrix);
            System.out.println("List of all Elem Name " + ListOfELemName);
            System.out.println("List of all Elem Size " + ListOfELemSize);
            System.out.println("List of all Elem Price " + ListOfELemPrice);
            System.out.println("chosen elements names "+ChosenELemNames);
            System.out.println("chosen elements Prices " + ChosenELemPrice);
            System.out.println("Chosen elements sizes" + ChosenELemSize);
           ObjectValue = (int) model.getObjValue();




            System.out.println(ObjectValue); // this is the value of the filled with maximal price
            model.end(); // close process of model .




        } catch (IloException e) {
            e.printStackTrace();
        }
    }



    public void Read_lp_file() {
        {


        }

    }




    @FXML
    void initialize() throws IloException {
        Etape1Controller controller = new Etape1Controller();
        String max_cap = controller.getMax_capacity();
       Max_Cap.setText(String.valueOf(max_cap));
        System.out.println(max_cap);
        Read_lp_file();
        Max_Price.setText(String.valueOf(ObjectValue));

        assert Max_Price != null : "fx:id=\"Max_Price\" was not injected: check your FXML file 'Etape4.fxml'.";
        assert Cap_sum != null : "fx:id=\"Cap_sum\" was not injected: check your FXML file 'Etape4.fxml'.";
        assert Max_Cap != null : "fx:id=\"Max_Cap\" was not injected: check your FXML file 'Etape4.fxml'.";
        assert Restart != null : "fx:id=\"Restart\" was not injected: check your FXML file 'Etape4.fxml'.";

    }
}
