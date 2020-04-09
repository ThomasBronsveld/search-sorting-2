package nl.hva.ict.se.sands;

import graphalgorithms.BreadhFirstSearch;
import model.Station;
import model.TransportGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BreadthFirstSearchTest {

    protected BreadhFirstSearch breadhFirstSearchfs;

    TransportGraph transportGraph;

    @BeforeEach
    public void setup() {
        String[] redLine = {"red", "metro", "A", "B", "C", "D"};
        String[] blueLine = {"blue", "metro", "E", "B", "F", "G"};
        String[] greenLine = {"green", "metro", "H", "I", "C", "G", "J"};
        String[] yellowLine = {"yellow", "bus", "A", "E", "H", "D", "G", "A"};

        TransportGraph.Builder builder = new TransportGraph.Builder();

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

        transportGraph = builder.build();
    }

    @Test
    public void transferTest1() {
        breadhFirstSearchfs = new BreadhFirstSearch(transportGraph, "E", "J");
        breadhFirstSearchfs.search();
        assertEquals(1, breadhFirstSearchfs.getTransfers());
    }

    @Test
    public void transferTest2() {
        breadhFirstSearchfs = new BreadhFirstSearch(transportGraph, "I", "F");
        breadhFirstSearchfs.search();
        assertEquals(4, breadhFirstSearchfs.getTransfers());
    }

    @Test
    public void visitedNodesTest() {
        ArrayList<String> listStation = new ArrayList<>();
        ArrayList<String> listStations = new ArrayList<>(Arrays.asList("E", "A", "B", "H", "G", "C", "F",
                "D", "I", "J"));
        breadhFirstSearchfs = new BreadhFirstSearch(transportGraph, "E", "J");
        breadhFirstSearchfs.search();
        for (Station station : breadhFirstSearchfs.getNodesVisited()
        ) {
            listStation.add(station.getStationName());
        }
        assertEquals(listStations, listStation);
    }

    @Test
    public void nodesVisitedTest2() {

        ArrayList<String> listStations = new ArrayList<>(Arrays.asList("F", "B", "G", "A", "C", "E",
                "D", "J", "I", "H"));
        ArrayList<String> listStation = new ArrayList<>();

        breadhFirstSearchfs = new BreadhFirstSearch(transportGraph, "F", "J");
        breadhFirstSearchfs.search();

        for (Station station : breadhFirstSearchfs.getNodesVisited()
        ) {
            listStation.add(station.getStationName());
        }

        assertEquals(listStations, listStation);
    }

    @Test
    public void edgeToTest() {
        int[] testArray = {4, 4, 1, 7, -1, 1, 0, 4, 7, 6};
        breadhFirstSearchfs = new BreadhFirstSearch(transportGraph, "E", "J");
        breadhFirstSearchfs.search();
        assertArrayEquals(testArray, breadhFirstSearchfs.getEdgeTo());
    }
}