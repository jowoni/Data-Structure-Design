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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import static parade.route.dynamic.graph.ParadeRouteDynamicGraph.vc;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

/**
 *
 * @author Michael
 */
public class ViewController implements Initializable {

    private static Map<String, Map<String, Integer>> wholeGraph = new HashMap<>(); // 전체 지도
    private static Map<String, ParadeInfo> paradeInfoMap = new HashMap<>();        // 시위대명별 행진정보
    private static LocalDate simulationDate = null;
    private static LocalTime initialTime = null;
    private static LocalTime currentTime = null;
    
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

    // 중요한 함수들
    void setWholeGraph(Map<String, Map<String, Integer>> wholeGraph) {
        ViewController.wholeGraph = wholeGraph;
    } // set으로 시작하는 함수들은 거의 Data Input으로 받아온 정보들 이용해서 그래프와 환경 구성

    void setCurrentTime(LocalTime currentTime) {
        ViewController.currentTime = currentTime;
    }                       //

    void setParadeInfoMap(Map<String, ParadeInfo> paradeInfoMap) {
        ViewController.paradeInfoMap = paradeInfoMap;
    }    //

    void setSimulationDate(LocalDate simulationDate) {
        ViewController.simulationDate = simulationDate;
    }                //

    void setInitialTime(LocalTime initialTime) {
        ViewController.initialTime = initialTime;
    }                       //

    void nextTime() {
        vc.getInitialTime().plusMinutes(10);
        currentTimeLbl.setText(getInitialTime().toString());
    }

    void checkInProgressParades() {
        ArrayList tempParadeRoute;
        for (String key : vc.getParadeInfoMap().keySet()) { //행진정보별
            if ((vc.getParadeInfoMap().get(key).getStartTime().equals(vc.getCurrentTime()) || vc.getParadeInfoMap().get(key).getStartTime().isBefore(vc.getCurrentTime()))
                    && (vc.getParadeInfoMap().get(key).getEndTime().equals(vc.getCurrentTime()) || vc.getParadeInfoMap().get(key).getEndTime().isAfter(vc.getCurrentTime()))) {
                //StarTime <= currentTime <= EndTime 일 경우
                vc.getParadeInfoMap().get(key).setInProgress(true);
            } else {
                vc.getParadeInfoMap().get(key).setInProgress(false);
                
                //행진 안하는 행진 경로상의 노드들 회색으로 칠하기.
                tempParadeRoute = vc.getParadeInfoMap().get(key).getParadeRoute();
                for(int i = 0; i < tempParadeRoute.size() - 1; i++){
                    if (tempParadeRoute.get(i).equals("개풍")) {
                        개풍.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("경희궁")) {
                        경희궁.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("공평")) {
                        공평.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("광교")) {
                        광교.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("광화문")) {
                        광화문.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("금호")) {
                        금호.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("꽃집")) {
                        꽃집.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("낙원상가")) {
                        낙원상가.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("남대문")) {
                        남대문.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("남신육교")) {
                        남신육교.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("내자")) {
                        내자.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("눈스퀘어")) {
                        눈스퀘어.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("대한문")) {
                        대한문.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("덕수궁")) {
                        덕수궁.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("덕수초")) {
                        덕수초.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("독립문")) {
                        독립문.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("동십자")) {
                        동십자.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("동원")) {
                        동원.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("명동성당")) {
                        명동성당.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("명보")) {
                        명보.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("모전교")) {
                        모전교.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("문화거리")) {
                        문화거리.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("미대사관")) {
                        미대사관.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("박물관")) {
                        박물관.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("사직공원")) {
                        사직공원.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("서대문")) {
                        서대문.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("서린")) {
                        서린.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("서울역")) {
                        서울역.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("서울청")) {
                        서울청.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("세문관")) {
                        세문관.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("세문관뒤")) {
                        세문관뒤.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("세종")) {
                        세종.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("소공")) {
                        소공.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("신교")) {
                        신교.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("안국")) {
                        안국.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("안국역")) {
                        안국역.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("열린마당")) {
                        열린마당.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("염천")) {
                        염천.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("옥인")) {
                        옥인.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("을지1")) {
                        을지1.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("을지2")) {
                        을지2.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("을지3")) {
                        을지3.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("의주")) {
                        의주.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("인사동")) {
                        인사동.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("재동초")) {
                        재동초.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("적선")) {
                        적선.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("정동")) {
                        정동.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("정부청사")) {
                        정부청사.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("종로1")) {
                        종로1.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("종로2")) {
                        종로2.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("종로3")) {
                        종로3.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("창덕궁")) {
                        창덕궁.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("청계2")) {
                        청계2.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("청계3")) {
                        청계3.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("청계광장")) {
                        청계광장.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("청사별관")) {
                        청사별관.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("태평")) {
                        태평.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("통의")) {
                        통의.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("퇴계1")) {
                        퇴계1.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("퇴계2")) {
                        퇴계2.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("퇴계3")) {
                        퇴계3.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("포시즌")) {
                        포시즌.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("한국은행")) {
                        한국은행.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("환구단")) {
                        환구단.setFill(Color.rgb(232, 227, 227));
                    }
                }
            }
        }
    }   // 현재 진행중인 행진 확인

