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
    private String srcNode;
    private String destNode;
    private float weight;
    
    Edge(String srcNode_, String destNode_, float weight_){
        srcNode = srcNode_;
        destNode = destNode_;
        weight = weight_;
    }
}
