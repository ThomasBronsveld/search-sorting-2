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


        String[] yellowline2 = {"yellow", "Grote Sluis", "Ymeerdijk", "Haven", "Nobelplein", "Violetplantsoen", "Oostvaarderss", "Grote Sluis"};
        String[] greenline2 = {"green", "Ymeerdijk", "Trojelaan", "Steigerplein", "Swingstraat", "Bachgracht", "Nobelplein", ""};
        String[] redline2 = {"red", "Haven", "Marken", "Steigerplein", "Centrum", "Meridiaan", "Dukdalf", "Oostvaarders"};
        String[] blueline2 = {"blue", "Trojelaan", "Coltrane Cirkel", "Meridiaan", "Robijnpark", "Violetplantsoen"};
        String[] purpleline2 = {"purple", "Grote Sluis", "Grootzeil", "Coltrane Cirkel", "Centrum", "Swingstraat"};

        double[] yellowLineWeights = {26.0, 19.0, 37.0, 25.0, 22.0, 28.0 };
        double[] greenLineWeights = {5.0, 3.7, 6.9, 3.9, 3.4};
        double[] redLineWeights = {4.5, 4.7, 6.1, 3.5, 5.4, 5.6};

        Builder builder = new Builder();

        Builder builder2 = new Builder();

        builder2.addLine(yellowline2);
        builder2.addLine(greenline2);
        builder2.addLine(redline2);
        builder2.addLine(blueline2);
        builder2.addLine(purpleline2);
        //Line is toegevoegd aan de lijst van lijnen.
        //Stations zijn toegevoegd aan de 'StationsOnLine' list van redLine.
        //GREENLINE GEEFT ERRROR omdat hij oneven is
        //builder.addLine(blueLine);
        builder.addLine(redLine);
        builder.addLine(blueLine);
        builder.addLine(greenLine);
        builder.addLine(yellowLine);

        builder2.buildStationSet();
        //Build a Set of stations, voegt ook de Line toe aan de station
        builder.buildStationSet();

        builder2.addLinesToStations();
        //Atm een waardeloze methode
        builder.addLinesToStations();
//
        builder.buildConnections();
        //Bouw connecties van opeenvolgende stations in een Line
        builder.buildConnections();

        TransportGraph transportGraph = builder.build();
//
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
//
        // A - 5
        System.out.println("\nOverview of all the paths from all stations to all other stations with the least connections:");
        System.out.println("-----------------------------------------------");

        for (Station s : transportGraph.getStationList()) {
            for (Station s2 : transportGraph.getStationList()) {
                if(!s.getStationName().equals(s2.getStationName())) {
                    DepthFirstSearch dfp = new DepthFirstSearch(transportGraph, s.getStationName(), s2.getStationName());
                    dfp.search();
                    System.out.println("DepthFirstPath: " + dfp);
//
                    BreadhFirstSearch bfp = new BreadhFirstSearch(transportGraph, s.getStationName(), s2.getStationName());
                    bfp.search();
                    System.out.println("BreadthFirstPath: " + bfp);

                    System.out.println("-----------------------------------------------");
                }
            }
        }
////
////
////       // Uncomment to test the builder:
        System.out.println(transportGraph);

//
    }
}
