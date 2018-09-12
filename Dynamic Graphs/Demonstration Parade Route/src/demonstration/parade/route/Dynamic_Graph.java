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
import java.util.LinkedList;

public class Dynamic_Graph {
    static class Edge {
        int node1;
        int node2;
        int weight;
        int paradeLength;
        boolean passingBy;
        

        public Edge(int node1, int node2, int weight,int paradeLength, boolean passingBy) {
            this.node1 = node1;
            this.node2 = node2;
            this.weight = weight;
            this.paradeLength = paradeLength;
            this.passingBy = passingBy;
        }
    }

    static class Graph {
        int nodes;
        LinkedList<Edge> [] adjacencylist;

        Graph(int nodes) {
            this.nodes = nodes;
            adjacencylist = new LinkedList[nodes];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i <nodes ; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }

        public void addEgde(int node_1, int node2, int weight) {
            Edge edge = new Edge(node_1, node2, weight);
            adjacencylist[node_1].addFirst(edge); //for directed graph
        }

        public void printGraph(){
            for (int i = 0; i <nodes ; i++) {
                LinkedList<Edge> list = adjacencylist[i];
                for (int j = 0; j <list.size() ; j++) {
                    System.out.println("vertex-" + i + " is connected to " +
                            list.get(j).node2 + " with weight " +  list.get(j).weight);
                }
            }
        }
    }
      public static void main(String[] args) {
            int vertices = 6;
            Graph graph = new Graph(vertices);
            graph.addEgde(0, 1, 4);
            graph.addEgde(0, 2, 3);
            graph.addEgde(1, 3, 2);
            graph.addEgde(1, 2, 5);
            graph.addEgde(2, 3, 7);
            graph.addEgde(3, 4, 2);
            graph.addEgde(4, 0, 4);
            graph.addEgde(4, 1, 4);
            graph.addEgde(4, 5, 6);
            graph.printGraph();
        }
}
