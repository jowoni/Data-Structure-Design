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
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import static parade.route.dynamic.graph.ParadeRouteDynamicGraph.input;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineBuilder;

/**
 *
 * @author Michael
 */
public class ViewController implements Initializable {

    private static Map<String, Map<String, Integer>> wholeGraph = input.getWholeGraph(); // 전체 지도
    private static Map<String, ParadeInfo> paradeInfoMap = input.getParadeInfoMap();     // 시위대명별 행진정보
    private static LocalDate simulationDate = input.getDate();
    private static LocalTime currentTime = null;
    private static String currentParade;

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

    void nextTime() {
        getInitialTime().plusMinutes(10);
        currentTimeLbl.setText(getInitialTime().toString());
    }

    void checkInProgressParades() {
        ArrayList tempParadeRoute;
        for (String key : getParadeInfoMap().keySet()) { //행진정보별
            if ((getParadeInfoMap().get(key).getStartTime().equals(getCurrentTime()) || getParadeInfoMap().get(key).getStartTime().isBefore(getCurrentTime()))
                    && (getParadeInfoMap().get(key).getEndTime().equals(getCurrentTime()) || getParadeInfoMap().get(key).getEndTime().isAfter(getCurrentTime()))) {
                //StarTime <= currentTime <= EndTime 일 경우
                getParadeInfoMap().get(key).setInProgress(true);
            } else { //행진 안하는 행진 경로상의 노드들 회색으로 칠하기.
                getParadeInfoMap().get(key).setInProgress(false);
                resetAllNodeColor();
            }
        }
    }   // 현재 진행중인 행진 확인

    void resetAllNodeColor() {
        ArrayList tempParadeRoute;
        for (String key : getParadeInfoMap().keySet()) { //행진정보별
            tempParadeRoute = getParadeInfoMap().get(key).getParadeRoute();
            for (int i = 0; i < tempParadeRoute.size() - 1; i++) {
                inputStringReturnCircle(tempParadeRoute.get(i).toString()).setFill(Color.rgb(232, 227, 227));
            }
        }
    } //모든 노드 기본 회색으로 칠하기

    void calcHeadDistanceAndName() {
        int accDistance;
        ArrayList tempParadeRoute;
        for (String key : getParadeInfoMap().keySet()) { //행진정보별
            if (getParadeInfoMap().get(key).getStartTime().isBefore(getCurrentTime())
                    && ((getParadeInfoMap().get(key).getEndTime()).equals(getCurrentTime()) || (getParadeInfoMap().get(key).getEndTime()).isAfter(getCurrentTime()))) { //현재시간이 행진시작시간 이후일 때만
                getParadeInfoMap().get(key).setHeadDistance( //Head 까지의 거리 계산
                        getParadeInfoMap().get(key).getStartTime().until(getCurrentTime(), ChronoUnit.MINUTES)
                        * getParadeInfoMap().get(key).getParadeSpeed());
                accDistance = 0;
                tempParadeRoute = getParadeInfoMap().get(key).getParadeRoute(); //행진정보별 행진경로ArrayList
                if (getParadeInfoMap().get(key).getHeadDistance() <= getParadeInfoMap().get(key).getRouteLength()) { //행진 Head가 경로 안에 있을때
                    for (int i = 0; i < tempParadeRoute.size() - 1; i++) {               //행진정보별 행진경로ArrayList 순환
                        accDistance += getWholeGraph().get(tempParadeRoute.get(i)).get(tempParadeRoute.get(i + 1));
                        if (i == 0 && accDistance >= getParadeInfoMap().get(key).getHeadDistance()) { //Head가 방금 출발해서 처음 노드 도달도 못한 경우(Head와 첫번째 노드가 같은 경우도 포함)
                            getParadeInfoMap().get(key).setHeadName(tempParadeRoute.get(i + 1).toString());
                            break;
                        } else if (accDistance < getParadeInfoMap().get(key).getHeadDistance() && getParadeInfoMap().get(key).getHeadDistance() < accDistance + getWholeGraph().get(tempParadeRoute.get(i + 1)).get(tempParadeRoute.get(i + 2))) {
                            //Head거리가 accDistance, accDistance(Next)사이일때         
                            getParadeInfoMap().get(key).setHeadName(tempParadeRoute.get(i + 2).toString());
                            break;
                        } else if (accDistance == getParadeInfoMap().get(key).getHeadDistance()) { //Head가 완전히 교차로 위에 있을때
                            getParadeInfoMap().get(key).setHeadName(tempParadeRoute.get(i + 1).toString());
                            break;
                        }
                    }
                } else { //행진 Head가 경로밖에 있을때
                    getParadeInfoMap().get(key).setHeadName(getParadeInfoMap().get(key).getParadeRoute().get(getParadeInfoMap().get(key).getParadeRoute().size() - 1));
                    //행진 HeadName은 행진경로 ArrayList 마지막 노드로 저장
                }
            } else if (getParadeInfoMap().get(key).getStartTime().equals(getCurrentTime())) { //행진시작시간이 현재시간과 같을때
                getParadeInfoMap().get(key).setHeadDistance(0); //Head까지 거리 = 0 그리고 HeadName은 행진경로 첫번째 노드
                getParadeInfoMap().get(key).setHeadName(getParadeInfoMap().get(key).getParadeRoute().get(0));
                //         break;
            } else if ((getParadeInfoMap().get(key).getEndTime()).equals(getCurrentTime())) { //행진종료시간이 현재시간과 같을때
                getParadeInfoMap().get(key).setHeadDistance(getParadeInfoMap().get(key).getRouteLength());
                getParadeInfoMap().get(key).setHeadName(getParadeInfoMap().get(key).getParadeRoute().get(getParadeInfoMap().get(key).getParadeRoute().size() - 1));

            } else if ((getParadeInfoMap().get(key).getEndTime()).isBefore(getCurrentTime())) { //현재시간이 행진종료시간 후일때(행진이 종료)
                //해당 행진의 모든것을 null로 되돌려버리는 알고리즘.
                getParadeInfoMap().get(key).setHeadName(null);
                getParadeInfoMap().get(key).setHeadDistance(0);
            }
        }
    }   // 행진의 Head의 Distance와 Head 교차로 계산

