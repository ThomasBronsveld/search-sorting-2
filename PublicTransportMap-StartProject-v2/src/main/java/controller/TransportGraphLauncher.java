package controller;

import model.Connection;
import model.Station;
import model.TransportGraph;
import model.TransportGraph.Builder;
import model.Connection;

public class TransportGraphLauncher {

    public static void main(String[] args) {
          String[] redLine = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        // TODO Use the builder to build the graph from the String array.
        //Als size in dit geval het aantal vertices genomen
       // TransportGraph transportGraph = new TransportGraph(10);
        Builder builder = new Builder();


       // Hier worden lineobject aangemaakt en daarbij worden ook per Line de stations toegevoegd
        builder.addLine(redLine);
        builder.addLine(blueLine);
        builder.addLine(greenLine);
        builder.addLine(yellowLine);


        builder.buildStationSet();
        builder.buildConnections();

        TransportGraph transportGraph = builder.build();


        String resultString = transportGraph.toString();
        System.out.println(resultString);


//        Uncomment to test the builder:
//        System.out.println(transportGraph);

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
