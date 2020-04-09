package nl.hva.ict.se.sands;

import graphalgorithms.DepthFirstSearch;
import model.Station;
import model.TransportGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DepthFirstSearchTest {

    protected DepthFirstSearch dfs;

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
        dfs = new DepthFirstSearch(transportGraph, "E", "J");
        dfs.search();
        assertEquals(3, dfs.getTransfers());
    }

    @Test
    public void transferTest2() {
        dfs = new DepthFirstSearch(transportGraph, "F", "J");
        dfs.search();
        assertEquals(2, dfs.getTransfers());
    }

    @Test
    public void visitedNodesTest() {
        ArrayList<String> listStation = new ArrayList<>();
        ArrayList<String> listStations = new ArrayList<>(Arrays.asList("E", "A", "B", "C", "D", "G",
                "F", "J", "H", "I"));
        dfs = new DepthFirstSearch(transportGraph, "E", "J");
        dfs.search();
        for (Station station: dfs.getNodesVisited()
             ) {
            listStation.add(station.getStationName());
        }
        assertEquals(listStations, listStation);
    }

    @Test
    public void nodesVisitedTest2() {

        ArrayList<String> listStations = new ArrayList<>(Arrays.asList("F", "B", "A", "E","H", "D",
                "C", "G", "J", "I"));
        ArrayList<String> listStation = new ArrayList<>();

        dfs = new DepthFirstSearch(transportGraph, "F", "J");
        dfs.search();

        for (Station station: dfs.getNodesVisited()
        ) {
            listStation.add(station.getStationName());
        }

        assertEquals(listStations, listStation);
    }

    @Test
    public void edgeToTest(){
        int[] testArray = {4,0,1,2,-1,6,3,3,7,6};
        dfs = new DepthFirstSearch(transportGraph, "E", "J");
        dfs.search();
        assertArrayEquals(testArray, dfs.getEdgeTo());
    }
}