    void calcHeadDistanceAndName() {
        int accDistance;
        ArrayList tempParadeRoute;
        //for (String key : vc.getParadeInfoMap().keySet()) { //행진정보별
        String key = "태극기국민평의회";
        if (vc.getParadeInfoMap().get(key).getStartTime().isBefore(vc.getCurrentTime())) { //현재시간이 행진시작시간 이후일 때만
            vc.getParadeInfoMap().get(key).setHeadDistance( //Head 까지의 거리 계산
                    vc.getParadeInfoMap().get(key).getStartTime().until(vc.getCurrentTime(), ChronoUnit.MINUTES)
                    * vc.getParadeInfoMap().get(key).getParadeSpeed()
            ); //System.out.println(vc.getParadeInfoMap().get(key).getHeadDistance()); 
            accDistance = 0;
            tempParadeRoute = vc.getParadeInfoMap().get(key).getParadeRoute(); //행진정보별 행진경로ArrayList
            if (vc.getParadeInfoMap().get(key).getHeadDistance() <= vc.getParadeInfoMap().get(key).getRouteLength()) { //행진 Head가 경로 안에 있을때
                for (int i = 0; i < tempParadeRoute.size() - 1; i++) {               //행진정보별 행진경로ArrayList 순환
                    accDistance += vc.getWholeGraph().get(tempParadeRoute.get(i)).get(tempParadeRoute.get(i + 1));
                    if (i == 0 && accDistance >= vc.getParadeInfoMap().get(key).getHeadDistance()) { //Head가 방금 출발해서 처음 노드 도달도 못한 경우(Head와 첫번째 노드가 같은 경우도 포함)
                        vc.getParadeInfoMap().get(key).setHeadName(tempParadeRoute.get(i + 1).toString());
                        //System.out.println(accDistance);
                        break;
                    } else if (accDistance < vc.getParadeInfoMap().get(key).getHeadDistance() && vc.getParadeInfoMap().get(key).getHeadDistance() < accDistance + vc.getWholeGraph().get(tempParadeRoute.get(i + 1)).get(tempParadeRoute.get(i + 2))) {
                        //Head거리가 accDistance, accDistance(Next)사이일때         이건 필요없을듯?! i+2이 행진경로 ArrayList 사이즈 이하일때(마지막 노드일 경우 발생)
                        vc.getParadeInfoMap().get(key).setHeadName(tempParadeRoute.get(i + 2).toString());
                        //System.out.println(accDistance);
                        break;
                    }
                    //else if() Head와 노드가 같은 경우
                }
            } else { //행진 Head가 경로밖에 있을때
                vc.getParadeInfoMap().get(key).setHeadName(vc.getParadeInfoMap().get(key).getParadeRoute().get(vc.getParadeInfoMap().get(key).getParadeRoute().size()-1));
                //행진 HeadName은 행진경로 ArrayList 마지막 노드로 저장
            }
        } else if (vc.getParadeInfoMap().get(key).getStartTime().equals(vc.getCurrentTime())) { //행진시작시간이 현재시간과 같을때
            vc.getParadeInfoMap().get(key).setHeadDistance(0); //Head까지 거리 = 0 그리고 HeadName은 행진경로 첫번째 노드
            vc.getParadeInfoMap().get(key).setHeadName(vc.getParadeInfoMap().get(key).getParadeRoute().get(0));
            //         break;
        } else if ((vc.getParadeInfoMap().get(key).getEndTime()).equals(vc.getCurrentTime())) { //행진종료시간이 현재시간과 같을때
            vc.getParadeInfoMap().get(key).setHeadDistance(vc.getParadeInfoMap().get(key).getRouteLength());
            vc.getParadeInfoMap().get(key).setHeadName(vc.getParadeInfoMap().get(key).getParadeRoute().get(vc.getParadeInfoMap().get(key).getParadeRoute().size()-1));

        } else if ((vc.getParadeInfoMap().get(key).getEndTime()).isBefore(vc.getCurrentTime())) { //현재시간이 행진종료시간 후일때(행진이 종료)
            //해당 행진의 모든것을 null로 되돌려버리는 알고리즘.
            vc.getParadeInfoMap().get(key).setHeadName(null);
            vc.getParadeInfoMap().get(key).setHeadDistance(0);
        }
        //}
        //System.out.println(vc.getParadeInfoMap().get(key).getHeadName()); 
    }   // 행진의 Head의 Distance와 Head 교차로 계산

