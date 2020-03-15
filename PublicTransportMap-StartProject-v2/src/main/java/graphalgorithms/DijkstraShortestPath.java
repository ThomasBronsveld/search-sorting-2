package graphalgorithms;

import model.Connection;
import model.IndexMinPQ;
import model.Station;
import model.TransportGraph;

import java.util.Stack;

/**
 * @Project $(PROJECT_NAME)
 * @Author Thomas Bronsveld <Thomas.Bronsveld@hva.nl>
 */
public class DijkstraShortestPath extends AbstractPathSearch {
    private double totalweight;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public DijkstraShortestPath(TransportGraph graph, String start, String end) {
        super(graph, start, end);

        pq = new IndexMinPQ<Double>(graph.getNumberOfStations());
        distTo = new double[graph.getNumberOfStations()];

        for (int v = 0; v < graph.getNumberOfStations(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[startIndex] = 0.0;
    }

    @Override
    public void search() {
        int s = startIndex;
        edgeTo[s] = -1;
        pq.insert(s, (double) 0);

        while (!pq.isEmpty() && s != endIndex){
            s = pq.delMin();

            if(s == endIndex) {
                totalweight = distTo[s];
            }

            nodesVisited.add(graph.getStation(s));
            for (Integer i : graph.getAdjacentVertices(s)) {
                Connection connection = graph.getConnection(s, i);
                if (distTo[i] > (distTo[s] + connection.getWeight())) {

                    // Add the connection and weight
                    distTo[i] = distTo[s] + connection.getWeight();

                    // Change the edge to the vertex
                    edgeTo[i] = s;
                    //pq bevat marken en zijn gewicht.
                    //Steigerplein.
                    if (pq.contains(i)) {
                        pq.decreaseKey(i, distTo[i]);
                    } else {
                        pq.insert(i, distTo[i]);
                    }
                }
            }
        }
        pathTo(endIndex);
    }

    @Override
    public boolean hasPathTo(int vertex) {
        return distTo[vertex] < Double.POSITIVE_INFINITY;
    }

//    private double weightOfRoute() {
//
//    }

    public double getTotalWeight() {
        return totalweight;
    }
}
