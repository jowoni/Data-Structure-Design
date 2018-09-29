/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
/**
 *
 * @author Michael
 */
public class Dynamic_Graph 
{
    private Map<Node, ArrayList<Edge>> all_intersection = new HashMap<Node, ArrayList<Edge>>();
    
    public static void main(String[] args) throws Exception
    {
        View v = new View();
        Controller c = new Controller(v);
    }

    public void addEdge()
    {
        
    }
    
    public Edge getEdge(Node src, Node dest)
    {
        for(Edge i : all_intersection.get(src))
        {
            if(i.getDestNode().getName().equals(dest.getName()))
            {
                return i;
            }
        }
        return null;
    }

    public void removeEdge()
    {
        
    }
    
    public int numEdges(Node v)
    {
        return all_intersection.get(v).size();
    }
    
    public void addNode(int name, int numEdges)
    {
        
    }
    
    public void getNode()
    {
        
    }

    public void removeNode()
    {
        
    }
}