    void calcTailDistanceAndName() {
        int accDistance;
        ArrayList tempParadeRoute;
        String key = "태극기국민평의회";
        //for (String key : vc.getParadeInfoMap().keySet()) { //행진정보별
        if (vc.getParadeInfoMap().get(key).getStartTime().isBefore(vc.getCurrentTime()) //현재시간이 행진시작시간 이후이고 Tail(Head - 행진길이) >= 0 일때
                && vc.getParadeInfoMap().get(key).getHeadDistance() - vc.getParadeInfoMap().get(key).getParadeLength() >= 0) {
            vc.getParadeInfoMap().get(key).setTailDistance(vc.getParadeInfoMap().get(key).getHeadDistance() - vc.getParadeInfoMap().get(key).getParadeLength()); //Tail까지 거리 계산
            if (vc.getParadeInfoMap().get(key).getTailDistance() == 0) { //Tail까지 거리가 0일때
                vc.getParadeInfoMap().get(key).setTailName(vc.getParadeInfoMap().get(key).getParadeRoute().get(0)); //행진경로 첫번째 노드를 TailName으로 설정
            } else if (vc.getParadeInfoMap().get(key).getTailDistance() < vc.getParadeInfoMap().get(key).getRouteLength()) { //Tail이 행진 경로 안에 있을때
                accDistance = 0;
                tempParadeRoute = vc.getParadeInfoMap().get(key).getParadeRoute(); //행진정보별 행진경로ArrayList
                for (int i = 0; i < tempParadeRoute.size() - 1; i++) {             //행진정보별 행진경로ArrayList 순환
                    accDistance += vc.getWholeGraph().get(tempParadeRoute.get(i)).get(tempParadeRoute.get(i + 1));
                    if (i == 0 && accDistance >= vc.getParadeInfoMap().get(key).getTailDistance()) { //Tail이 방금 출발해서 처음 노드 도달도 못한 경우 (Tail이 첫번째 노드와 같은 경우도 포함)
                        vc.getParadeInfoMap().get(key).setTailName(tempParadeRoute.get(i + 1).toString());
                        //                    break;
                    } else if (accDistance < vc.getParadeInfoMap().get(key).getTailDistance() && vc.getParadeInfoMap().get(key).getTailDistance() < accDistance + vc.getWholeGraph().get(tempParadeRoute.get(i + 1)).get(tempParadeRoute.get(i + 2))) {
                        //Tail거리가 accDistance, accDistance(Next)사이일때         이건 필요없을듯?! i+2이 행진경로 ArrayList 사이즈 이하일때(마지막 노드일 경우 발생)
                        vc.getParadeInfoMap().get(key).setTailName(tempParadeRoute.get(i + 2).toString());
                        //                    break;
                    }
                }
            } else { //Tail이 경로밖에 있을때
                vc.getParadeInfoMap().get(key).setTailName(vc.getParadeInfoMap().get(key).getParadeRoute().get(vc.getParadeInfoMap().get(key).getParadeRoute().size()-1));
                //TailName은 행진경로 ArrayList 마지막 노드로 저장
            }
        } else if ((vc.getParadeInfoMap().get(key).getEndTime()).equals(vc.getCurrentTime())) { //행진종료시간이 현재시간과 같을때
            vc.getParadeInfoMap().get(key).setTailName(vc.getParadeInfoMap().get(key).getParadeRoute().get(vc.getParadeInfoMap().get(key).getParadeRoute().size()-1));

        } else if ((vc.getParadeInfoMap().get(key).getEndTime()).isBefore(vc.getCurrentTime())) { //현재시간이 행진종료시간 후일때(행진이 종료)
            //해당 행진의 모든것을 null로 되돌려버리는 알고리즘.
            vc.getParadeInfoMap().get(key).setTailName(null);
            vc.getParadeInfoMap().get(key).setTailDistance(0);
        }
        //}
    }   // 행진의 Tail의 Distance와 Tail 교차로 계산                       

