/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
// 출처: http://vaert.tistory.com/107 [Vaert Street]
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Michael
 */
public class Adjacency_List {
/*
    private Map<Integer, List<Integer>> adjacencyList;

    public Adjacency_List(int v){    //Constructor
        adjacencyList = new HashMap<Integer,List<Integer>>();
        for(int i=0;i<v;++i){
            adjacencyList.put(i, new LinkedList<Integer>());
        }
    }

    public void setEdge(int a,int b){    //method to add an edge
        List<Integer> edges=adjacencyList.get(a);
        edges.add(b);
    }

    public List<Integer> getEdge(int a){
        return adjacencyList.get(a);
    }

    public boolean contain(int a,int b){
        return adjacencyList.get(a).contains(b);
    }

    public int numofEdges(int a){
        return adjacencyList.get(a).size();
    }

    public void removeEdge(int a,int b){
        adjacencyList.get(a).remove(b);
    }

    public void removeVertex(int a){
        adjacencyList.get(a).clear();
    }

    public void addVertex(int a){
        adjacencyList.put(a, new LinkedList<Integer>());
    }
*/
}
// 출처: https://stackoverflow.com/questions/41351353/weighted-directed-graph-implementation-in-java-bellman-ford
