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
    public static ExcelRead input;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) throws Exception {
        input = new ExcelRead();
        vc = new ViewController();
        //System.out.println("총 길이 :" + vc.getParadeInfoMap().get("새한국").getRouteLength());
        //System.out.println("총 시간 : " + vc.getParadeInfoMap().get("새한국").getTotalTime());
        //System.out.println("속력 : " + vc.getParadeInfoMap().get("새한국").getParadeSpeed());
        //System.out.println("종착지 : " + vc.getParadeInfoMap().get("새한국").getDestNode());
        launch(args);
    }
}
