/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Michael
 */
public class ParadeRouteDynamicGraph extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    private static Map<String, ArrayList<Edge>> all_intersection;
    private static Map<String, ParadeInfo> paradeInfoList;
    private static LocalDate simulationDate;
    
    public static void main(String[] args) throws Exception {
        launch(args);
        all_intersection = new HashMap<String, ArrayList<Edge>>();
        paradeInfoList = new HashMap<String, ParadeInfo>();
        
        //View v = new View();
        //Controller c = new Controller(v);
        ExcelRead input_all = new ExcelRead(all_intersection, paradeInfoList, simulationDate);
        all_intersection = input_all.getMap();
        paradeInfoList = input_all.getParadeList();
        simulationDate = input_all.getDate();
        System.out.println(simulationDate);
    }
    
}
