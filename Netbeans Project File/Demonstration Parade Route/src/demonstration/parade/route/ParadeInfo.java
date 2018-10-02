/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;

import java.util.LinkedList;

/**
 *
 * @author Michael
 */
public class ParadeInfo {

    int numParticipants;
    int paradeTime;
    LinkedList<String> paradeRoute;

    ParadeInfo(int numParticipants, int paradeTime) {
        this.numParticipants = numParticipants;
        this.paradeTime = paradeTime;
        paradeRoute = new LinkedList<String>();
    }

    LinkedList<String> getParadeRoute() {
        return paradeRoute;
    }

}
