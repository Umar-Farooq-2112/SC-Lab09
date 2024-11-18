/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();

    private void checkRep() {
        for (Vertex vertex : vertices) {
            assert vertex.getEdges().values().stream().allMatch(weight -> weight >= 0);
        }
    }
    
    @Override
    public boolean add(String vertex) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(vertex)) return false;
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        Vertex srcVertex = null, tgtVertex = null;
        for (Vertex v : vertices) {
            if (v.getLabel().equals(source)) srcVertex = v;
            if (v.getLabel().equals(target)) tgtVertex = v;
        }
        if (srcVertex == null) srcVertex = new Vertex(source);
        if (tgtVertex == null) tgtVertex = new Vertex(target);
        
        int prevWeight = srcVertex.setEdge(target, weight);
        if (!vertices.contains(srcVertex)) vertices.add(srcVertex);
        if (!vertices.contains(tgtVertex)) vertices.add(tgtVertex);
        
        checkRep();
        return prevWeight;
    }
    
    @Override
    public boolean remove(String vertex) {
        Vertex v = null;
        for (Vertex vert : vertices) {
            if (vert.getLabel().equals(vertex)) {
                v = vert;
                break;
            }
        }
        if (v == null) return false;
        
        vertices.remove(v);
        for (Vertex vert : vertices) {
            vert.removeEdge(vertex);
        }
        
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        Set<String> vertexSet = new HashSet<>();
        for (Vertex v : vertices) {
            vertexSet.add(v.getLabel());
        }
        return vertexSet;
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex v : vertices) {
            Integer weight = v.getEdges().get(target);
            if (weight != null) sources.put(v.getLabel(), weight);
        }
        return sources;
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(source)) {
                return new HashMap<>(v.getEdges());
            }
        }
        return new HashMap<>();
    }
    
    @Override
    public String toString() {
        return "Vertices: " + vertices;
    }
}

class Vertex {
    private final String label;
    private final Map<String, Integer> edges = new HashMap<>();
    
    public Vertex(String label) {
        this.label = label;
        checkRep();
    }
    
    private void checkRep() {
        assert edges.values().stream().allMatch(weight -> weight >= 0);
    }
    
    public String getLabel() {
        return label;
    }
    
    public Map<String, Integer> getEdges() {
        return new HashMap<>(edges);
    }
    
    public int setEdge(String target, int weight) {
        int prevWeight = edges.getOrDefault(target, 0);
        if (weight == 0) {
            edges.remove(target);
        } else {
            edges.put(target, weight);
        }
        checkRep();
        return prevWeight;
    }
    
    public void removeEdge(String target) {
        edges.remove(target);
        checkRep();
    }
    
    @Override
    public String toString() {
        return "Vertex(" + label + " -> " + edges + ")";
    }
}