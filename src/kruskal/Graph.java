package kruskal;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GraphVertextKeyPair {
    Long key;
    Vertex vertex;
    
    public GraphVertextKeyPair () {}
    public GraphVertextKeyPair (Long key , Vertex vertex) {
        this.key = key;
        this.vertex = vertex;
    }
}
class GraphVertexMapper {
    ArrayList<GraphVertextKeyPair> list = new ArrayList<GraphVertextKeyPair>();
    
    boolean keySearch (Long key) {
        if (get(key) != null) 
         return true;
       else
            return false;
    }
    
    Vertex get (Long key) {   
        for (int i = 0 ; i < list.size() ; i++) {
            if (list.get(i).key == key)
                return list.get(i).vertex;
        }
        return null;
    }
    
    void add (Long key, Vertex vertex) {
        GraphVertextKeyPair graphVertextKeyPair = new GraphVertextKeyPair(key , vertex);
        list.add(graphVertextKeyPair);
    }
    
    ArrayList<Vertex> values (){
        ArrayList<Vertex> listVertex = new ArrayList<>();
        for (GraphVertextKeyPair ele : list) {
               listVertex.add(ele.vertex);
            }
        return listVertex;
        
    }
}
public class Graph{

    private List<Edge> allEdges;
    private GraphVertexMapper allVertex;
    boolean isDirected = false;
    
    public Graph(boolean isDirected){
        allEdges = new ArrayList<Edge>();
        allVertex = new GraphVertexMapper();
        this.isDirected = isDirected;
    }
    
    public void addEdge(long id1, long id2){
        addEdge(id1,id2,0);
    }
    
    //This works only for directed graph because for undirected graph we can end up
    //adding edges two times to allEdges
    public void addVertex(Vertex vertex){
        if (allVertex.keySearch(vertex.getId())) {
            return;
        }
        allVertex.add(vertex.getId(), vertex);
               
        for(Edge edge : vertex.getEdges()){
            allEdges.add(edge);
        }
    }
    
    public Vertex addSingleVertex(long id){
        if (allVertex.keySearch(id)){
            return allVertex.get(id);
        }
        Vertex v = new Vertex(id);
        allVertex.add(id, v);
        return v;
    }
    
    public Vertex getVertex(long id){
        return allVertex.get(id);
    }
    
    public void addEdge(long id1,long id2, int weight){
        Vertex vertex1 = null;
        if(allVertex.keySearch(id1)){
            vertex1 = allVertex.get(id1);
        }else{
            vertex1 = new Vertex(id1);
            allVertex.add(id1, vertex1);
        }
        Vertex vertex2 = null;
        if(allVertex.keySearch(id2)){
            vertex2 = allVertex.get(id2);
        }else{
            vertex2 = new Vertex(id2);
            allVertex.add(id2, vertex2);
        }

        Edge edge = new Edge(vertex1,vertex2,isDirected,weight);
        allEdges.add(edge);
        vertex1.addAdjacentVertex(edge, vertex2);
        if(!isDirected){
            vertex2.addAdjacentVertex(edge, vertex1);
        }

    }
    
    public List<Edge> getAllEdges(){
        return allEdges;
    }
    
    public Collection<Vertex> getAllVertex(){
        return allVertex.values();
    }
    public void setDataForVertex(long id, int data){
        if(allVertex.keySearch(id)){
            Vertex vertex = allVertex.get(id);
            vertex.setData(data);
        }
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        for(Edge edge : getAllEdges()){
            buffer.append(edge.getVertex1() + " " + edge.getVertex2() + " " + edge.getWeight());
            buffer.append("\n");
        }
        return buffer.toString();
    }
}


class Vertex {
    long id;
    private int data;
    private List<Edge> edges = new ArrayList<>();
    private List<Vertex> adjacentVertex = new ArrayList<>();
    
    Vertex(long id){
        this.id = id;
    }
    
    public long getId(){
        return id;
    }
    
    public void setData(int data){
        this.data = data;
    }
    
    public int getData(){
        return data;
    }
    
    public void addAdjacentVertex(Edge e, Vertex v){
        edges.add(e);
        adjacentVertex.add(v);
    }
    
    public String toString(){
        return String.valueOf(id);
    }
    
    public List<Vertex> getAdjacentVertexes(){
        return adjacentVertex;
    }
    
    public List<Edge> getEdges(){
        return edges;
    }
    
    public int getDegree(){
        return edges.size();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id != other.id)
            return false;
        return true;
    }
}

class Edge{
    private boolean isDirected = false;
    private Vertex vertex1;
    private Vertex vertex2;
    private int weight;
    
    Edge(Vertex vertex1, Vertex vertex2){
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    Edge(Vertex vertex1, Vertex vertex2,boolean isDirected,int weight){
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
        this.isDirected = isDirected;
    }
    
    Edge(Vertex vertex1, Vertex vertex2,boolean isDirected){
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.isDirected = isDirected;
    }
    
    Vertex getVertex1(){
        return vertex1;
    }
    
    Vertex getVertex2(){
        return vertex2;
    }
    
    int getWeight(){
        return weight;
    }
    
    public boolean isDirected(){
        return isDirected;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((vertex1 == null) ? 0 : vertex1.hashCode());
        result = prime * result + ((vertex2 == null) ? 0 : vertex2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Edge other = (Edge) obj;
        if (vertex1 == null) {
            if (other.vertex1 != null)
                return false;
        } else if (!vertex1.equals(other.vertex1))
            return false;
        if (vertex2 == null) {
            if (other.vertex2 != null)
                return false;
        } else if (!vertex2.equals(other.vertex2))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Edge [isDirected=" + isDirected + ", vertex1=" + vertex1
                + ", vertex2=" + vertex2 + ", weight=" + weight + "]";
    }
}
