package kruskal;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KruskalMST {
    
    //sorting: selection sort
    public void sortEdges (List <Edge> allEdges) {
        
        for (int i = 0 ; i < allEdges.size()-1; i++) {
            for (int j = 0 ; j < i ; j++) {
                if (allEdges.get(i).getWeight() <= allEdges.get(j).getWeight()) {
                    Edge tempEdge = allEdges.get(i);
                    allEdges.set(i, allEdges.get(j));
                    allEdges.set(j, tempEdge);
                }
            }
        }
    }

    public List<Edge> getMST(Graph graph) {
        List<Edge> allEdges = graph.getAllEdges();


        //sort all edges in non decreasing order

        sortEdges(allEdges);
        DisjointSet disjointSet = new DisjointSet();

        //create as many disjoint sets as the total vertices
        for (Vertex vertex : graph.getAllVertex()) {
            disjointSet.makeSet(vertex.getId());
        }

        List<Edge> resultEdge = new ArrayList<Edge>();

        for (Edge edge : allEdges) {
            //get the sets of two vertices of the edge
            long root1 = disjointSet.findSet(edge.getVertex1().getId());
            long root2 = disjointSet.findSet(edge.getVertex2().getId());

            //check if the vertices are in same set or different set
            //if verties are in same set then ignore the edge
            if (root1 == root2) {
                continue;
            } else {
                //if vertices are in different set then add the edge to result and union these two sets into one
                resultEdge.add(edge);
                disjointSet.union(edge.getVertex1().getId(), edge.getVertex2().getId());
            }

        }
        return resultEdge;
    }

    public static void main(String args[]) {
        Graph graph = new Graph(false);
        graph.addEdge(1, 2, 4);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 5, 1);
        graph.addEdge(2, 6, 3);
        graph.addEdge(2, 4, 2);
        graph.addEdge(6, 5, 2);
        graph.addEdge(6, 4, 3);
        graph.addEdge(4, 7, 2);
        graph.addEdge(3, 4, 5);
        graph.addEdge(3, 7, 8);
        KruskalMST mst = new KruskalMST();
        List<Edge> result = mst.getMST(graph);
        int mstWeight = 0;

        for (Edge edge : result) {
            mstWeight += edge.getWeight();
            //edges that makes connected tree -- uncomment if you want to show edges
           // System.out.println(edge.getVertex1() + " " + edge.getVertex2());
        }
        
        System.out.println("Minimum spanning tree weight:"  +mstWeight);
    }
}