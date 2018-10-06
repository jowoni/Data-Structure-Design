/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Michael
 */
public class ParadeRouteDynamicGraph extends Application {
    
    public static ViewController vc;
    public static ExcelRead input_all;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) throws Exception {
        launch(args);
        vc = new ViewController();
        input_all = new ExcelRead(vc.getWholeGraph(), vc.getRouteGraph(), vc.getSimulationDate(), vc.getInitialTime());
        vc.setWholeGraph(input_all.getMap());
        vc.setRouteGraph(input_all.getParadeList());
        vc.setSimulationDate(input_all.getDate());
        vc.setInitialTime(input_all.getTime());
    }
    
}