    void calcTailDistanceAndName() {
        int accDistance;
        ArrayList tempParadeRoute;
        for (String key : getParadeInfoMap().keySet()) { //행진정보별
            if (getParadeInfoMap().get(key).getStartTime().isBefore(getCurrentTime()) //현재시간이 행진시작시간 이후이고 Tail(Head - 행진길이) >= 0 일때
                    && getParadeInfoMap().get(key).getHeadDistance() - getParadeInfoMap().get(key).getParadeLength() >= 0
                    && ((getParadeInfoMap().get(key).getEndTime()).isAfter(getCurrentTime())) || (getParadeInfoMap().get(key).getEndTime()).equals(getCurrentTime())) {
                getParadeInfoMap().get(key).setTailDistance(getParadeInfoMap().get(key).getHeadDistance() - getParadeInfoMap().get(key).getParadeLength()); //Tail까지 거리 계산
                if (getParadeInfoMap().get(key).getTailDistance() == 0) { //Tail까지 거리가 0일때
                    getParadeInfoMap().get(key).setTailName(getParadeInfoMap().get(key).getParadeRoute().get(0)); //행진경로 첫번째 노드를 TailName으로 설정
                } else if (getParadeInfoMap().get(key).getTailDistance() < getParadeInfoMap().get(key).getRouteLength()) { //Tail이 행진 경로 안에 있을때
                    accDistance = 0;
                    tempParadeRoute = getParadeInfoMap().get(key).getParadeRoute(); //행진정보별 행진경로ArrayList
                    for (int i = 0; i < tempParadeRoute.size() - 1; i++) {             //행진정보별 행진경로ArrayList 순환
                        accDistance += getWholeGraph().get(tempParadeRoute.get(i)).get(tempParadeRoute.get(i + 1));
                        if (i == 0 && accDistance >= getParadeInfoMap().get(key).getTailDistance()) { //Tail이 방금 출발해서 처음 노드 도달도 못한 경우 (Tail이 첫번째 노드와 같은 경우도 포함)
                            getParadeInfoMap().get(key).setTailName(tempParadeRoute.get(i).toString());
                            break;
                        } else if (accDistance < getParadeInfoMap().get(key).getTailDistance() && getParadeInfoMap().get(key).getTailDistance() < accDistance + getWholeGraph().get(tempParadeRoute.get(i + 1)).get(tempParadeRoute.get(i + 2))) {
                            //Tail거리가 accDistance, accDistance(Next)사이일때         
                            getParadeInfoMap().get(key).setTailName(tempParadeRoute.get(i + 1).toString());
                            break;
                        } else if (accDistance == getParadeInfoMap().get(key).getTailDistance()) { //Tail이 완전히 교차로 위에 있을때
                            getParadeInfoMap().get(key).setTailName(tempParadeRoute.get(i + 1).toString());
                            break;
                        }
                    }
                } else { //Tail이 경로밖에 있을때
                    getParadeInfoMap().get(key).setTailName(getParadeInfoMap().get(key).getParadeRoute().get(getParadeInfoMap().get(key).getParadeRoute().size() - 1));
                    //TailName은 행진경로 ArrayList 마지막 노드로 저장
                }
            } else if ((getParadeInfoMap().get(key).getEndTime()).equals(getCurrentTime())) { //행진종료시간이 현재시간과 같을때
                getParadeInfoMap().get(key).setTailName(getParadeInfoMap().get(key).getParadeRoute().get(getParadeInfoMap().get(key).getParadeRoute().size() - 1));

            } else if ((getParadeInfoMap().get(key).getEndTime()).isBefore(getCurrentTime())) { //현재시간이 행진종료시간 후일때(행진이 종료)
                //해당 행진의 모든것을 null로 되돌려버리는 알고리즘.
                getParadeInfoMap().get(key).setTailName(null);
                getParadeInfoMap().get(key).setTailDistance(0);
            }
        }
    }   // 행진의 Tail의 Distance와 Tail 교차로 계산                       

