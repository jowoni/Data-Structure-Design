/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

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
        vc = new ViewController();
        input_all = new ExcelRead(vc.getWholeGraph(), vc.getParadeInfoMap(), vc.getSimulationDate(), vc.getInitialTime());
        vc.setWholeGraph(input_all.getMap());
        vc.setParadeInfoMap(input_all.getParadeList());
        vc.setSimulationDate(input_all.getDate());
        vc.setInitialTime(input_all.getTime());
        
        //System.out.println(input_all.getParadeList().get("새한국").getStartTime());
        //System.out.println(input_all.getParadeList().get("새한국").getEndTime());
        //System.out.println(input_all.getParadeList().get("새한국").getInProgress());
        /*
        System.out.println("새한국");
        System.out.println(input_all.getParadeList().get("새한국").getParadeLength());
        System.out.println(input_all.getParadeList().get("새한국").getParadeSpeed());
        System.out.println(input_all.getParadeList().get("새한국").getTotalTime());
        System.out.println("구명총");
        System.out.println(input_all.getParadeList().get("구명총").getParadeLength());
        System.out.println(input_all.getParadeList().get("구명총").getParadeSpeed());
        System.out.println(input_all.getParadeList().get("구명총").getTotalTime());
        System.out.println("태극기국민평의회");
        System.out.println(input_all.getParadeList().get("태극기국민평의회").getParadeLength());
        System.out.println(input_all.getParadeList().get("태극기국민평의회").getParadeSpeed());
        System.out.println(input_all.getParadeList().get("태극기국민평의회").getTotalTime());
        System.out.println("석방운동본부");
        System.out.println(input_all.getParadeList().get("석방운동본부").getParadeLength());
        System.out.println(input_all.getParadeList().get("석방운동본부").getParadeSpeed());
        System.out.println(input_all.getParadeList().get("석방운동본부").getTotalTime());
        System.out.println("태극기행동본부");
        System.out.println(input_all.getParadeList().get("태극기행동본부").getParadeLength());
        System.out.println(input_all.getParadeList().get("태극기행동본부").getParadeSpeed());
        System.out.println(input_all.getParadeList().get("태극기행동본부").getTotalTime());
        System.out.println("태극기국민운동본부");
        System.out.println(input_all.getParadeList().get("태극기국민운동본부").getParadeLength());
        System.out.println(input_all.getParadeList().get("태극기국민운동본부").getParadeSpeed());
        System.out.println(input_all.getParadeList().get("태극기국민운동본부").getTotalTime());
        */
        launch(args);
    }
}
