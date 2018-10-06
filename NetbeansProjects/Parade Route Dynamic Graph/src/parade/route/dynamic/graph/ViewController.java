/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static parade.route.dynamic.graph.ParadeRouteDynamicGraph.vc;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;

/**
 *
 * @author Michael
 */
public class ViewController implements Initializable {
    
    private static Map<String, ArrayList<Edge>> wholeGraph = new HashMap<>();
    private static Map<String, ParadeInfo> routeGraph = new HashMap<>();
    private static LocalDate simulationDate = null;
    private static LocalTime initialTime = null;
    private long timeProgress;
    /*
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    TimerTask shit = new TimerTask() {
        @Override
        public void run() {
            vc.setInitialTime(vc.getInitialTime().plusMinutes(10));
            currentTime.setText(vc.getInitialTime().toString());
        }
    };*/
    
    // Local Variable Getter & Setter Methods
    Map<String, ArrayList<Edge>> getWholeGraph(){
        return wholeGraph;
    }
    Map<String, ParadeInfo> getRouteGraph(){
        return routeGraph;
    }
    LocalDate getSimulationDate(){
        return simulationDate;
    }
    LocalTime getInitialTime(){
        return initialTime;
    }
    void setWholeGraph(Map<String, ArrayList<Edge>> wholeGraph){
        ViewController.wholeGraph = wholeGraph;
    }
    void setRouteGraph(Map<String, ParadeInfo> routeGraph){
        ViewController.routeGraph = routeGraph;
    }
    void setSimulationDate(LocalDate simulationDate){
        ViewController.simulationDate = simulationDate;
    }
    void setInitialTime(LocalTime initialTime){
        ViewController.initialTime = initialTime;
    }
    
    @FXML
     Label currentTime;
    
    @FXML
    private Button play;
    
    @FXML
    private Button pause;
    
    void runging(){
        new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    vc.setInitialTime(vc.getInitialTime().plusMinutes(10));
                    currentTime.setText(vc.getInitialTime().toString());
            });
            }
        }, 
        500 
);
    }
    
    @FXML
    public void onPlayClicked(ActionEvent event) throws InterruptedException{
        //service.scheduleAtFixedRate(shit, 0, 1, TimeUnit.SECONDS);
        timeProgress = System.currentTimeMillis(); 
        runging();
    }
    
    @FXML
    public void onBoostClicked(ActionEvent event){
        currentTime.setText(vc.getInitialTime().toString());
        vc.timeProgress = System.currentTimeMillis();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentTime.setText(vc.getInitialTime().toString());
    }  
}
