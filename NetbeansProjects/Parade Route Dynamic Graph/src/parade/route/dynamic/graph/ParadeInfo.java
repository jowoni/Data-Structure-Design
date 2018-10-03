/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

import java.time.*;
import java.util.LinkedList;

/**
 *
 * @author Michael
 */
public class ParadeInfo {

    int length;
    LocalTime startTime, endTIme;
    Duration totalTime;
    LinkedList<String> paradeRoute;

    ParadeInfo(int length, LocalTime startTime, LocalTime endTIme) {
        
        this.length = length;
        this.startTime = startTime;
        this.endTIme = endTIme;
        this.totalTime = Duration.between(startTime, endTIme);
        paradeRoute = new LinkedList<>();
    }

    LinkedList<String> getParadeRoute() {
        return paradeRoute;
    }

}
