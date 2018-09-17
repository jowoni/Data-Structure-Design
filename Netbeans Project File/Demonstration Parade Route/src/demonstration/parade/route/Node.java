/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;

/**
 *
 * @author Michael
 //* @param <E> the type of value stored in this Edge
 */
public class Node 
{
    private String intersectionName;
    private int numEdges;
    private boolean active = false;
    
    String getName()
    {
        return intersectionName;
    }
    
    void setName(String n)
    {
        intersectionName = n;
    }
    
    int getNumEdges()
    {
        return numEdges;
    }
    
    void setNumEdges(int n)
    {
        numEdges = n;
    }
    
    boolean getActive()
    {
        return active;
    }
    
    void setActive(boolean b)
    {
        active = b;
    }
}
