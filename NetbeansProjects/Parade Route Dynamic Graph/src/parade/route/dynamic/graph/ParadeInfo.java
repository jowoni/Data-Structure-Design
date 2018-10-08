/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

import java.time.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Michael
 */
public class ParadeInfo {

    private boolean inProgress;
    private final int paradeLength;
    private int routeLength;
    private float headDistance, tailDistance;
    private String headName, tailName;
    private final LocalTime startTime, endTime;
    private final Duration totalTime;
    private final ArrayList<String> paradeRoute;
    private float paradeSpeed;

    ParadeInfo(int paradeLength, LocalTime startTime, LocalTime endTime) {
        this.paradeLength = paradeLength;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = Duration.between(startTime, endTime);
        inProgress = false;
        paradeRoute = new ArrayList<>();
                //(Total route length+ Parade length)meter / Parade time(minute)
    }
    
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
