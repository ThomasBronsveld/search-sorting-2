package controller;

import graphalgorithms.BreadhFirstSearch;
import graphalgorithms.DepthFirstSearch;
import model.Station;
import model.TransportGraph;
import model.TransportGraph.Builder;

public class TransportGraphLauncher {

    public static void main(String[] args) {
        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        // TODO Use the builder to build the graph from the String array.
        TransportGraph transportGraph;
        TransportGraph.Builder builder = new TransportGraph.Builder()
                .addLine(redLine)
                .addLine(blueLine)
                .addLine(greenLine)
                .addLine(yellowLine)
                .buildStationSet()
                .addLinesToStations()
                .buildConnections();

        transportGraph = builder.build();

        // Uncomment to test the builder:
        System.out.println(transportGraph.toString());

        // DepthFirstPath algorithm
        System.out.println("Result of DepthFirstSearch:");
        DepthFirstSearch dfpTest = new DepthFirstSearch(transportGraph, "E", "J");
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();

        // BreadthFirstPath algorithm
        System.out.println("Result of BreadthFirstPath:");
        BreadhFirstSearch bfsTest = new BreadhFirstSearch(transportGraph, "E", "J");
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();

        // A - 5
        System.out.println("\nOverview of all the paths from all stations to all other stations with the least connections:");
        System.out.println("-----------------------------------------------");

        for (Station s : transportGraph.getStationList()) {
            for (Station s2 : transportGraph.getStationList()) {
                if(!s.getStationName().equals(s2.getStationName())) {
                    DepthFirstSearch dfp = new DepthFirstSearch(transportGraph, s.getStationName(), s2.getStationName());
                    dfp.search();
                    System.out.println("DepthFirstPath: " + dfp);

                    BreadhFirstSearch bfp = new BreadhFirstSearch(transportGraph, s.getStationName(), s2.getStationName());
                    bfp.search();
                    System.out.println("BreadthFirstPath: " + bfp);

                    System.out.println("-----------------------------------------------");
                }
            }
        }

//        Builder builder = new Builder();
//
//        //Line is toegevoegd aan de lijst van lijnen.
//        //Stations zijn toegevoegd aan de 'StationsOnLine' list van redLine.
//        //GREENLINE GEEFT ERRROR omdat hij oneven is
//        //builder.addLine(blueLine);
//        builder.addLine(redLine);
//        builder.addLine(blueLine);
//        builder.addLine(greenLine);
//        builder.addLine(yellowLine);
//
//        //Build a Set of stations, voegt ook de Line toe aan de station
//        builder.buildStationSet();
//
//        //Atm een waardeloze methode
//        builder.addLinesToStations();
//
//        //Bouw connecties van opeenvolgende stations in een Line
//        builder.buildConnections();
//
//        TransportGraph transportGraph = builder.build();
//
//        System.out.println(transportGraph.toString());
//
//        // DepthFirstPath algorithm
//        System.out.println("Result of DepthFirstSearch:");
//        DepthFirstSearch dfpTest = new DepthFirstSearch(transportGraph, "E", "J");
//        dfpTest.search();
//        System.out.println(dfpTest);
//        dfpTest.printNodesInVisitedOrder();
//        System.out.println();
//
//        // BreadthFirstPath algorithm
//        System.out.println("Result of BreadthFirstPath:");
//        BreadhFirstSearch bfsTest = new BreadhFirstSearch(transportGraph, "E", "J");
//        bfsTest.search();
//        System.out.println(bfsTest);
//        bfsTest.printNodesInVisitedOrder();
//
//        // A - 5
//        System.out.println("\nOverview of all the paths from all stations to all other stations with the least connections:");
//        System.out.println("-----------------------------------------------");
//
//        for (Station s : transportGraph.getStationList()) {
//            for (Station s2 : transportGraph.getStationList()) {
//                if(!s.getStationName().equals(s2.getStationName())) {
//                    DepthFirstSearch dfp = new DepthFirstSearch(transportGraph, s.getStationName(), s2.getStationName());
//                    dfp.search();
//                    System.out.println("DepthFirstPath: " + dfp);
//
//                    BreadhFirstSearch bfp = new BreadhFirstSearch(transportGraph, s.getStationName(), s2.getStationName());
//                    bfp.search();
//                    System.out.println("BreadthFirstPath: " + bfp);
//
//                    System.out.println("-----------------------------------------------");
//                }
//            }
//        }
//
//
//       // Uncomment to test the builder:
//        System.out.println(transportGraph);

//
    }
}
