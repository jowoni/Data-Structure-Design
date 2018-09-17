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
    private Vertex srcNode;
    private Vertex destNode;
    private float weight;
 
    void setSrcNode(Vertex v)
    {
        srcNode = v;
    }
    Vertex getSrcNode()
    {
        return srcNode;
    }
    void setDestNode(Vertex v)
    {
        destNode = v;
    }
    Vertex getDestNode()
    {
        return destNode;
    }
    void setWeight(float w)
    {
        weight = w;
    }
    float getWeight()
    {
        return weight;
    }
}
