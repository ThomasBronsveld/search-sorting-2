package controller;

import graphalgorithms.BreadhFirstSearch;
import graphalgorithms.DepthFirstSearch;
import graphalgorithms.DijkstraShortestPath;
import model.Station;
import model.TransportGraph;
import model.TransportGraph.Builder;

public class TransportGraphLauncher {

    public static void main(String[] args) {
//        assignmentA();
        assignmentB();
//        assignmentC();
    }

    private static void depthAndBreadthFirstSearch(TransportGraph graph, String start, String end) {
        System.out.println("Result of DepthFirstSearch:");
        DepthFirstSearch dfpTest = new DepthFirstSearch(graph, start, end);
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();

        System.out.println("Result of BreadthFirstPath:");
        BreadhFirstSearch bfsTest = new BreadhFirstSearch(graph, start, end);
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();
    }
    /**
     *
     */
    public static void assignmentA() {
        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        Builder builder = new Builder();

        builder.addLine(blueLine);
        builder.addLine(redLine);
        builder.addLine(blueLine);
        builder.addLine(greenLine);
        builder.addLine(yellowLine);

        //Build a Set of stations, voegt ook de Line toe aan de station
        builder.buildStationSet();

        //Atm een waardeloze methode
        builder.addLinesToStations();

        //Bouw connecties van opeenvolgende stations in een Line
        builder.buildConnections();

        TransportGraph transportGraph = builder.build();

        depthAndBreadthFirstSearch(transportGraph, "E", "J");

    }

    /**
     *
     */
    public static void assignmentB() {
        String[] yellowline2 = {"yellow", "bus","Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarders", "Grote Sluis"};
        String[] greenline2 = {"green", "metro","Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein"};
        String[] redline2 = {"red", "metro","Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
        String[] blueline2 = {"blue", "metro","Trojelaan", "Coltrane Cirkel", "Meridiaan", "Robijnpark", "Violetplantsoen"};
        String[] purpleline2 = {"purple", "metro","Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};

        // TODO Use the builder to build the graph from the String array.
        double[] yellowLineWeights = {26.0, 19.0, 37.0, 25.0, 22.0, 28.0 };
        double[] greenLineWeights = {5.0, 3.7, 6.9, 3.9, 3.4};
        double[] redLineWeights = {4.5, 4.7, 6.1, 3.5, 5.4, 5.6};
        double[] purpleLineWeights = {6.2, 5.2, 3.8, 3.6};
        double[] blueLineWeights = {6.0, 5.3, 5.1, 3.3};


        Builder builder2 = new Builder();
        //red,blue,purple green yellow
        builder2.addLine(redline2);
        builder2.addLine(blueline2);
        builder2.addLine(purpleline2);
        builder2.addLine(greenline2);
        builder2.addLine(yellowline2);

        //Build a Set of stations, voegt ook de Line toe aan de station
        builder2.buildStationSet();
        //Atm een waardeloze methode
        builder2.addLinesToStations();
//


        //Bouw connecties van opeenvolgende stations in een Line
        builder2.addWeightsToConnections(yellowLineWeights, 4);
        builder2.addWeightsToConnections(greenLineWeights, 3);
        builder2.addWeightsToConnections(redLineWeights, 0);
        builder2.addWeightsToConnections(blueLineWeights, 1);
        builder2.addWeightsToConnections(purpleLineWeights, 2);

        TransportGraph transportGraph2 = builder2.build();
//
        System.out.println(transportGraph2.toString());

//        depthAndBreadthFirstSearch(transportGraph2, "Trojelaan", "Violetplantsoen");

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(transportGraph2, "Marken", "Oostvaarders");
        dijkstraShortestPath.search();
    }

    /**
     *
     */
    public static void assignmentC() {

    }
}
