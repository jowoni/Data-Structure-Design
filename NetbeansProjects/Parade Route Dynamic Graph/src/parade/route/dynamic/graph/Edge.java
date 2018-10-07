/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parade.route.dynamic.graph;

/**
 *
 * @author Michael
 */
public class Edge 
{
    private final String destNode;
    private final float weight;
    
    Edge(String destNode, float weight){
        this.destNode = destNode;
        this.weight = weight;
    }
}
