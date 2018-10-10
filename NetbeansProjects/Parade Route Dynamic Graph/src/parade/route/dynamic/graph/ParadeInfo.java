/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

import java.time.*;
import java.util.ArrayList;
/**
 *
 * @author Michael
 */
public class ParadeInfo {

    private boolean inProgress;                  //행진의 진행여부를 나타냄
    private final int paradeLength;              //행진의 길이(인원수)
    private int routeLength;                     //행진 경로의 총길이
    private float headDistance, tailDistance;    //Head, Tail까지의 거리
    private String headName, tailName;           //Head, Tail 해당 교차로
    private final LocalTime startTime, endTime;  //행진 시작, 종료 시간
    private final Duration totalTime;            //총 행진 시간
    private final ArrayList<String> paradeRoute; //행진 경로의 ArrayList<String>
    private float paradeSpeed;                   //행진 진행 속도

    ParadeInfo(int paradeLength, LocalTime startTime, LocalTime endTime) {
        this.paradeLength = paradeLength;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = Duration.between(startTime, endTime);
        tailName = null;
        headName = null;
        inProgress = false;
        paradeRoute = new ArrayList<>();
                //(Total route length+ Parade length)meter / Parade time(minute)
    }
    
    // 게터 세터
    boolean getInProgress(){
        return inProgress;
    }
    float getParadeSpeed(){
        return paradeSpeed;
    }
    int getParadeLength(){
        return paradeLength;
    }
    int getRouteLength(){
        return routeLength;
    }
    float getHeadDistance(){
        return headDistance;
    }
    float getTailDistance(){
        return tailDistance;
    }
    String getHeadName(){
        return headName;
    }
    String getTailName(){
        return tailName;
    }
    LocalTime getStartTime(){
        return startTime;
    }
    LocalTime getEndTime(){
        return endTime;
    }
    Duration getTotalTime(){
        return totalTime;
    }
    ArrayList<String> getParadeRoute(){
        return paradeRoute;
    }
    void setInProgress(boolean inProgress){
        this.inProgress = inProgress;
    }
    void setRouteLength(float routeLength){
        this.routeLength = (int)routeLength;
    }
    void setHeadDistance(float headDistance){
        this.headDistance = headDistance;
    }
    void setTailDistance(float tailDistance){
        this.tailDistance = tailDistance;
    }
    void setHeadName(String headName){
        this.headName = headName;
    }
    void setTailName(String tailName){
        this.tailName = tailName;
    }
    void setParadeSpeed(float paradeSpeed){
        this.paradeSpeed = paradeSpeed;
    }
}