    void paintActivatedNodes() {
        ArrayList tempParadeRoute;
        for (String key : getParadeInfoMap().keySet()) { //행진정보별
            if (getParadeInfoMap().get(key).getInProgress() == true) { //행진이 현재 진행중일때
                tempParadeRoute = getParadeInfoMap().get(key).getParadeRoute(); //행진정보별 행진경로ArrayList
                if (getParadeInfoMap().get(key).getTailName() == null) { // Tail이 아직 존재 안하는 경우
                    for (int i = 0; i <= tempParadeRoute.indexOf(getParadeInfoMap().get(key).getHeadName()); i++) {
                        //행진이 진행중인 경로의 노드들을 색칠
                        inputStringReturnCircle(tempParadeRoute.get(i).toString()).setFill(getParadeInfoMap().get(key).getCircleColor());
                    }
                } else { // Tail이 존재 하는 경우
                    for (int i = tempParadeRoute.indexOf(getParadeInfoMap().get(key).getTailName()); i <= tempParadeRoute.indexOf(getParadeInfoMap().get(key).getHeadName()); i++) {
                        //행진이 진행중인 경로의 노드들을 색칠.
                        inputStringReturnCircle(tempParadeRoute.get(i).toString()).setFill(getParadeInfoMap().get(key).getCircleColor());
                    }
                    for (int i = 0; i <= tempParadeRoute.indexOf(getParadeInfoMap().get(key).getTailName()) - 1; i++) {
                        //행진이 지나간 Node들 색 없애기
                        inputStringReturnCircle(tempParadeRoute.get(i).toString()).setFill(Color.rgb(232, 227, 227));
                    }
                }
            }
        }
    }       // 현재 행진이 지나가고 있는 노드들 색 변화

    void drawAllEdges() {
        double x1, y1, x2, y2;
        for (String key : getWholeGraph().keySet()) {
            x1 = inputStringReturnCircle(key).getLayoutX();
            y1 = inputStringReturnCircle(key).getLayoutY();
            for (String key2 : getWholeGraph().get(key).keySet()) {
                x2 = inputStringReturnCircle(key2).getLayoutX();
                y2 = inputStringReturnCircle(key2).getLayoutY();
                map.getChildren().add(LineBuilder.create()
                        .startX(x1)
                        .startY(y1)
                        .endX(x2)
                        .endY(y2)
                        .fill(Color.BLACK)
                        .strokeWidth(0.5)
                        .build()
                );
            }
        }
        for (String key : getWholeGraph().keySet()) {
            inputStringReturnCircle(key).toFront();
            x1 = inputStringReturnCircle(key).getLayoutX();
            y1 = inputStringReturnCircle(key).getLayoutY();
            map.getChildren().add(LabelBuilder.create().text(key).layoutX(x1).layoutY(y1).build());
        }
    }

