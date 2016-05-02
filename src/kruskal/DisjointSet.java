package kruskal;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Node {
    long data;
    Node parent;
    int rank;
}

class KeyPair {
    public Long data;
    public Node node;
    
    public KeyPair () {
        
    }
    
    public KeyPair (Long data, Node node) {
        this.data = data;
        this.node = node;
    }
}

class Mapper {
    ArrayList <KeyPair> list = new ArrayList <KeyPair>();
    
    public void add (Long data, Node node) {
        list.add(new KeyPair (data, node));
    }
    
    public Node get(Long data) {
        for (int i = 0 ; i < list.size () ; i++) {
           KeyPair tempKeyPair =  list.get(i);
           if (tempKeyPair.data == data) {
               return tempKeyPair.node;
           }
               
        }
        return null;
    }
}
public class DisjointSet {

    private Map<Long, Node> map = new HashMap<>();
    private Mapper mapper = new Mapper ();

    /**
     * Create a set with only one element.
     */
    public void makeSet(long data) {
        Node node = new Node();
        node.data = data;
        node.parent = node;
        node.rank = 0;
        map.put(data, node);
        mapper.add (data, node);
    }

    /**
     * Combines two sets together to one.
     * Does union by rank
     *
     * @return true if data1 and data2 are in different set before union else false.
     */
    public boolean union(long data1, long data2) {
        
        Node node1 = mapper.get(data1);
        Node node2 = mapper.get(data2);

        Node parent1 = findSet(node1);
        Node parent2 = findSet(node2);

        //if they are part of same set do nothing
        if (parent1.data == parent2.data) {
            return false;
        }

        //else whoever's rank is higher becomes parent of other
        if (parent1.rank >= parent2.rank) {
            //increment rank only if both sets have same rank
            parent1.rank = (parent1.rank == parent2.rank) ? parent1.rank + 1 : parent1.rank;
            parent2.parent = parent1;
        } else {
            parent1.parent = parent2;
        }
        return true;
    }

    /**
     * Finds the representative of this set
     */
    public long findSet(long data) {
        return findSet(mapper.get(data)).data;
    }

    /**
     * Find the representative recursively and does path
     * compression as well.
     */
    private Node findSet(Node node) {
        Node parent = node.parent;
        if (parent == node) {
            return parent;
        }
        node.parent = findSet(node.parent);
        return node.parent;
    }


}