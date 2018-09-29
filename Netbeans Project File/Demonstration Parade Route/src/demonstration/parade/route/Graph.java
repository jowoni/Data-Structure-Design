/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.*;
import java.lang.reflect.Method;

/**
 *
 * @author Michael
 */
public class Graph {
    private Map<Vertex, List<Edge>> all_intersection = new HashMap<Vertex, List<Edge>>();
    private Map<String, List<Edge>> parade_intersection = new HashMap<String, List<Edge>>();
    private int numVertex = 100;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Graph g = new Graph();
        Edge e = new Edge();
        /*
        for(int i = 0; i < 100; i++)
        {
            g.adjacencyList.put("입력노드", new LinkedList<Edge>() );
        }
        
        Vertex ndm = new Vertex("남대문");
        g.all_intersection.put(ndm, new ArrayList<Edge>() );
        g.all_intersection.get("남대문");
        */
        
        String className = "서울역"; 
        //Vertex 서울역 = new Vertex();
        try 
        { 
            Class Vertex = Class.forName(className); 
            Object newObj = Vertex.newInstance();
            //Method m = Vertex.getDeclaredMethod("show", boolean.class); 
            //m.invoke(newObj, true);
            //newObj.getClass().getDeclaredField("name") = "seg";
        } 
        catch (Exception i) 
        {
        }
       
        
        
       //출처: http://sks3297.tistory.com/entry/JAVA-문자열로-클래스-만들기 [SIM's Review]
       /* 
        g.all_intersection.put("시청", new LinkedList<Edge>() );
        g.all_intersection.put("광화문", new LinkedList<Edge>() );
        g.all_intersection.put("세종", new LinkedList<Edge>() );
        g.all_intersection.put("서린", new LinkedList<Edge>() );
        //g.all_intersection.get("남대문").add(e);
        엑셀 파일에 있는대로 교차로별 인접 교차로 목록을 Edge에 불러와 변수 설정 후
        */
    }
}
