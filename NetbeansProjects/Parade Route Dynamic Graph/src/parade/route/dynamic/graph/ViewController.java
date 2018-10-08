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
import javafx.scene.paint.Color;

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
    Map<String, Map<String, Integer>> getWholeGraph() {
        return wholeGraph;
    }

    Map<String, ParadeInfo> getParadeInfoMap() {
        return paradeInfoMap;
    }

    LocalDate getSimulationDate() {
        return simulationDate;
    }

    LocalTime getInitialTime() {
        return initialTime;
    }

    LocalTime getCurrentTime() {
        return currentTime;
    }

    void setWholeGraph(Map<String, Map<String, Integer>> wholeGraph) {
        ViewController.wholeGraph = wholeGraph;
    }

    void setCurrentTime(LocalTime currentTime) {
        ViewController.currentTime = currentTime;
    }

    void setParadeInfoMap(Map<String, ParadeInfo> paradeInfoMap) {
        ViewController.paradeInfoMap = paradeInfoMap;
    }

    void setSimulationDate(LocalDate simulationDate) {
        ViewController.simulationDate = simulationDate;
    }

    void setInitialTime(LocalTime initialTime) {
        ViewController.initialTime = initialTime;
    }

    void nextTime() {
        vc.getInitialTime().plusMinutes(10);
        currentTimeLbl.setText(getInitialTime().toString());
    }

    void checkInProgressParades() {
        for (String key : vc.getParadeInfoMap().keySet()) { //행진정보별
            if (vc.getParadeInfoMap().get(key).getStartTime() == vc.getCurrentTime() || vc.getParadeInfoMap().get(key).getStartTime().isAfter(vc.getCurrentTime())
                    && vc.getParadeInfoMap().get(key).getEndTime() == vc.getCurrentTime() || vc.getParadeInfoMap().get(key).getEndTime().isBefore(vc.getCurrentTime())) {
                //StarTime <= currentTime <= EndTime 일 경우
                vc.getParadeInfoMap().get(key).setInProgress(true);
            } else {
                vc.getParadeInfoMap().get(key).setInProgress(false);
            }
        }
    }

    void calcHeadDistanceAndName() {
        int accDistance;
        ArrayList tempParadeRoute;
        for (String key : vc.getParadeInfoMap().keySet()) { //행진정보별
            if (vc.getParadeInfoMap().get(key).getStartTime().isAfter(vc.getCurrentTime())) { //행진시작시간이 현재시간보다 이후일 때만
                vc.getParadeInfoMap().get(key).setHeadDistance( //Head 까지의 거리 계산
                        vc.getParadeInfoMap().get(key).getStartTime().until(vc.getCurrentTime(), ChronoUnit.MINUTES)
                        * vc.getParadeInfoMap().get(key).getParadeSpeed()
                );
                accDistance = 0;
                tempParadeRoute = vc.getParadeInfoMap().get(key).getParadeRoute(); //행진정보별 행진경로ArrayList
                if (vc.getParadeInfoMap().get(key).getHeadDistance() < vc.getParadeInfoMap().get(key).getRouteLength()) { //행진 Head가 경로 안에 있을때
                    for (int i = 0; i < tempParadeRoute.size() - 1; i++) {               //행진정보별 행진경로ArrayList 순환
                        accDistance += vc.getWholeGraph().get(tempParadeRoute.get(i)).get(tempParadeRoute.get(i + 1));
                        if (accDistance >= vc.getParadeInfoMap().get(key).getHeadDistance()) { //Head가 방금 출발해서 처음 노드 도달도 못한 경우 & Head와 노드가 같은 경우
                            vc.getParadeInfoMap().get(key).setHeadName(tempParadeRoute.get(i + 1).toString());
                            break;
                        } else if (accDistance < vc.getParadeInfoMap().get(key).getHeadDistance() && accDistance > vc.getWholeGraph().get(tempParadeRoute.get(i + 1)).get(tempParadeRoute.get(i + 2))) {//&& i+2 <= tempParadeRoute.size()){
                            //Head거리가 accDistance, accDistance(Next)사이일때         이건 필요없을듯?! i+2이 행진경로 ArrayList 사이즈 이하일때(마지막 노드일 경우 발생)
                            vc.getParadeInfoMap().get(key).setHeadName(tempParadeRoute.get(i + 2).toString());
                            break;
                        }
                    }
                } else { //행진 Head가 경로밖에 있을때
                    vc.getParadeInfoMap().get(key).setHeadName(vc.getParadeInfoMap().get(key).getParadeRoute().get(vc.getParadeInfoMap().get(key).getParadeRoute().size()));
                    //행진 HeadName은 행진경로 ArrayList 마지막 노드로 저장
                }
            } else if (vc.getParadeInfoMap().get(key).getStartTime() == vc.getCurrentTime()) { //행진시작시간이 현재시간과 같을때
                vc.getParadeInfoMap().get(key).setHeadDistance(0); //Head까지 거리 = 0 그리고 HeadName은 행진경로 첫번째 노드
                vc.getParadeInfoMap().get(key).setHeadName(vc.getParadeInfoMap().get(key).getParadeRoute().get(0));
                break;
            } else if ((vc.getParadeInfoMap().get(key).getEndTime()) == vc.getCurrentTime()) { //행진종료시간이 현재시간과 같을때

            } else if ((vc.getParadeInfoMap().get(key).getEndTime()).isBefore(vc.getCurrentTime())) { //행진종료시간이 현재시간 전일때(행진이 종료)
                //해당 행진의 모든것을 null로 되돌려버리는 알고리즘.
            }
        }
    }

    void calcTailDistanceAndName() {
        int accDistance;
        ArrayList tempParadeRoute;
        for (String key : vc.getParadeInfoMap().keySet()) { //행진정보별
            if (vc.getParadeInfoMap().get(key).getStartTime().isAfter(vc.getCurrentTime()) //행진시작시간이 현재시간보다 이후이고 Tail(Head - 행진길이) >= 0 일때
                    && vc.getParadeInfoMap().get(key).getHeadDistance() - vc.getParadeInfoMap().get(key).getParadeLength() >= 0) {
                vc.getParadeInfoMap().get(key).setTailDistance(vc.getParadeInfoMap().get(key).getHeadDistance() - vc.getParadeInfoMap().get(key).getParadeLength()); //Tail까지 거리 계산
                if (vc.getParadeInfoMap().get(key).getTailDistance() == 0) { //Tail까지 거리가 0일때
                    vc.getParadeInfoMap().get(key).setTailName(vc.getParadeInfoMap().get(key).getParadeRoute().get(0)); //행진경로 첫번째 노드를 TailName으로 설정
                } else if (vc.getParadeInfoMap().get(key).getTailDistance() < vc.getParadeInfoMap().get(key).getRouteLength()) { //Tail이 행진 경로 안에 있을때
                    accDistance = 0;
                    tempParadeRoute = vc.getParadeInfoMap().get(key).getParadeRoute(); //행진정보별 행진경로ArrayList
                    for (int i = 0; i < tempParadeRoute.size() - 1; i++) {               //행진정보별 행진경로ArrayList 순환
                        accDistance += vc.getWholeGraph().get(tempParadeRoute.get(i)).get(tempParadeRoute.get(i + 1));
                        if (accDistance >= vc.getParadeInfoMap().get(key).getTailDistance()) { //Tail이 방금 출발해서 처음 노드 도달도 못한 경우 & Tail이 노드가 같은 경우
                            vc.getParadeInfoMap().get(key).setTailName(tempParadeRoute.get(i + 1).toString());
                            break;
                        } else if (accDistance < vc.getParadeInfoMap().get(key).getTailDistance() && accDistance > vc.getWholeGraph().get(tempParadeRoute.get(i + 1)).get(tempParadeRoute.get(i + 2))) {//&& i+2 <= tempParadeRoute.size()){
                            //Tail거리가 accDistance, accDistance(Next)사이일때         이건 필요없을듯?! i+2이 행진경로 ArrayList 사이즈 이하일때(마지막 노드일 경우 발생)
                            vc.getParadeInfoMap().get(key).setTailName(tempParadeRoute.get(i + 2).toString());
                            break;
                        }
                    }
                } else { //행진 Tail이 경로밖에 있을때
                    vc.getParadeInfoMap().get(key).setTailName(vc.getParadeInfoMap().get(key).getParadeRoute().get(vc.getParadeInfoMap().get(key).getParadeRoute().size()));
                    //TailName은 행진경로 ArrayList 마지막 노드로 저장
                    break; //굳이 안해도 어차피 마지막이니까 상관 없을것 같은데 혹시 모르니까...
                }
            }
        }
    }

    void calcActivatedNodes() {
        ArrayList tempParadeRoute;
        ArrayList routeInProgress = new ArrayList<String>();
        for (String key : vc.getParadeInfoMap().keySet()) { //행진정보별
            if (vc.getParadeInfoMap().get(key).getInProgress() == true) { //행진이 현재 진행중일때
                tempParadeRoute = vc.getParadeInfoMap().get(key).getParadeRoute(); //행진정보별 행진경로ArrayList
                for (int i = tempParadeRoute.indexOf(vc.getParadeInfoMap().get(key).getTailName()); i <= tempParadeRoute.indexOf(vc.getParadeInfoMap().get(key).getHeadName()); i++) {
                    //행진이 진행중인 경로의 노드들을 Activate; 
                    switch (tempParadeRoute.get(i).toString()) {
                        case "개풍":
                            개풍.setFill(Color.AQUA);
                            break;
                        case "경희궁":
                            경희궁.setFill(Color.AQUA);
                            break;
                        case "공평":
                            공평.setFill(Color.AQUA);
                            break;
                        case "광교":
                            광교.setFill(Color.AQUA);
                            break;
                        case "광화문":
                            광화문.setFill(Color.AQUA);
                            break;
                        case "금호":
                            금호.setFill(Color.AQUA);
                            break;
                        case "꽃집":
                            꽃집.setFill(Color.AQUA);
                            break;
                        case "낙원상가":
                            낙원상가.setFill(Color.AQUA);
                            break;
                        case "남대문":
                            남대문.setFill(Color.AQUA);
                            break;
                        case "남신육교":
                            남신육교.setFill(Color.AQUA);
                            break;
                        case "내자":
                            내자.setFill(Color.AQUA);
                            break;
                        case "눈스퀘어":
                            눈스퀘어.setFill(Color.AQUA);
                            break;
                        case "대한문":
                            대한문.setFill(Color.AQUA);
                            break;
                        case "덕수궁":
                            덕수궁.setFill(Color.AQUA);
                            break;
                        case "덕수초":
                            덕수초.setFill(Color.AQUA);
                            break;
                        case "독립문":
                            독립문.setFill(Color.AQUA);
                            break;
                        case "동십자":
                            동십자.setFill(Color.AQUA);
                            break;
                        case "동원":
                            동원.setFill(Color.AQUA);
                            break;
                        case "명동성당":
                            명동성당.setFill(Color.AQUA);
                            break;
                        case "명보":
                            명보.setFill(Color.AQUA);
                            break;
                        case "모전교":
                            모전교.setFill(Color.AQUA);
                            break;
                        case "문화거리":
                            문화거리.setFill(Color.AQUA);
                            break;
                        case "미대사관":
                            미대사관.setFill(Color.AQUA);
                            break;
                        case "박물관":
                            박물관.setFill(Color.AQUA);
                            break;
                        case "사직공원":
                            사직공원.setFill(Color.AQUA);
                            break;
                        case "서대문":
                            서대문.setFill(Color.AQUA);
                            break;
                        case "서린":
                            서린.setFill(Color.AQUA);
                            break;
                        case "서울역":
                            서울역.setFill(Color.AQUA);
                            break;
                        case "서울청":
                            서울청.setFill(Color.AQUA);
                            break;
                        case "세문관":
                            세문관.setFill(Color.AQUA);
                            break;
                        case "세문관뒤":
                            세문관뒤.setFill(Color.AQUA);
                            break;
                        case "세종":
                            세종.setFill(Color.AQUA);
                            break;
                        case "소공":
                            소공.setFill(Color.AQUA);
                            break;
                        case "신교":
                            신교.setFill(Color.AQUA);
                            break;
                        case "안국":
                            안국.setFill(Color.AQUA);
                            break;
                        case "안국역":
                            안국역.setFill(Color.AQUA);
                            break;
                        case "열린마당":
                            열린마당.setFill(Color.AQUA);
                            break;
                        case "염천":
                            염천.setFill(Color.AQUA);
                            break;
                        case "옥인":
                            옥인.setFill(Color.AQUA);
                            break;
                        case "을지1":
                            을지1.setFill(Color.AQUA);
                            break;
                        case "을지2":
                            을지2.setFill(Color.AQUA);
                            break;
                        case "을지3":
                            을지3.setFill(Color.AQUA);
                            break;
                        case "의주":
                            의주.setFill(Color.AQUA);
                            break;
                        case "인사동":
                            인사동.setFill(Color.AQUA);
                            break;
                        case "재동초":
                            재동초.setFill(Color.AQUA);
                            break;
                        case "적선":
                            적선.setFill(Color.AQUA);
                            break;
                        case "정동":
                            정동.setFill(Color.AQUA);
                            break;
                        case "정부청사":
                            정부청사.setFill(Color.AQUA);
                            break;
                        case "종로1":
                            종로1.setFill(Color.AQUA);
                            break;
                        case "종로2":
                            종로2.setFill(Color.AQUA);
                            break;
                        case "종로3":
                            종로3.setFill(Color.AQUA);
                            break;
                        case "창덕궁":
                            창덕궁.setFill(Color.AQUA);
                            break;
                        case "청계2":
                            청계2.setFill(Color.AQUA);
                            break;
                        case "청계3":
                            청계3.setFill(Color.AQUA);
                            break;
                        case "청계광장":
                            청계광장.setFill(Color.AQUA);
                            break;
                        case "청사별관":
                            청사별관.setFill(Color.AQUA);
                            break;
                        case "태평":
                            태평.setFill(Color.AQUA);
                            break;
                        case "통의":
                            통의.setFill(Color.AQUA);
                            break;
                        case "퇴계1":
                            퇴계1.setFill(Color.AQUA);
                            break;
                        case "퇴계2":
                            퇴계2.setFill(Color.AQUA);
                            break;
                        case "퇴계3":
                            퇴계3.setFill(Color.AQUA);
                            break;
                        case "포시즌":
                            포시즌.setFill(Color.AQUA);
                            break;
                        case "한국은행":
                            한국은행.setFill(Color.AQUA);
                            break;
                        case "환구단":
                            환구단.setFill(Color.AQUA);
                            break;
                    }
                }
            }
        }
    }

    @FXML
    private Button play;
    @FXML
    private Button prev;
    @FXML
    private Button next;
    @FXML
    private Button reset;

    @FXML
    Label currentTimeLbl;
    @FXML
    Label currentDateLbl;

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
    public void onPlayClicked(ActionEvent event) throws InterruptedException {
        //service.scheduleAtFixedRate(shit, 0, 1, TimeUnit.SECONDS);
        //timeProgress = System.currentTimeLblMillis(); 
        //runging();
    }

    @FXML
    public void onNextClicked(ActionEvent event) {
        if (vc.getCurrentTime() == null) { //currentTime이 없을때. 초시 시간일때
            vc.setCurrentTime(vc.getInitialTime());
            vc.setCurrentTime(vc.getCurrentTime().plusMinutes(10));
        } else {
            vc.setCurrentTime(vc.getCurrentTime().plusMinutes(10));
        }
        currentTimeLbl.setText(vc.getCurrentTime().toString());
        checkInProgressParades();
        calcHeadDistanceAndName();
        calcTailDistanceAndName();
        calcActivatedNodes();
    }

    @FXML
    public void onPrevClicked(ActionEvent event) {
        if (vc.getCurrentTime() != null && vc.getCurrentTime().isAfter(vc.getInitialTime())) {
            vc.setCurrentTime(vc.getCurrentTime().minusMinutes(10));
            currentTimeLbl.setText(vc.getCurrentTime().toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentTimeLbl.setText(vc.getInitialTime().toString());
    }
}
