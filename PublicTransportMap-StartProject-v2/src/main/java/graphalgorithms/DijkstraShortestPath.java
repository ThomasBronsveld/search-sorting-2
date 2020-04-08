package graphalgorithms;

import model.*;

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
        System.out.println(pq.size());

        distTo = new double[graph.getNumberOfStations()];

        for (int v = 0; v < graph.getNumberOfStations(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[startIndex] = 0.0;
    }

    @Override
    public void search() {
        System.out.println("dit is startindex: " + startIndex);
        int s = startIndex;
        edgeTo[s] = -1;
        pq.insert(s, (double) 0);

        while (!pq.isEmpty() && s != endIndex){
            System.out.println(pq.size());
            if(pq.size() > 1) {
                System.out.println(pq.minKey());
            }
            s = pq.delMin();
            if(s == endIndex) {
                totalweight = distTo[s];
            }

            nodesVisited.add(graph.getStation(s));
            for (Integer i : graph.getAdjacentVertices(s)) {
                Connection connection = graph.getConnection(s, i);
                edgeToType[i] = connection.getLine();
                if(graph.getStation(s).getLines() != graph.getStation(i).getLines()){
                    System.out.println("hierzo");
                    System.out.println(graph.getStation(s).getCommonLines(graph.getStation(i)));
                }
                System.out.println("De from");
                System.out.println(graph.getStation(s).getStationName());
                System.out.println(graph.getStation(s).getLines());
                System.out.println("De TO");
                System.out.println(graph.getStation(i).getStationName());
                System.out.println(graph.getStation(i).getLines());
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

    public double getTotalWeight() {
        return totalweight;
    }

    private double getTransferPenatly(int from, int to) {
        return 0.0;
    }
}
