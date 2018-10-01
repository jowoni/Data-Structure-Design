/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;

/**
 *
 * @author Michael //* @param <E> the type of value stored in this Edge
 */
public class Node {

    private String intscName;
    private int numEdges;
    private boolean active = false;

    Node(String intsc_name, int num_edges) {
        intscName = intsc_name;
        numEdges = num_edges;
    }

    String getName() {
        return intscName;
    }

    void setName(String n) {
        intscName = n;
    }

    int getNumEdges() {
        return numEdges;
    }

    void setNumEdges(int n) {
        numEdges = n;
    }

    boolean getActive() {
        return active;
    }

    void setActive(boolean b) {
        active = b;
    }
}
