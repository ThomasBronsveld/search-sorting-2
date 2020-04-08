package nl.hva.ict.se.sands;

import graphalgorithms.DijkstraShortestPath;
import model.TransportGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DijkstraTest {

    DijkstraShortestPath dijkstra;

    TransportGraph transportGraph;

    @BeforeEach
    public void setup() {
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

        TransportGraph.Builder builder2 = new TransportGraph.Builder();
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
//        builder2.addWeightsToConnections(yellowLineWeights, 4);
//        builder2.addWeightsToConnections(greenLineWeights, 3);
//        builder2.addWeightsToConnections(redLineWeights, 0);
//        builder2.addWeightsToConnections(blueLineWeights, 1);
//        builder2.addWeightsToConnections(purpleLineWeights, 2);

        transportGraph = builder2.build();

    }

    @Test
    public void weightTest() {
        dijkstra = new DijkstraShortestPath(transportGraph, "Trojelaan", "Nobelplein");
        dijkstra.search();
        DecimalFormat df = new DecimalFormat("0.0");
        double dijkstraGewicht;
        try {
            dijkstraGewicht = Double.parseDouble(df.format(dijkstra.getTotalWeight()));
            assertEquals(17.9, dijkstraGewicht);
        } catch (NumberFormatException ex) {
            ex.getMessage();
        }
    }

    @Test
    public void weightTest2() {
        dijkstra = new DijkstraShortestPath(transportGraph, "Grote Sluis", "Ymeerdijk");
        dijkstra.search();
        DecimalFormat df = new DecimalFormat("0.0");
        double dijkstraGewicht;
        try {
            dijkstraGewicht = Double.parseDouble(df.format(dijkstra.getTotalWeight()));
            assertEquals(22.2, dijkstraGewicht);
        } catch (NumberFormatException ex) {
            ex.getMessage();
        }
    }
}