    Circle inputStringReturnCircle(String inputString) {
        switch (inputString) {
            case "개풍":
                return 개풍;
            case "경희궁":
                return 경희궁;
            case "공평":
                return 공평;
            case "광교":
                return 광교;
            case "광화문":
                return 광화문;
            case "금호":
                return 금호;
            case "꽃집":
                return 꽃집;
            case "낙원상가":
                return 낙원상가;
            case "남대문":
                return 남대문;
            case "남산육교":
                return 남산육교;
            case "내자":
                return 내자;
            case "눈스퀘어":
                return 눈스퀘어;
            case "대한문":
                return 대한문;
            case "덕수궁":
                return 덕수궁;
            case "덕수초":
                return 덕수초;
            case "독립문":
                return 독립문;
            case "동십자":
                return 동십자;
            case "동원":
                return 동원;
            case "명동성당":
                return 명동성당;
            case "명보":
                return 명보;
            case "모전교":
                return 모전교;
            case "문화거리":
                return 문화거리;
            case "미대사관":
                return 미대사관;
            case "박물관":
                return 박물관;
            case "사직공원":
                return 사직공원;
            case "서대문":
                return 서대문;
            case "서린":
                return 서린;
            case "서울역":
                return 서울역;
            case "서울청":
                return 서울청;
            case "세문관":
                return 세문관;
            case "세문관뒤":
                return 세문관뒤;
            case "세종":
                return 세종;
            case "소공":
                return 소공;
            case "신교":
                return 신교;
            case "안국":
                return 안국;
            case "안국역":
                return 안국역;
            case "열린마당":
                return 열린마당;
            case "염천":
                return 염천;
            case "옥인":
                return 옥인;
            case "을지1":
                return 을지1;
            case "을지2":
                return 을지2;
            case "을지3":
                return 을지3;
            case "의주":
                return 의주;
            case "인사동":
                return 인사동;
            case "재동초":
                return 재동초;
            case "적선":
                return 적선;
            case "정동":
                return 정동;
            case "정부청사":
                return 정부청사;
            case "종로1":
                return 종로1;
            case "종로2":
                return 종로2;
            case "종로3":
                return 종로3;
            case "창덕궁":
                return 창덕궁;
            case "청계2":
                return 청계2;
            case "청계3":
                return 청계3;
            case "청계광장":
                return 청계광장;
            case "청사별관":
                return 청사별관;
            case "태평":
                return 태평;
            case "통의":
                return 통의;
            case "퇴계1":
                return 퇴계1;
            case "퇴계2":
                return 퇴계2;
            case "퇴계3":
                return 퇴계3;
            case "포시즌":
                return 포시즌;
            case "한국은행":
                return 한국은행;
            case "환구단":
                return 환구단;
            default:
                break;
        }
        return null;
    }
    
    //콤보박스
    @FXML
    ComboBox cb;
    
    //레이블
    @FXML
    Label currentTimeLbl;
    @FXML
    Label currentDateLbl;

    //페인
    @FXML
    AnchorPane map;

    //프로그레스바
    @FXML
    ProgressBar pb;

    @FXML
    ProgressIndicator pi;

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
    private Circle 남산육교;
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
    public void onNextClicked(ActionEvent event) {
        if (getCurrentTime() == null) { //currentTime이 없을때. 초시 시간일때
            setCurrentTime(getInitialTime());
            setCurrentTime(getCurrentTime().plusMinutes(10));
        } else {
            setCurrentTime(getCurrentTime().plusMinutes(10));
        }
        currentTimeLbl.setText(getCurrentTime().toString());
        checkInProgressParades();
        calcHeadDistanceAndName();
        calcTailDistanceAndName();
        paintActivatedNodes();
        //pi.setProgress();
        //pb.setProgress();
        /*
        System.out.println(getParadeInfoMap().get("태극기국민평의회").getHeadName());
        System.out.println(getParadeInfoMap().get("태극기국민평의회").getHeadDistance());
        System.out.println(getParadeInfoMap().get("태극기국민평의회").getTailName());
        System.out.println(getParadeInfoMap().get("태극기국민평의회").getTailDistance());
        System.out.println("");*/
    } //Next 버튼을 누르면 현재시간 10분을 추가하고 그래프가 변화.

    @FXML
    public void onResetClicked(ActionEvent event) {
        currentTime = null;
        currentTimeLbl.setText(getInitialTime().toString());
        getParadeInfoMap().keySet().forEach((key) -> {
            getParadeInfoMap().get(key).resetValues();
        });
        resetAllNodeColor();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentDateLbl.setText(getSimulationDate().toString());
        //currentTimeLbl.setText(getInitialTime().toString());
        drawAllEdges();
        for (String key : getWholeGraph().keySet()) {
            cb.getItems().add(key);
        }
    }
}
