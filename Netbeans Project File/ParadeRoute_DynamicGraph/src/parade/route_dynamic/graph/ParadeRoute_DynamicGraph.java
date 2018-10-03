/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route_dynamic.graph;

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
public class ParadeRoute_DynamicGraph extends Application {
    
    private static Map<String, ArrayList<Edge>> all_intersection = new HashMap<String, ArrayList<Edge>>();
    private static Map<String, ParadeInfo> paradeInfoList = new HashMap<String, ParadeInfo>();
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        //View v = new View();
        //Controller c = new Controller(v);
        //ExcelRead input_all = new ExcelRead(all_intersection, paradeInfoList);
    }
    
}
