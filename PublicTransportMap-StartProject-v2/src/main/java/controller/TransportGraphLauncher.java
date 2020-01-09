package controller;

import model.TransportGraph;
import model.TransportGraph.Builder;

public class TransportGraphLauncher {

    public static void main(String[] args) {
        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        // TODO Use the builder to build the graph from the String array.
        Builder builder = new Builder();

        //Line is toegevoegd aan de lijst van lijnen.
        //Stations zijn toegevoegd aan de 'StationsOnLine' list van redLine.
        //GREENLINE GEEFT ERRROR omdat hij oneven is
        //builder.addLine(blueLine);
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




       // Uncomment to test the builder:
        System.out.println(transportGraph);

//        Uncommented to test the DepthFirstPath algorithm
        /*DepthFirstPath dfpTest = new DepthFirstPath(transportGraph, "E", "J");
        dfpTest.search();
        System.out.println(dfpTest);
        dfpTest.printNodesInVisitedOrder();
        System.out.println();*/

//        Uncommented to test the BreadthFirstPath algorithm
        /*BreadthFirstPath bfsTest = new BreadthFirstPath(transportGraph, "E", "J");
        bfsTest.search();
        System.out.println(bfsTest);
        bfsTest.printNodesInVisitedOrder();*/

    }
}
