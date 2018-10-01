/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;

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
            
    /*
    Node getSrcNode()
    {
        return srcNode;
    }
    
    void setSrcNode(Node v)
    {
        srcNode = v;
    }
    
    Node getDestNode()
    {
        return destNode;
    }
    
    void setDestNode(Node v)
    {
        destNode = v;
    }
    
    float getWeight()
    {
        return weight;
    }
   
    void setWeight(float w)
    {
        weight = w;
    }
    */
}
