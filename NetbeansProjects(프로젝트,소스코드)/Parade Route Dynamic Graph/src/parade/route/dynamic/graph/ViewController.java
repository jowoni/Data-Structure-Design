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
import java.util.LinkedHashMap;
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

    private static final Map<String, Map<String, Integer>> wholeGraph = input.getWholeGraph(); // 전체 지도
    private static final Map<String, ParadeInfo> paradeInfoMap = input.getParadeInfoMap();     // 시위대명별 행진정보
    private static final LocalDate simulationDate = input.getDate();                           // 입력받은 행진 날짜 정보
    private static LocalTime currentTime = null;                                               // 현재 시간
    private static String paradeChoice = null;                                                 // 현재 선택된 행진 이름

    // getter setter methods
    Map<String, Map<String, Integer>> getWholeGraph() {
        return wholeGraph;
    }   // wholeGraph getter method

    Map<String, ParadeInfo> getParadeInfoMap() {
        return paradeInfoMap;
    }          // paradeInfoMap getter method

    LocalDate getSimulationDate() {
        return simulationDate;
    }                       // simulationDate getter method

    LocalTime getCurrentTime() {
        return currentTime;
    }                          // currentTime getter method

    void setCurrentTime(LocalTime currentTime) {
        ViewController.currentTime = currentTime;
    }          // currentTime setter method

    // 중요한 계산 함수들
    String dijkstra(String startNode, String endNode) {
        int n = wholeGraph.size();     //Node의 수
        int e = 0;                     //Edge의 수
        e = wholeGraph.keySet().stream().map((key) -> wholeGraph.get(key).size()).reduce(e, Integer::sum); //Edge의 수를 계산

        Map<String, Integer> keyToInteger = new HashMap<>(); // 노드들에 int index부여. 노드이름으로 index를 찾을수 있는 Map
        int keyIndex = 0;
        for (Object key : wholeGraph.keySet()) {
            keyToInteger.put(key.toString(), keyIndex);
            keyIndex++;
        }
        Map<Integer, String> integerToKey = new HashMap<>(); // index로 노드이름을 찾을수 있는 Map
        keyToInteger.keySet().forEach((key) -> {
            integerToKey.put((int) keyToInteger.get(key), key.toString());
        });

        int distance[] = new int[n];          //최단 거리를 저장할 변수
        boolean[] check = new boolean[n];     //해당 노드를 방문했는지 체크할 변수
        int maps[][] = new int[n][n];         //노드 간의 거리 배열
        wholeGraph.keySet().forEach((i) -> {  //노드 간의 거리 계산 및 저장
            wholeGraph.get(i).keySet().forEach((j) -> {
                maps[keyToInteger.get(i)][keyToInteger.get(j)] = wholeGraph.get(i).get(j);
            });
        });

        int v = keyToInteger.get(startNode);

        //distance값 초기화.
        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        //시작노드값 초기화.
        distance[v] = 0;
        check[v] = true;

        //연결노드 distance갱신
        for (int i = 0; i < n; i++) {
            if (!check[i] && maps[v][i] != 0) {
                distance[i] = maps[v][i];
            }
        }

        Map<Integer, Integer> temp = new LinkedHashMap<>();
        int tempp = 0;

        for (int a = 0; a < n - 1; a++) {
            //원래는 모든 노드가 true될때까지 인데
            //노드가 n개 있을 때 다익스트라를 위해서 반복수는 n-1번이면 된다.
            //원하지 않으면 각각의 노드가 모두 true인지 확인하는 식으로 구현해도 된다.
            int min = Integer.MAX_VALUE;
            int min_index = -1;

            //최소값 찾기
            for (int i = 0; i < n; i++) { //기준 노드에서 인접 노드중 가장 가까운 노드 선택
                if (!check[i] && distance[i] != Integer.MAX_VALUE) { //체크안되있고 거리가 무한이 아닌 노드
                    if (distance[i] < min) {
                        min = distance[i];
                        min_index = i;
                        tempp = i;
                    }
                }
            }
            check[min_index] = true;
            for (int i = 0; i < n; i++) {
                if (!check[i] && maps[min_index][i] != 0) {
                    if (distance[i] > distance[min_index] + maps[min_index][i]) {
                        distance[i] = distance[min_index] + maps[min_index][i];
                    }
                    if (i == keyToInteger.get(endNode)) {
                        temp.put(tempp, distance[i]);
                    }
                }
            }
        }
        //결과값 출력. startNode와 endNode까지의 최단거리
        int mini = 0;
        Object lastKey = null;
        for (Object key : temp.keySet()) {
            if (mini == 0) {
                mini = temp.get(key);
                lastKey = key;
            } else if (mini > temp.get(key)) {
                mini = temp.get(key);
                lastKey = key;
            }
        }
        return integerToKey.get(lastKey);
    }   // 두 노드 사이 최단 거리를 계산. 바리케이드 교차로 노드를 String으로 리턴. 해당 사이트의 코드 재사용 http://bumbums.tistory.com/4 [범범스의 코딩놀이터]
    
    void paintBarricadeNodes() {
        ArrayList tempParadeRoute;
        if (getParadeInfoMap().get(paradeChoice).getInProgress() == true && currentTime.isBefore(getParadeInfoMap().get(paradeChoice).getEndTime())) { //행진이 현재 진행중일때
            tempParadeRoute = getParadeInfoMap().get(paradeChoice).getParadeRoute(); //행진정보별 행진경로ArrayList
            if (getParadeInfoMap().get(paradeChoice).getTailName() == null) { // Tail이 아직 존재 안하는 경우
                for (int i = 0; i <= tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getHeadName()); i++) {
                    //행진이 진행중인 경로의 노드들을 색칠
                    if (wholeGraph.get(getParadeInfoMap().get(paradeChoice).getDestNode()).containsKey(tempParadeRoute.get(i).toString()) == false) {
                        if (tempParadeRoute.contains(dijkstra(getParadeInfoMap().get(paradeChoice).getDestNode(), tempParadeRoute.get(i).toString())) == false) {
                            if (dijkstra(getParadeInfoMap().get(paradeChoice).getDestNode(), tempParadeRoute.get(i).toString()) != null) {
                                //시작, 종착 교차로가 인접하지 않고 dijkstra로 계산된 바리케이드 교차로가 행진 진행 교차로에 포함되지 않고 dijkstra input node들이 인접 노드들이 아닌 경우에만 색칠
                                inputStringReturnCircle(dijkstra(getParadeInfoMap().get(paradeChoice).getDestNode(), tempParadeRoute.get(i).toString())).setFill(Color.RED);
                            }
                        }
                    }
                }
            } else { // Tail이 존재 하는 경우
                if (tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getTailName()) > tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getHeadName())) {
                    //중복 행진 경로 노드의 경우 예외처리. Tail이 Head보다 클 경우.
                    for (int i = tempParadeRoute.lastIndexOf(getParadeInfoMap().get(paradeChoice).getTailName()); i <= tempParadeRoute.lastIndexOf(getParadeInfoMap().get(paradeChoice).getHeadName()); i++) {
                        //행진이 진행중인 경로의 노드들을 색칠.
                        if (wholeGraph.get(getParadeInfoMap().get(paradeChoice).getDestNode()).containsKey(tempParadeRoute.get(i).toString()) == false) {
                            if (tempParadeRoute.subList(tempParadeRoute.lastIndexOf(getParadeInfoMap().get(paradeChoice).getTailName()), tempParadeRoute.lastIndexOf(getParadeInfoMap().get(paradeChoice).getHeadName()) + 1).contains(dijkstra(getParadeInfoMap().get(paradeChoice).getDestNode(), tempParadeRoute.get(i).toString())) == false) {
                                if (dijkstra(getParadeInfoMap().get(paradeChoice).getDestNode(), tempParadeRoute.get(i).toString()) != null) {
                                    //시작, 종착 교차로가 인접하지 않고 dijkstra로 계산된 바리케이드 교차로가 행진 진행 교차로에 포함되지 않고 dijkstra input node들이 인접 노드들이 아닌 경우에만 색칠
                                    inputStringReturnCircle(dijkstra(getParadeInfoMap().get(paradeChoice).getDestNode(), tempParadeRoute.get(i).toString())).setFill(Color.RED);
                                }
                            }
                        }
                    }
                } else { //중복 행진 아닌 아닌 경우.
                    for (int i = tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getTailName()); i <= tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getHeadName()); i++) {
                        //행진이 진행중인 경로의 노드들을 색칠.
                        if (wholeGraph.get(getParadeInfoMap().get(paradeChoice).getDestNode()).containsKey(tempParadeRoute.get(i).toString()) == false) {
                            if (tempParadeRoute.subList(tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getTailName()), tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getHeadName()) + 1).contains(dijkstra(getParadeInfoMap().get(paradeChoice).getDestNode(), tempParadeRoute.get(i).toString())) == false) {
                                if (dijkstra(getParadeInfoMap().get(paradeChoice).getDestNode(), tempParadeRoute.get(i).toString()) != null) {
                                    //시작, 종착 교차로가 인접하지 않고 dijkstra로 계산된 바리케이드 교차로가 행진 진행 교차로에 포함되지 않고 dijkstra input node들이 인접 노드들이 아닌 경우에만 색칠
                                    inputStringReturnCircle(dijkstra(getParadeInfoMap().get(paradeChoice).getDestNode(), tempParadeRoute.get(i).toString())).setFill(Color.RED);
                                }
                            }
                        }
                    }
                }
            }
        }
    }                         // dijkstra로 계산된 바리케이드 교차로 노드를 빨강으로 색칠

    void checkInProgressParades() {
        if ((getParadeInfoMap().get(paradeChoice).getStartTime().equals(getCurrentTime()) || getParadeInfoMap().get(paradeChoice).getStartTime().isBefore(getCurrentTime()))
                && (getParadeInfoMap().get(paradeChoice).getEndTime().equals(getCurrentTime()) || getParadeInfoMap().get(paradeChoice).getEndTime().isAfter(getCurrentTime()))) {
            //StarTime <= currentTime <= EndTime 일 경우
            getParadeInfoMap().get(paradeChoice).setInProgress(true);
        } else { //행진 안하는 행진 경로상의 노드들 회색으로 칠하기.
            getParadeInfoMap().get(paradeChoice).setInProgress(false);
            resetAllNodeColor();
        }

    }       // 현재 진행중인 행진 확인

    void resetAllNodeColor() {
        wholeGraph.keySet().forEach((key) -> {
            inputStringReturnCircle(key).setFill(Color.rgb(232, 227, 227));
        });
    }              // 모든 노드 기본색으로 색칠하기

    void calcHeadDistanceAndName() {
        int accDistance;
        ArrayList tempParadeRoute;
        if (getParadeInfoMap().get(paradeChoice).getStartTime().isBefore(getCurrentTime())
                && ((getParadeInfoMap().get(paradeChoice).getEndTime()).equals(getCurrentTime())
                || (getParadeInfoMap().get(paradeChoice).getEndTime()).isAfter(getCurrentTime()))) { //현재시간이 행진시작시간 이후일 때만
            getParadeInfoMap().get(paradeChoice).setHeadDistance( //Head 까지의 거리 계산
                    getParadeInfoMap().get(paradeChoice).getStartTime().until(getCurrentTime(), ChronoUnit.MINUTES)
                    * getParadeInfoMap().get(paradeChoice).getParadeSpeed());
            accDistance = 0;
            tempParadeRoute = getParadeInfoMap().get(paradeChoice).getParadeRoute(); //행진정보별 행진경로ArrayList
            if (getParadeInfoMap().get(paradeChoice).getHeadDistance() <= getParadeInfoMap().get(paradeChoice).getRouteLength()) { //행진 Head가 경로 안에 있을때
                for (int i = 0; i < tempParadeRoute.size() - 1; i++) {               //행진정보별 행진경로ArrayList 순환
                    accDistance += getWholeGraph().get(tempParadeRoute.get(i)).get(tempParadeRoute.get(i + 1));
                    if (i == 0 && accDistance >= getParadeInfoMap().get(paradeChoice).getHeadDistance()) { //Head가 방금 출발해서 처음 노드 도달도 못한 경우(Head와 첫번째 노드가 같은 경우도 포함)
                        getParadeInfoMap().get(paradeChoice).setHeadName(tempParadeRoute.get(i + 1).toString());
                        break;
                    } else if (accDistance < getParadeInfoMap().get(paradeChoice).getHeadDistance() && getParadeInfoMap().get(paradeChoice).getHeadDistance() < accDistance + getWholeGraph().get(tempParadeRoute.get(i + 1)).get(tempParadeRoute.get(i + 2))) {
                        //Head거리가 accDistance, accDistance(Next)사이일때         
                        getParadeInfoMap().get(paradeChoice).setHeadName(tempParadeRoute.get(i + 2).toString());
                        break;
                    } else if (accDistance == getParadeInfoMap().get(paradeChoice).getHeadDistance()) { //Head가 완전히 교차로 위에 있을때
                        getParadeInfoMap().get(paradeChoice).setHeadName(tempParadeRoute.get(i + 1).toString());
                        break;
                    }
                }
            } else { //행진 Head가 경로밖에 있을때
                getParadeInfoMap().get(paradeChoice).setHeadName(getParadeInfoMap().get(paradeChoice).getParadeRoute().get(getParadeInfoMap().get(paradeChoice).getParadeRoute().size() - 1));
                //행진 HeadName은 행진경로 ArrayList 마지막 노드로 저장
            }
        } else if (getParadeInfoMap().get(paradeChoice).getStartTime().equals(getCurrentTime())) { //행진시작시간이 현재시간과 같을때
            getParadeInfoMap().get(paradeChoice).setHeadDistance(0); //Head까지 거리 = 0 그리고 HeadName은 행진경로 첫번째 노드
            getParadeInfoMap().get(paradeChoice).setHeadName(getParadeInfoMap().get(paradeChoice).getParadeRoute().get(0));
            //         break;
        } else if ((getParadeInfoMap().get(paradeChoice).getEndTime()).equals(getCurrentTime())) { //행진종료시간이 현재시간과 같을때
            getParadeInfoMap().get(paradeChoice).setHeadDistance(getParadeInfoMap().get(paradeChoice).getRouteLength());
            getParadeInfoMap().get(paradeChoice).setHeadName(getParadeInfoMap().get(paradeChoice).getParadeRoute().get(getParadeInfoMap().get(paradeChoice).getParadeRoute().size() - 1));

        } else if ((getParadeInfoMap().get(paradeChoice).getEndTime()).isBefore(getCurrentTime())) { //현재시간이 행진종료시간 후일때(행진이 종료)
            //해당 행진의 모든것을 null로 되돌려버리는 알고리즘.
            getParadeInfoMap().get(paradeChoice).setHeadName(null);
            getParadeInfoMap().get(paradeChoice).setHeadDistance(0);
        }

    }      // 행진 Head의 Distance와 Head 교차로 계산

    void calcTailDistanceAndName() {
        int accDistance;
        ArrayList tempParadeRoute;
        if (getParadeInfoMap().get(paradeChoice).getStartTime().isBefore(getCurrentTime()) // 현재시간이 행진시작시간 이후이고 Tail(= Head - 행진길이) >= 0 일때
                && (getParadeInfoMap().get(paradeChoice).getHeadDistance() - getParadeInfoMap().get(paradeChoice).getParadeLength() >= 0)
                && (((getParadeInfoMap().get(paradeChoice).getEndTime()).isAfter(getCurrentTime())) || (getParadeInfoMap().get(paradeChoice).getEndTime()).equals(getCurrentTime()))) {
            getParadeInfoMap().get(paradeChoice).setTailDistance(getParadeInfoMap().get(paradeChoice).getHeadDistance() - getParadeInfoMap().get(paradeChoice).getParadeLength()); //Tail까지 거리 계산
            if (getParadeInfoMap().get(paradeChoice).getTailDistance() == 0) { // Tail까지 거리가 0일때
                getParadeInfoMap().get(paradeChoice).setTailName(getParadeInfoMap().get(paradeChoice).getParadeRoute().get(0));          // 행진경로 첫번째 노드를 TailName으로 설정
            } else if (getParadeInfoMap().get(paradeChoice).getTailDistance() < getParadeInfoMap().get(paradeChoice).getRouteLength()) { // Tail이 행진 경로 안에 있을때
                accDistance = 0;
                tempParadeRoute = getParadeInfoMap().get(paradeChoice).getParadeRoute(); // 행진정보별 행진경로ArrayList
                for (int i = 0; i < tempParadeRoute.size() - 1; i++) {                   // 행진정보별 행진경로ArrayList 순환
                    accDistance += getWholeGraph().get(tempParadeRoute.get(i)).get(tempParadeRoute.get(i + 1));
                    if (i == 0 && accDistance >= getParadeInfoMap().get(paradeChoice).getTailDistance()) { // Tail이 방금 출발해서 처음 노드 도달도 못한 경우 (Tail이 첫번째 노드와 같은 경우도 포함)
                        getParadeInfoMap().get(paradeChoice).setTailName(tempParadeRoute.get(i).toString());
                        break;
                    } else if (accDistance < getParadeInfoMap().get(paradeChoice).getTailDistance() && getParadeInfoMap().get(paradeChoice).getTailDistance() < accDistance + getWholeGraph().get(tempParadeRoute.get(i + 1)).get(tempParadeRoute.get(i + 2))) {
                        // Tail거리가 accDistance, accDistance(Next)사이일때         
                        getParadeInfoMap().get(paradeChoice).setTailName(tempParadeRoute.get(i + 1).toString());
                        break;
                    } else if (accDistance == getParadeInfoMap().get(paradeChoice).getTailDistance()) { // Tail이 완전히 교차로 위에 있을때
                        getParadeInfoMap().get(paradeChoice).setTailName(tempParadeRoute.get(i + 1).toString());
                        break;
                    }
                }
            } else { // Tail이 경로밖에 있을때
                // Tail 교차로 이름을 행진 경로상 마지막 노드로 설정
                getParadeInfoMap().get(paradeChoice).setTailName(getParadeInfoMap().get(paradeChoice).getParadeRoute().get(getParadeInfoMap().get(paradeChoice).getParadeRoute().size() - 1));
                getParadeInfoMap().get(paradeChoice).setTailDistance(getParadeInfoMap().get(paradeChoice).getRouteLength());
            }
        } else if ((getParadeInfoMap().get(paradeChoice).getEndTime()).equals(getCurrentTime())) { //행진종료시간이 현재시간과 같을때
            // Tail 교차로 이름을 행진 경로상 마지막 노드로 설정
            getParadeInfoMap().get(paradeChoice).setTailName(getParadeInfoMap().get(paradeChoice).getParadeRoute().get(getParadeInfoMap().get(paradeChoice).getParadeRoute().size() - 1));
            getParadeInfoMap().get(paradeChoice).setTailDistance(getParadeInfoMap().get(paradeChoice).getRouteLength());
        } else if ((getParadeInfoMap().get(paradeChoice).getEndTime()).isBefore(getCurrentTime())) { // 현재시간이 행진종료시간 후일때(행진이 종료)
            getParadeInfoMap().get(paradeChoice).setTailName(null); // 해당 행진의 모든것을 null로 되돌려버리는 알고리즘.
            getParadeInfoMap().get(paradeChoice).setTailDistance(0);
        }

    }      // 행진 Tail의 Distance와 Tail 교차로 계산                       

    void paintActivatedNodes() {
        ArrayList tempParadeRoute;
        if (getParadeInfoMap().get(paradeChoice).getInProgress() == true) { //행진이 현재 진행중일때
            tempParadeRoute = getParadeInfoMap().get(paradeChoice).getParadeRoute(); //행진정보별 행진경로ArrayList
            if (getParadeInfoMap().get(paradeChoice).getTailName() == null) { // Tail이 아직 존재 안하는 경우
                for (int i = 0; i <= tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getHeadName()); i++) {
                    //행진이 진행중인 경로의 노드들을 색칠
                    inputStringReturnCircle(tempParadeRoute.get(i).toString()).setFill(getParadeInfoMap().get(paradeChoice).getCircleColor());
                }
            } else { // Tail이 존재 하는 경우
                if (tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getTailName()) > tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getHeadName())) {
                    //중복 행진 경로 노드의 경우 예외처리. Tail이 Head보다 클 경우.
                    for (int i = 0; i <= tempParadeRoute.lastIndexOf(getParadeInfoMap().get(paradeChoice).getTailName()) - 1; i++) {
                        //행진이 지나간 Node들 색 없애기
                        inputStringReturnCircle(tempParadeRoute.get(i).toString()).setFill(Color.rgb(232, 227, 227));
                    }
                    for (int i = tempParadeRoute.lastIndexOf(getParadeInfoMap().get(paradeChoice).getTailName()); i <= tempParadeRoute.lastIndexOf(getParadeInfoMap().get(paradeChoice).getHeadName()); i++) {
                        //행진이 진행중인 경로의 노드들을 색칠.
                        inputStringReturnCircle(tempParadeRoute.get(i).toString()).setFill(getParadeInfoMap().get(paradeChoice).getCircleColor());
                    }
                } else { //중복 행진 아닌 아닌 경우.
                    for (int i = 0; i <= tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getTailName()) - 1; i++) {
                        //행진이 지나간 Node들 색 없애기
                        inputStringReturnCircle(tempParadeRoute.get(i).toString()).setFill(Color.rgb(232, 227, 227));
                    }
                    for (int i = tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getTailName()); i <= tempParadeRoute.indexOf(getParadeInfoMap().get(paradeChoice).getHeadName()); i++) {
                        //행진이 진행중인 경로의 노드들을 색칠.
                        inputStringReturnCircle(tempParadeRoute.get(i).toString()).setFill(getParadeInfoMap().get(paradeChoice).getCircleColor());
                    }
                }
            }
        }
    }          // 현재 행진이 지나가고 있는 노드들 색 변화

    void drawAllEdges() {
        double x1, y1, x2, y2;
        for (String key : getWholeGraph().keySet()) { // 모든 노드에 대해서
            x1 = inputStringReturnCircle(key).getLayoutX();
            y1 = inputStringReturnCircle(key).getLayoutY();
            for (String key2 : getWholeGraph().get(key).keySet()) { // 현재 iterate중인 노드의 인접 노드에 대해서 두 노드의 중심점 사이에 Line 출력
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
        for (String key : getWholeGraph().keySet()) { // 모든 노드에 대해서
            inputStringReturnCircle(key).toFront();
            x1 = inputStringReturnCircle(key).getLayoutX();
            y1 = inputStringReturnCircle(key).getLayoutY();
            map.getChildren().add(LabelBuilder.create().text(key).layoutX(x1).layoutY(y1).build()); // 현재 iterated중인 노드의 중심점에 교차로 이름 Label 출력
        }
    }                  // 프로그램 시작시 모든 Edge와 교차로 Label을 화면에 출력

    void showProgressInTextArea() {
        if (getParadeInfoMap().get(paradeChoice).getInProgress() == true) { // 행진이 현재 진행중일때
            if (paradeInfoMap.get(paradeChoice).getStartTime().plusMinutes(10).equals(currentTime)) { // 행진 시작하면 해당 문구 출력
                ta.appendText("PARADE START!" + '\n' + '\n');
            }
            ta.appendText("Time : " + currentTime + '\n'); // 현재 시간 출력
            if (paradeInfoMap.get(paradeChoice).getHeadDistance() >= paradeInfoMap.get(paradeChoice).getRouteLength()) { // Head가 종착했을때 해당 문구 출력
                ta.appendText("Head : " + paradeInfoMap.get(paradeChoice).getHeadName() + "(Arrived)" + '\n');
            } else { // Head의 시작 교차로부터 거리 출력
                ta.appendText("Head : " + paradeInfoMap.get(paradeChoice).getHeadName() + "(" + paradeInfoMap.get(paradeChoice).getHeadDistance() + ")" + '\n');
            }
            if (paradeInfoMap.get(paradeChoice).getTailName() == null) { // Tail이 아직 존재하지 않을때 해당 문구 출력
                ta.appendText("Tail  : " + paradeInfoMap.get(paradeChoice).getParadeRoute().get(0) + "(In starting point)" + '\n' + '\n');
            } else if (paradeInfoMap.get(paradeChoice).getTailDistance() >= paradeInfoMap.get(paradeChoice).getRouteLength()) { // Tail이 종착했을때 해당 문구 출력
                ta.appendText("Tail  : " + paradeInfoMap.get(paradeChoice).getTailName() + "(Arrived)" + '\n' + '\n');
            } else { // Tail의 시작 교차로부터 거리 출력
                ta.appendText("Tail  : " + paradeInfoMap.get(paradeChoice).getTailName() + "(" + paradeInfoMap.get(paradeChoice).getTailDistance() + ")" + '\n' + '\n');
            }
        }
    }       // TextArea에 행진의 진행 상황을 출력

    void updateProgressBarAndIndicator() {
        if (getParadeInfoMap().get(paradeChoice).getInProgress() == true) { // 행진이 현재 진행중일때
            pb.setProgress((float) getParadeInfoMap().get(paradeChoice).getStartTime().until(currentTime, ChronoUnit.MINUTES)
                    / getParadeInfoMap().get(paradeChoice).getStartTime().until(getParadeInfoMap().get(paradeChoice).getEndTime(), ChronoUnit.MINUTES));
            pi.setProgress((float) getParadeInfoMap().get(paradeChoice).getStartTime().until(currentTime, ChronoUnit.MINUTES)
                    / getParadeInfoMap().get(paradeChoice).getStartTime().until(getParadeInfoMap().get(paradeChoice).getEndTime(), ChronoUnit.MINUTES));
        }
    } // 행진 진척도인 Progress Bar & Indicator를 update

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
    } // String을 인수에다 넣으면 같은 이름의 Circle을 리턴

    @FXML
    TextArea ta;            // 행진 진척 상황을 나타내는 TextArea

    @FXML
    ComboBox cb;            // 행진 선택하는 콤보박스

    @FXML                   // 현재 날짜, 시간을 나타내는 Label
    Label currentTimeLbl;
    @FXML
    Label currentDateLbl;

    @FXML                   // 지도가 포함되어 있는 AnchorPane
    AnchorPane map;

    @FXML                   // 행진 진척을 나타내는 ProgressBar, ProgressIndicator
    ProgressBar pb;
    @FXML
    ProgressIndicator pi;

    @FXML
    private Circle 개풍;     //그래프의 노드를 Circle로 표현. 총64개.
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
    public void onComboBoxChecked(ActionEvent event) {
        paradeChoice = cb.getValue().toString();
        currentTime = null;
        currentTimeLbl.setText(paradeInfoMap.get(paradeChoice).getStartTime().toString());
        getParadeInfoMap().get(paradeChoice).resetValues();
        resetAllNodeColor();
    }     // 콤복 박스를 에서 행진을 선택하면 앞으로 해당 행진 정보를 Load하게끔 설정

    @FXML
    public void onNextClicked(ActionEvent event) {
        if (paradeChoice != null) {         // 현재 진행중인 행진이 있을때  
            if (getCurrentTime() == null) { // currentTime이 없을때. 초시 시간일때
                setCurrentTime(paradeInfoMap.get(paradeChoice).getStartTime());
                setCurrentTime(getCurrentTime().plusMinutes(10));
            } else {
                setCurrentTime(getCurrentTime().plusMinutes(10));
            }
            currentTimeLbl.setText(getCurrentTime().toString());
            resetAllNodeColor();
            checkInProgressParades();
            calcHeadDistanceAndName();
            calcTailDistanceAndName();
            paintActivatedNodes();
            paintBarricadeNodes();
            showProgressInTextArea();
            updateProgressBarAndIndicator();
        }
    }        // Next 버튼을 누르면 현재시간 10분을 추가하고 그래프가 알맞게 변화.

    @FXML
    public void onResetClicked(ActionEvent event) {
        currentTime = null;
        if (paradeChoice != null) {
            currentTimeLbl.setText(paradeInfoMap.get(paradeChoice).getStartTime().toString());
            getParadeInfoMap().get(paradeChoice).resetValues();
        }
        resetAllNodeColor();
        ta.setText("");
        pi.setProgress(0);
        pb.setProgress(0);
    }        // Reset 버튼을 누르면 행진 진척 정보와 현재 시간을 초기화

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentDateLbl.setText(getSimulationDate().toString());   // 현재 날짜 Label로 표시       
        drawAllEdges();                                           // 지도에 Edge 그리기
        getParadeInfoMap().keySet().forEach((key) -> {            // 콤보박스 행진 목록 추가
            cb.getItems().add(key);
        });
    }    // GUI 전반적인 초기화
}
