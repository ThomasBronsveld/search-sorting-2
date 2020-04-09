package graphalgorithms;


import model.Connection;
import model.Line;
import model.Station;
import model.TransportGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class that contains methods and attributes shared by the DepthFirstPath en BreadthFirstPath classes
 */
public abstract class AbstractPathSearch {

    protected boolean[] marked;
    protected int[] edgeTo;
    protected int transfers = 0;
    protected List<Station> nodesVisited;
    protected List<Station> nodesInPath;
    protected LinkedList<Integer> verticesInPath;
    protected TransportGraph graph;
    protected final int startIndex;
    protected final int endIndex;
    protected Line[] edgeToType;

    public AbstractPathSearch(TransportGraph graph, String start, String end) {
        startIndex = graph.getIndexOfStationByName(start);
        endIndex = graph.getIndexOfStationByName(end);
        this.graph = graph;
        nodesVisited = new ArrayList<>();
        marked = new boolean[graph.getNumberOfStations()];
        edgeTo = new int[graph.getNumberOfStations()];
        edgeToType = new Line[graph.getNumberOfStations()];
        verticesInPath = new LinkedList<>();
        nodesVisited = new LinkedList<>();
        nodesInPath = new ArrayList<>();
    }

    public abstract void search();

    /**
     * @param vertex Determines whether a path exists to the station as an index from the start station
     * @return
     */
    public boolean hasPathTo(int vertex) {
        //For some reason every marked value is true...
        return marked[vertex];
    }


    /**
     * Method to build the path to the vertex, index of a Station.
     * First the LinkedList verticesInPath, containing the indexes of the stations, should be build, used as a stack
     * Then the list nodesInPath containing the actual stations is build.
     * Also the number of transfers is counted.
     *
     * @param vertex The station (vertex) as an index
     */
    public void pathTo(int vertex) {
        if (hasPathTo(vertex)) {
            for (int i = vertex; i != -1; i = edgeTo[i]) {
                verticesInPath.addFirst(i);
            }


            for (Integer v : verticesInPath) {
                nodesInPath.add(graph.getStation(v));
            }
            countTransfers();
        } else {
            return;
        }
    }




    /**
     * Method to count the number of transfers in a path of vertices.
     * Uses the line information of the connections between stations.
     * If to consecutive connections are on different lines there was a transfer.
     */
    public void countTransfers() {
//        // TODO
        //Pak het path
        ArrayList<Integer> stationsInPath = new ArrayList<>();
        Line testLine = null;
        String transportType = null;
        for (int i = 0; i < verticesInPath.size() - 1; i++) {
            Connection currentConnection = graph.getConnection(verticesInPath.get(i), verticesInPath.get(i + 1));
            System.out.println(currentConnection);
            Line currLine = currentConnection.getLine();
            System.out.println(currLine);
            String lineType = currLine.getType();
            System.out.println(lineType);
            if (transportType == null) {

                transportType = lineType;
            } else if (!transportType.equals(lineType)) {

                transfers++;
                transportType = lineType;
            }
        }
    }

        /**
         * Method to print all the nodes that are visited by the search algorithm implemented in one of the subclasses.
         */
        public void printNodesInVisitedOrder(){
            System.out.print("Nodes in visited order: ");
            for (Station vertex : nodesVisited) {
                System.out.print(vertex.getStationName() + " ");
            }

            System.out.println();
        }


    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder(String.format("Path from %s to %s: ", graph.getStation(startIndex), graph.getStation(endIndex)));
        resultString.append(nodesInPath).append(" with " + transfers).append(" transfers");
        return resultString.toString();
    }

    public List<Station> getNodesVisited() {
        return nodesVisited;
    }

    public int getTransfers() {
        return transfers;
    }

    public int[] getEdgeTo() {
        return edgeTo;
    }
    public double getTransferPenalty(int from, int to) {
        System.out.println("eyo penalty");
        double metroPenalty = 6.0;
        double busPenalty = 3.0;
        double noPenalty = 0.0;

        if(edgeToType[from] != null && edgeToType[to] != null) {
            if(!edgeToType[from].getName().equals(edgeToType[to].getName())) {
                if(edgeToType[from].getType().equals(edgeToType[to].getType())) {
                    return metroPenalty;
                } else {
                    return busPenalty;
                }
            }
        }
        return noPenalty;
    }
}
