package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    private void checkRep() {
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource()) && vertices.contains(edge.getTarget());
            assert edge.getWeight() >= 0;
        }
    }
    
    @Override
    public boolean add(String vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        checkRep();
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        if (!vertices.contains(source)) add(source);
        if (!vertices.contains(target)) add(target);
        
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                int oldWeight = edge.getWeight();
                if (weight == 0) {
                    edges.remove(edge);
                } else {
                    edges.set(edges.indexOf(edge), new Edge(source, target, weight));
                }
                checkRep();
                return oldWeight;
            }
        }
        
        if (weight != 0) {
            edges.add(new Edge(source, target, weight));
        }
        
        checkRep();
        return 0;
    }
    
    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices);
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }
    
    @Override
    public String toString() {
        return "Vertices: " + vertices + ", Edges: " + edges;
    }
}

class Edge {
    private final String source;
    private final String target;
    private final int weight;
    
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    private void checkRep() {
        assert weight >= 0;
    }
    
    public String getSource() {
        return source;
    }
    
    public String getTarget() {
        return target;
    }
    
    public int getWeight() {
        return weight;
    }
    
    @Override
    public String toString() {
        return "Edge(" + source + " -> " + target + ", weight=" + weight + ")";
    }
}
