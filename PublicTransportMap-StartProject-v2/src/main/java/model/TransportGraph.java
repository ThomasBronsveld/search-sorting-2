package model;

import java.util.*;

public class TransportGraph {

    private int numberOfStations;
    private int numberOfConnections;
    private List<Station> stationList;
    private Map<String, Integer> stationIndices;
    private List<Integer>[] adjacencyLists;
    private Connection[][] connections;

    public TransportGraph(int size) {
        this.numberOfStations = size;
        stationList = new ArrayList<>(size);
        stationIndices = new HashMap<>();
        connections = new Connection[size][size];
        adjacencyLists = (List<Integer>[]) new List[size];
        for (int vertex = 0; vertex < size; vertex++) {
            adjacencyLists[vertex] = new LinkedList<>();
        }
    }

    /**
     * @param vertex Station to be added to the stationList
     *               The method also adds the station with it's index to the map stationIndices
     */
    public void addVertex(Station vertex) {
        // TODO
        stationList.add(vertex);
        stationIndices.put(vertex.getStationName(), stationList.size());
    }


    /**
     * Method to add an edge to a adjacencyList. The indexes of the vertices are used to define the edge.
     * Method also increments the number of edges, that is number of Connections.
     * The graph is bidirectional, so edge(to, from) should also be added.
     *
     * @param from
     * @param to
     */
    private void addEdge(int from, int to) {
        //We willen een List van een integer array([station linkerkant, station rechterkant) terugkrijgen.
        /*
        * Strikt van het voorbeeld genomen met gekleurde lijnen:
        * station B heeft als Adjecencies: {[E, F], [A, C]}
        * station G heeft als Adjecencies: {[F, J], [A, D]}
         */
        Station fromStation = stationList.get(from);
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(Line l : fromStation.getLines()) {
            for (int i = 0; i < l.getStationsOnLine().size(); i++) {
                if (l.getStationsOnLine().get(i).equals(fromStation)) {
                    list.add(stationList.indexOf(l.getStationsOnLine().get(i - 1)));
                    list.add(stationList.indexOf(l.getStationsOnLine().get(i + 1)));
                }

            }
            adjacencyLists[from] = list;
            System.out.println("Dichtbijgelegen stations/adj: " + list);

            // TODO
            numberOfConnections++;
            //from 6 to = 9
            Connection c = new Connection(stationList.get(from), stationList.get(to));
            connections[from][to] = c; //zet Connection object neer.
        }

    }


    /**
     * Method to add an edge in the form of a connection between stations.
     * The method also adds the edge as an edge of indices by calling addEdge(int from, int to).
     * The method adds the connection to the connections 2D-array.
     * The method also builds the reverse connection, Connection(To, From) and adds this to the connections 2D-array.
     *
     * @param connection The edge as a connection between stations
     */
    public void addEdge(Connection connection) {
        // TODO
        //Allebei de richtingen
        this.addEdge(stationList.indexOf(connection.getFrom()), stationList.indexOf(connection.getTo()));
        this.addEdge(stationList.indexOf(connection.getTo()), stationList.indexOf(connection.getFrom()));

    }

    public List<Integer> getAdjacentVertices(int index) {
        return adjacencyLists[index];
    }

    public Connection getConnection(int from, int to) {
        return connections[from][to];
    }

    public int getIndexOfStationByName(String stationName) {
        return stationIndices.get(stationName);
    }

    public Station getStation(int index) {
        return stationList.get(index);
    }

    public int getNumberOfStations() {
        return numberOfStations;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    public int getNumberEdges() {
        return numberOfConnections;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append(String.format("Graph with %d vertices and %d edges: \n", numberOfStations, numberOfConnections));
        for (int indexVertex = 0; indexVertex < numberOfStations; indexVertex++) {
            resultString.append(stationList.get(indexVertex) + ": ");
            int loopsize = adjacencyLists[indexVertex].size() - 1;
            for (int indexAdjacent = 0; indexAdjacent < loopsize; indexAdjacent++) {
                resultString.append(stationList.get(adjacencyLists[indexVertex].get(indexAdjacent)).getStationName() + "-");
            }
            //resultString.append(stationList.get(adjacencyLists[indexVertex].get(loopsize)).getStationName() + "\n");
        }
        return resultString.toString();
    }


    /**
     * A Builder helper class to build a TransportGraph by adding lines and building sets of stations and connections from these lines.
     * Then build the graph from these sets.
     */
    public static class Builder {

        private Set<Station> stationSet;
        private List<Line> lineList;
        private Set<Connection> connectionSet;

        public Builder() {
            lineList = new ArrayList<>();
            stationSet = new HashSet<>();
            connectionSet = new HashSet<>();
        }

        /**
         * Method to add a line to the list of lines and add stations to the line.
         *
         * @param lineDefinition String array that defines the line. The array should start with the name of the line,
         *                       followed by the type of the line and the stations on the line in order.
         * @return
         */
        public Builder addLine(String[] lineDefinition) {
            Line newLine = new Line(lineDefinition[0], lineDefinition[1]);

            //Lees alles achter de naam en type van de Line uit (dus stations) en voeg deze toe het lineObject
            for(int i = 2; i < lineDefinition.length; i++){
                Station station;
                newLine.addStation(station = new Station(lineDefinition[i]));
                station.addLine(newLine);

            }
            lineList.add(newLine);
            return this;
        }


    /**
     * Method that reads all the lines and their stations to build a set of stations.
     * Stations that are on more than one line will only appear once in the set.
     *
     * @return
     */
    public Builder buildStationSet() {
        // TODO
        for (Line l : lineList) {
            //Atm zijn er geen stations op de line
            for (Station s : l.getStationsOnLine()
                 ) {
                //Volgensmij kan die if weg, omdat in Set sws altijd maar 1 keer bijvoorbeeld 'A' kan voorkomenw
                System.out.println("Heeel veel stations hier");
                System.out.println(s.getStationName());
                if(!stationSet.contains(s)){
                    stationSet.add(s);

                }
            }
        }
        return this;
    }

    /**
     * For every station on the set of station add the lines of that station to the lineList in the station
     *
     * @return
     */
    public Builder addLinesToStations() {
        // TODO
        for (Station s : stationSet) {
            for(Line line : s.getLines()){
                s.addLine(line);
                System.out.println("Ik kom hier lettterlijk nooit");
            }
            lineList.addAll(s.getLines());
        }
        return this;
    }

    /**
     * Method that uses the list of Lines to build connections from the consecutive stations in the stationList of a line.
     *
     * @return
     */
    public Builder buildConnections() {
        // TODO
        for (Line l : lineList) {
            for (int i = 0; i < l.getStationsOnLine().size(); i++) {
                if (i == l.getStationsOnLine().size() - 1){
                    break;
                }
                connectionSet.add(new Connection(l.getStationsOnLine().get(i), l.getStationsOnLine().get(i + 1)));
            }
        }
        System.out.println(connectionSet);
        return this;
    }

    /**
     * Method that builds the graph.
     * All stations of the stationSet are addes as vertices to the graph.
     * All connections of the connectionSet are addes as edges to the graph.
     *
     * @return
     */
    public TransportGraph build() {
        TransportGraph graph = new TransportGraph(stationSet.size());
        for(Station s : stationSet){
            graph.addVertex(s);
        }
        for(Connection c : connectionSet){
            graph.addEdge(c);
        }

        // TODO
        return graph;
    }

}
}
