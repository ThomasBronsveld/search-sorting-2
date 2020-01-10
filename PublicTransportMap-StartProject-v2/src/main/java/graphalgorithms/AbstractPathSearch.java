package graphalgorithms;

import javafx.geometry.VerticalDirection;
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


    public AbstractPathSearch(TransportGraph graph, String start, String end) {
        startIndex = graph.getIndexOfStationByName(start);
        endIndex = graph.getIndexOfStationByName(end);
        this.graph = graph;
        nodesVisited = new ArrayList<>();
        marked = new boolean[graph.getNumberOfStations()];
        edgeTo = new int[graph.getNumberOfStations()];
        verticesInPath = new LinkedList<>();
        nodesVisited = new LinkedList<>();
    }

    public abstract void search();

    /**
     * @param vertex Determines whether a path exists to the station as an index from the start station
     * @return
     */
    public boolean hasPathTo(int vertex) {
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
        // TODO
//        if (hasPathTo(vertex)) return;
//
//        for (int v = vertex; v != startIndex; v = edgeTo[v]) {
//            verticesInPath.push(v);
//        }
//
//        verticesInPath.addFirst(startIndex);
//
//        for (Integer v : verticesInPath) {
//            nodesInPath.add(graph.getStation(v));
//        }
//
//        countTransfers();
        if (!hasPathTo(vertex)) {
            for (int i = vertex; i != startIndex; i = edgeTo[i]) {
                verticesInPath.push(i);
            }
            verticesInPath.addFirst(startIndex);

            for (Integer v : verticesInPath) {
                nodesInPath.add(graph.getStation(v));
            }
            countTransfers();
        }else{
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
//        if (verticesInPath.size() <= 1) {
//            return;
//        }
//
//        Line currentLine = graph.getStation(endIndex).getCommonLine(graph.getStation(edgeTo[endIndex]));
//        for (int v = endIndex; v != startIndex; v = edgeTo[v]) {
//            if (!currentLine.getStationsOnLine().contains(graph.getStation(edgeTo[v]))) {
//                currentLine = graph.getStation(v).getCommonLine(graph.getStation(edgeTo[v]));
//                transfers++;
//            }
//        }

//        Check if there are transfers
        boolean hasTransfers = verticesInPath.size() > 1;
        //there are no transfers,  break function
        if (!hasTransfers) return;
        //Get station from graph with endindex
        Station s = graph.getStation(endIndex);
        //Get the commonline in the graph
        Line commonLine = s.getCommonLine(graph.getStation(edgeTo[endIndex]));
        for (int i = endIndex; i < startIndex; i = edgeTo[i]) {
            if (!commonLine.getStationsOnLine().contains(graph.getStation(edgeTo[i]))) {
                commonLine = graph.getStation(i).getCommonLine(graph.getStation(edgeTo[i]));
                transfers += 1;
            }
        }
    }


    /**
     * Method to print all the nodes that are visited by the search algorithm implemented in one of the subclasses.
     */
    public void printNodesInVisitedOrder() {
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

}
