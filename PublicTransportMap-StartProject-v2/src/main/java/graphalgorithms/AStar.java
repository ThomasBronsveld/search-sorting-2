package graphalgorithms;

import model.*;

public class AStar extends AbstractPathSearch {
    private double totalweight;

    private IndexMinPQ<Double> queue;
    private double[] distTo;

    public AStar(TransportGraph graph, String start, String end) {
        super(graph, start, end);

        queue = new IndexMinPQ<>(graph.getNumberOfStations());
        distTo = new double[graph.getNumberOfStations()];

        for (int v = 0; v < graph.getNumberOfStations(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[startIndex] = 0;
    }

    @Override
    public void search() {
        queue.insert(startIndex, (double) 0);
        edgeTo[startIndex] = -1;
        int s = startIndex;

        if(s == endIndex) {
            totalweight = distTo[s];
        }

        while (!queue.isEmpty() && s != endIndex){
            s = queue.delMin();

            nodesVisited.add(graph.getStation(s));

            for (Integer i : graph.getAdjacentVertices(s)) {
                System.out.println("BEN IN DE HOOOOOOOOOOOOOD");
                Connection connection = graph.getConnection(s, i);
                System.out.println("Connection: " + connection.toString());
                edgeToType[i] = connection.getLine();
                double penalty = getTransferPenalty(s, i);
                System.out.println("Connection weight: " + connection.getWeight());
                System.out.println("Traveltime: " + getTravelTime(i));
                connection.setWeight(connection.getWeight() + penalty);

                double travelTime = getTravelTime(i);
                if ((distTo[i] + travelTime) > (distTo[s] + connection.getWeight() + travelTime)) {

                    // Add the connection and weight
                    distTo[i] = distTo[s] + connection.getWeight();
                    System.out.println(graph.getStation(s).getStationName());
                    System.out.println(graph.getStation(i).getStationName());
                    System.out.println(distTo[i]);
                    // Change the edge to the vertex
                    edgeTo[i] = s;

                    if (queue.contains(i)) {
                        queue.decreaseKey(i, distTo[i] + travelTime);
                    } else {
                        queue.insert(i, distTo[i] + travelTime);
                    }
                }
            }
        }
        pathTo(endIndex);
    }

//    /**
//     *
//     * @param stationIndex
//     * @return
//     */
    private double getTravelTime(int stationIndex) {
        return Math.abs(graph.getStation(stationIndex).getLocation().travelTime(graph.getStation(endIndex).getLocation()));
    }

    /**
     * @param vertex Determines whether a path exists to the station as an index from the start station
     * @return
     */
    @Override
    public boolean hasPathTo(int vertex) {
        return distTo[vertex] < Double.POSITIVE_INFINITY;
    }

    public double getTotalweight() {
        return totalweight;
    }
}