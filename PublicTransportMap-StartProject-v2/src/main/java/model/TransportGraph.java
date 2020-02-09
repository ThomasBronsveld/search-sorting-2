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
//        System.out.println(stationList.size());
        stationIndices.put(vertex.getStationName(), stationList.indexOf(vertex));
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
        //add one to the numberofconnections variable
        numberOfConnections+=1;
        adjacencyLists[from].add(to);
        adjacencyLists[to].add(from);

    }

//
//
//            // TODO
//            numberOfConnections++;
//            //from 6 to = 9
//            Connection c = new Connection(stationList.get(from), stationList.get(to));
//            connections[from][to] = c; //zet Connection object neer.
//        }


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
        this.addEdge(stationList.indexOf(connection.getFrom()), stationList.indexOf(connection.getTo()));
        int from = this.stationIndices.get(connection.getFrom().getStationName());
        int to = this.stationIndices.get(connection.getTo().getStationName());
        connections[to][from] = connection;
        connections[from][to] = connection;
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
            int x = adjacencyLists[indexVertex].get(loopsize);

            resultString.append(stationList.get(adjacencyLists[indexVertex].get(loopsize)).getStationName() + "\n");

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
            //Add Line to list of lines
            lineList.add(newLine);
            //Begin bij 2 omdat vanaf deze plek de station namen komen in de line definition
            for (int i = 2; i < lineDefinition.length; i++) {
                Station s = new Station(lineDefinition[i]);
                //Voeg station toe aan de Line
                newLine.addStation(s);

            }
            return this;
        }


        /**
         * Method that reads all the lines and their stations to build a set of stations.
         * Stations that are on more than one line will only appear once in the set.
         * DANKZIJ DEZE METHODE WORDT DE STATIONSET GEVULD
         *
         * @return
         */
        public Builder buildStationSet() {
            // TODO
            for (Line l : lineList
            ) {
                //De Lines hebben nu wel stations op de line
                for (Station s : l.getStationsOnLine()
                ) {
                    //Die if statement maakt in principe niet uit omdat het om een Set gaat
                    if (!stationSet.contains(s)) {
                        //Voeg alle stations van line toe aan de stationSet.
                        stationSet.add(s);
                        //Voeg line toe aan station
                        //Dit moet eigenlijk gebeuren in de methode addLinesToStations maar dit is echt 1000keer makkelijker
                        s.addLine(l);
                    }
                }
            }
            return this;
        }

        /**
         * For every station on the set of station add the lines of that station to the lineList in the station
         * Waardeloze methode omdat dit ook in de methode buildStationsOnSet kan met 1 regel s.addLine(l)
         *
         * @return
         */
        public Builder addLinesToStations() {
            // TODO
            for (Station s : stationSet
            ) {
                //get the lines of that station DE STATIONS HEBBEN NOG GEEN LINES
                for (Line line : s.getLines()) {
                    //voeg lijn toe aan LINELIST(SET) IN STATION (CLASS)
                    //Vraag me af of er nog iets moet gebeuren met StationList in de builder class
                    //s.addLine(line);


                }


            }
            return this;
        }

        /**
         * Method that uses the list of Lines to build connections from the consecutive stations in the stationList of a line.
         *
         * @return
         */
        public Builder buildConnections() {
            //[yellowWeights[], purpleWeights[]]
            // TODO
            //List of lines
            Station s;
            Station s2;

            for (Line l : lineList
            ) {
                for (int i = 0; i < l.getStationsOnLine().size() - 1; i++) {
                    s = l.getStationsOnLine().get(i);
                    s2 = l.getStationsOnLine().get(i + 1);
                    Connection connection = new Connection(s, s2);
                    connection.setLine(l);
                    connectionSet.add(connection);

                }
            }
            return this;
        }

        public Builder addWeightsToConnections(double[] weights, int lineNumber) {
            Station s;
            Station s2;

            Line l = lineList.get(lineNumber);

            for (int i = 0; i <l.getStationsOnLine().size() - 1; i++) {
                s = l.getStationsOnLine().get(i);
                s2 = l.getStationsOnLine().get(i + 1);
                Connection connection = new Connection(s, s2, weights[i], l);
                connection.setLine(l);
                connectionSet.add(connection);
            }
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
            for (Station s : stationSet) {
                graph.addVertex(s);
            }
            for (Connection c : connectionSet) {

                graph.addEdge(c);
            }

            // TODO
            System.out.println("Transportgraph Build method");
            return graph;
        }

    }
}
