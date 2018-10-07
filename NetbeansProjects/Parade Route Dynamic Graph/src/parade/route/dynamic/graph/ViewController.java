/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
import javafx.scene.shape.Circle;

/**
 *
 * @author Michael
 */
public class ViewController implements Initializable {
    
    private static Map<String, Map<String, Integer>> wholeGraph = new HashMap<>(); //전체 지도
    private static Map<String, ParadeInfo> paradeInfoMap = new HashMap<>(); //시위대명별 행진정보
    private static LocalDate simulationDate = null;
    private static LocalTime initialTime = null;
    private static LocalTime currentTime = null;
    private long timeProgress;
    /*
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    TimerTask shit = new TimerTask() {
        @Override
        public void run() {
            vc.setInitialTime(vc.getInitialTime().plusMinutes(10));
            currentTimeLbl.setText(vc.getInitialTime().toString());
        }
    };*/
    
    // Local Variable Getter & Setter Methods
    Map<String, Map<String, Integer>> getWholeGraph(){
        return wholeGraph;
    }
    Map<String, ParadeInfo> getParadeInfoMap(){
        return paradeInfoMap;
    }
    LocalDate getSimulationDate(){
        return simulationDate;
    }
    LocalTime getInitialTime(){
        return initialTime;
    }
    LocalTime getCurrentTime(){
        return currentTime;
    }
    void setWholeGraph(Map<String, Map<String, Integer>> wholeGraph){
        ViewController.wholeGraph = wholeGraph;
    }
    void setCurrentTime(LocalTime currentTime){
        ViewController.currentTime = currentTime;
    }
    void setParadeInfoMap(Map<String, ParadeInfo> paradeInfoMap){
        ViewController.paradeInfoMap = paradeInfoMap;
    }
    void setSimulationDate(LocalDate simulationDate){
        ViewController.simulationDate = simulationDate;
    }
    void setInitialTime(LocalTime initialTime){
        ViewController.initialTime = initialTime;
    }
    void nextTime(){
        vc.getInitialTime().plusMinutes(10);
        currentTimeLbl.setText(getInitialTime().toString());
    }
    
    void calcProgress(){
        for( String key : vc.getParadeInfoMap().keySet() ){
            if(vc.getParadeInfoMap().get(key).getStartTime().isAfter(vc.getCurrentTime())){
                vc.getParadeInfoMap().get(key).setProgressLength(
                        vc.getParadeInfoMap().get(key).getStartTime().until(vc.getCurrentTime(),ChronoUnit.MINUTES)
                        *vc.getParadeInfoMap().get(key).getParadeSpeed()
                );
            }
        }
    }
    
    void calcActiveEdges(){
        
    }
    
    @FXML
     Label currentTimeLbl;
    
    @FXML
    private Button play;
    
    @FXML
    private Button prev;
    
    @FXML
    private Button next;
    
    @FXML
    private Button reset;
    
    @FXML
    private Circle 개풍;
    @FXML
    private Circle 경희궁;
    @FXML
    private Circle 공평;
    @FXML
    private Circle 광교;
    @FXML
    private Circle 광화문;
    @FXML
    private Circle 금호;
    @FXML
    private Circle 꽃집;
    @FXML
    private Circle 낙원상가;
    @FXML
    private Circle 남대문;
    @FXML
    private Circle 남신육교;
    @FXML
    private Circle 내자;
    @FXML
    private Circle 눈스퀘어;
    @FXML
    private Circle 대한문;
    @FXML
    private Circle 덕수궁;
    @FXML
    private Circle 덕수초;
    @FXML
    private Circle 독립문;
    @FXML
    private Circle 동십자;
    @FXML
    private Circle 동원;
    @FXML
    private Circle 명동성당;
    @FXML
    private Circle 명보;
    @FXML
    private Circle 모전교;
    @FXML
    private Circle 문화거리;
    @FXML
    private Circle 미대사관;
    @FXML
    private Circle 박물관;
    @FXML
    private Circle 사직공원;
    @FXML
    private Circle 서대문;
    @FXML
    private Circle 서린;
    @FXML
    private Circle 서울역;
    @FXML
    private Circle 서울청;
    @FXML
    private Circle 세문관;
    @FXML
    private Circle 세문관뒤;
    @FXML
    private Circle 세종;
    @FXML
    private Circle 소공;
    @FXML
    private Circle 신교;
    @FXML
    private Circle 안국;
    @FXML
    private Circle 안국역;
    @FXML
    private Circle 열린마당;
    @FXML
    private Circle 염천;
    @FXML
    private Circle 옥인;
    @FXML
    private Circle 을지1;
    @FXML
    private Circle 을지2;
    @FXML
    private Circle 을지3;
    @FXML
    private Circle 의주;
    @FXML
    private Circle 인사동;
    @FXML
    private Circle 재동초;
    @FXML
    private Circle 적선;
    @FXML
    private Circle 정동;
    @FXML
    private Circle 정부청사;
    @FXML
    private Circle 종로1;
    @FXML
    private Circle 종로2;
    @FXML
    private Circle 종로3;
    @FXML
    private Circle 창덕궁;
    @FXML
    private Circle 청계2;
    @FXML
    private Circle 청계3;
    @FXML
    private Circle 청계광장;
    @FXML
    private Circle 청사별관;
    @FXML
    private Circle 태평;
    @FXML
    private Circle 통의;
    @FXML
    private Circle 퇴계1;
    @FXML
    private Circle 퇴계2;
    @FXML
    private Circle 퇴계3;
    @FXML
    private Circle 포시즌;
    @FXML
    private Circle 한국은행;
    @FXML
    private Circle 환구단;
    
    @FXML
    public void onPlayClicked(ActionEvent event) throws InterruptedException{
        //service.scheduleAtFixedRate(shit, 0, 1, TimeUnit.SECONDS);
        //timeProgress = System.currentTimeLblMillis(); 
        //runging();
    }
    
    @FXML
    public void onNextClicked(ActionEvent event){
        if(vc.getCurrentTime() == null) {
            vc.setCurrentTime(vc.getInitialTime());
            vc.setCurrentTime(vc.getCurrentTime().plusMinutes(10));
        }
        else{
            vc.setCurrentTime(vc.getCurrentTime().plusMinutes(10));
        }
        currentTimeLbl.setText(vc.getCurrentTime().toString());
    }
    
    @FXML
    public void onPrevClicked(ActionEvent event){
        if(vc.getCurrentTime() != null && vc.getCurrentTime().isAfter(vc.getInitialTime())){
            vc.setCurrentTime(vc.getCurrentTime().minusMinutes(10));
            currentTimeLbl.setText(vc.getCurrentTime().toString());
        }
    }
    
    @FXML
    public void onBoostClicked(ActionEvent event){
        currentTimeLbl.setText(vc.getInitialTime().toString());
        vc.timeProgress = System.currentTimeMillis();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentTimeLbl.setText(vc.getInitialTime().toString());
    }  
}
