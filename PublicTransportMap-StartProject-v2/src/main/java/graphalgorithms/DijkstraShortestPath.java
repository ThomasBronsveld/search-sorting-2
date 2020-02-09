package graphalgorithms;

import model.DirectedEdge;
import model.Edge;
import model.EdgeWeightedDigraph;
import model.IndexMinPQ;

import java.util.Stack;

/**
 * @Project $(PROJECT_NAME)
 * @Author Thomas Bronsveld <Thomas.Bronsveld@hva.nl>
 */
public class DijkstraShortestPath {

    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public DijkstraShortestPath(EdgeWeightedDigraph G, int s) {
        pq = new IndexMinPQ<Double>(G.V());

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        pq.insert(s, 0.0);

        while(!pq.isEmpty()) {
            relax(G, pq.delMin());
        }
    }

    private void relax(EdgeWeightedDigraph G, int v){
        for(DirectedEdge e: G.adj(v)){
            int w = e.to();

            if(distTo[w] > distTo[v] + e.weight()){
                distTo[w] = distTo[v] + e.weight();
                if(pq.contains(w)){
                    pq.changeKey(w, distTo[w]);
                } else {
                    pq.insert(w,distTo[w]);
                }
            }
        }
    }

    public double distTo(int v) {
        return 0.0;
    }

    public boolean hasPathTo(int v) {
        return true;
    }

    public Iterable<DirectedEdge> pathTo(int v){
        if(!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for(DirectedEdge e = edgeTo[v]; e != null; e=edgeTo[e.from()]){
            path.push(e);
        }
        return path;
    }
}