    void paintActivatedNodes() {
        ArrayList tempParadeRoute;
        //for (String key : vc.getParadeInfoMap().keySet()) { //행진정보별
        String key = "태극기국민평의회";
        if (vc.getParadeInfoMap().get(key).getInProgress() == true) { //행진이 현재 진행중일때
            tempParadeRoute = vc.getParadeInfoMap().get(key).getParadeRoute(); //행진정보별 행진경로ArrayList
            if (vc.getParadeInfoMap().get(key).getTailName() == null) {
                for (int i = 0; i <= tempParadeRoute.indexOf(vc.getParadeInfoMap().get(key).getHeadName()); i++) { //Tail이 아직 존재 안하는 경우
                    //행진이 진행중인 경로의 노드들을 Activate; 
                    System.out.println(vc.getParadeInfoMap().get(key).getHeadName());
                    if (tempParadeRoute.get(i).equals("개풍")) {
                        개풍.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("경희궁")) {
                        경희궁.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("공평")) {
                        공평.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("광교")) {
                        광교.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("광화문")) {
                        광화문.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("금호")) {
                        금호.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("꽃집")) {
                        꽃집.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("낙원상가")) {
                        낙원상가.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("남대문")) {
                        남대문.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("남신육교")) {
                        남신육교.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("내자")) {
                        내자.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("눈스퀘어")) {
                        눈스퀘어.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("대한문")) {
                        대한문.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("덕수궁")) {
                        덕수궁.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("덕수초")) {
                        덕수초.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("독립문")) {
                        독립문.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("동십자")) {
                        동십자.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("동원")) {
                        동원.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("명동성당")) {
                        명동성당.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("명보")) {
                        명보.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("모전교")) {
                        모전교.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("문화거리")) {
                        문화거리.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("미대사관")) {
                        미대사관.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("박물관")) {
                        박물관.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("사직공원")) {
                        사직공원.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("서대문")) {
                        서대문.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("서린")) {
                        서린.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("서울역")) {
                        서울역.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("서울청")) {
                        서울청.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("세문관")) {
                        세문관.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("세문관뒤")) {
                        세문관뒤.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("세종")) {
                        세종.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("소공")) {
                        소공.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("신교")) {
                        신교.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("안국")) {
                        안국.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("안국역")) {
                        안국역.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("열린마당")) {
                        열린마당.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("염천")) {
                        염천.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("옥인")) {
                        옥인.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("을지1")) {
                        을지1.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("을지2")) {
                        을지2.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("을지3")) {
                        을지3.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("의주")) {
                        의주.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("인사동")) {
                        인사동.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("재동초")) {
                        재동초.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("적선")) {
                        적선.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("정동")) {
                        정동.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("정부청사")) {
                        정부청사.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("종로1")) {
                        종로1.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("종로2")) {
                        종로2.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("종로3")) {
                        종로3.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("창덕궁")) {
                        창덕궁.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("청계2")) {
                        청계2.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("청계3")) {
                        청계3.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("청계광장")) {
                        청계광장.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("청사별관")) {
                        청사별관.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("태평")) {
                        태평.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("통의")) {
                        통의.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("퇴계1")) {
                        퇴계1.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("퇴계2")) {
                        퇴계2.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("퇴계3")) {
                        퇴계3.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("포시즌")) {
                        포시즌.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("한국은행")) {
                        한국은행.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("환구단")) {
                        환구단.setFill(Color.AQUA);
                    }
                }
            } else {
                for (int i = tempParadeRoute.indexOf(vc.getParadeInfoMap().get(key).getTailName()); i <= tempParadeRoute.indexOf(vc.getParadeInfoMap().get(key).getHeadName()); i++) {
                    //행진이 진행중인 경로의 노드들을 Activate; Tail이 존재 하는 경우.
                    if (tempParadeRoute.get(i).equals("개풍")) {
                        개풍.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("경희궁")) {
                        경희궁.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("공평")) {
                        공평.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("광교")) {
                        광교.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("광화문")) {
                        광화문.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("금호")) {
                        금호.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("꽃집")) {
                        꽃집.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("낙원상가")) {
                        낙원상가.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("남대문")) {
                        남대문.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("남신육교")) {
                        남신육교.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("내자")) {
                        내자.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("눈스퀘어")) {
                        눈스퀘어.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("대한문")) {
                        대한문.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("덕수궁")) {
                        덕수궁.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("덕수초")) {
                        덕수초.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("독립문")) {
                        독립문.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("동십자")) {
                        동십자.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("동원")) {
                        동원.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("명동성당")) {
                        명동성당.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("명보")) {
                        명보.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("모전교")) {
                        모전교.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("문화거리")) {
                        문화거리.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("미대사관")) {
                        미대사관.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("박물관")) {
                        박물관.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("사직공원")) {
                        사직공원.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("서대문")) {
                        서대문.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("서린")) {
                        서린.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("서울역")) {
                        서울역.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("서울청")) {
                        서울청.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("세문관")) {
                        세문관.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("세문관뒤")) {
                        세문관뒤.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("세종")) {
                        세종.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("소공")) {
                        소공.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("신교")) {
                        신교.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("안국")) {
                        안국.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("안국역")) {
                        안국역.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("열린마당")) {
                        열린마당.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("염천")) {
                        염천.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("옥인")) {
                        옥인.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("을지1")) {
                        을지1.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("을지2")) {
                        을지2.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("을지3")) {
                        을지3.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("의주")) {
                        의주.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("인사동")) {
                        인사동.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("재동초")) {
                        재동초.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("적선")) {
                        적선.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("정동")) {
                        정동.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("정부청사")) {
                        정부청사.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("종로1")) {
                        종로1.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("종로2")) {
                        종로2.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("종로3")) {
                        종로3.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("창덕궁")) {
                        창덕궁.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("청계2")) {
                        청계2.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("청계3")) {
                        청계3.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("청계광장")) {
                        청계광장.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("청사별관")) {
                        청사별관.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("태평")) {
                        태평.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("통의")) {
                        통의.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("퇴계1")) {
                        퇴계1.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("퇴계2")) {
                        퇴계2.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("퇴계3")) {
                        퇴계3.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("포시즌")) {
                        포시즌.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("한국은행")) {
                        한국은행.setFill(Color.AQUA);
                    } else if (tempParadeRoute.get(i).equals("환구단")) {
                        환구단.setFill(Color.AQUA);
                    }
                }
                for (int i = 0; i <= tempParadeRoute.indexOf(vc.getParadeInfoMap().get(key).getTailName()) - 1; i++) { //행진이 지나간 Node들을 Deactivate; Tail이 존재 하는 경우.
                    if (tempParadeRoute.get(i).equals("개풍")) {
                        개풍.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("경희궁")) {
                        경희궁.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("공평")) {
                        공평.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("광교")) {
                        광교.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("광화문")) {
                        광화문.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("금호")) {
                        금호.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("꽃집")) {
                        꽃집.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("낙원상가")) {
                        낙원상가.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("남대문")) {
                        남대문.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("남신육교")) {
                        남신육교.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("내자")) {
                        내자.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("눈스퀘어")) {
                        눈스퀘어.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("대한문")) {
                        대한문.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("덕수궁")) {
                        덕수궁.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("덕수초")) {
                        덕수초.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("독립문")) {
                        독립문.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("동십자")) {
                        동십자.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("동원")) {
                        동원.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("명동성당")) {
                        명동성당.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("명보")) {
                        명보.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("모전교")) {
                        모전교.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("문화거리")) {
                        문화거리.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("미대사관")) {
                        미대사관.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("박물관")) {
                        박물관.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("사직공원")) {
                        사직공원.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("서대문")) {
                        서대문.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("서린")) {
                        서린.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("서울역")) {
                        서울역.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("서울청")) {
                        서울청.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("세문관")) {
                        세문관.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("세문관뒤")) {
                        세문관뒤.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("세종")) {
                        세종.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("소공")) {
                        소공.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("신교")) {
                        신교.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("안국")) {
                        안국.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("안국역")) {
                        안국역.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("열린마당")) {
                        열린마당.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("염천")) {
                        염천.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("옥인")) {
                        옥인.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("을지1")) {
                        을지1.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("을지2")) {
                        을지2.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("을지3")) {
                        을지3.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("의주")) {
                        의주.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("인사동")) {
                        인사동.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("재동초")) {
                        재동초.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("적선")) {
                        적선.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("정동")) {
                        정동.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("정부청사")) {
                        정부청사.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("종로1")) {
                        종로1.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("종로2")) {
                        종로2.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("종로3")) {
                        종로3.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("창덕궁")) {
                        창덕궁.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("청계2")) {
                        청계2.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("청계3")) {
                        청계3.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("청계광장")) {
                        청계광장.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("청사별관")) {
                        청사별관.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("태평")) {
                        태평.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("통의")) {
                        통의.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("퇴계1")) {
                        퇴계1.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("퇴계2")) {
                        퇴계2.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("퇴계3")) {
                        퇴계3.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("포시즌")) {
                        포시즌.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("한국은행")) {
                        한국은행.setFill(Color.rgb(232, 227, 227));
                    } else if (tempParadeRoute.get(i).equals("환구단")) {
                        환구단.setFill(Color.rgb(232, 227, 227));
                    }
                }
            }
        }
        //}
    }       // 현재 행진이 지나가고 있는 노드들 색 변화

    //버튼
    @FXML
    private Button play;
    @FXML
    private Button prev;
    @FXML
    private Button next;
    @FXML
    private Button reset;

    //레이블
    @FXML
    Label currentTimeLbl;
    @FXML
    Label currentDateLbl;

    //그래프의 노드를 Circle로 표현. 총64개.
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
        paintActivatedNodes();
    } //Next 버튼을 누르면 현재시간 10분을 추가하고 그래프가 변화.

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
