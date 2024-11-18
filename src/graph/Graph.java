package graph;

import java.util.Map;
import java.util.Set;

public interface Graph<L> {

    public static <L> Graph<L> empty() {
        return (Graph<L>) new ConcreteEdgesGraph();
    }
    
    public boolean add(L vertex);
    public int set(L source, L target, int weight);
    public boolean remove(L vertex);    
    public Set<L> vertices();
    public Map<L, Integer> sources(L target);
    public Map<L, Integer> targets(L source);
}
