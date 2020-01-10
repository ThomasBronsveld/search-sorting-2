package graphalgorithms;

import model.Station;
import model.TransportGraph;

import java.util.LinkedList;
import java.util.Queue;

public class BreadhFirstSearch extends AbstractPathSearch{

    public BreadhFirstSearch(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }


    //Source Sedgeick book p. 520
    @Override
    public void search() {
        //CHeck ff of dit in de volgorde van sedgewick nog altijd werkt
        //Zo ook nog even commentaar erbij zetten.
        Queue<Integer> queue = new LinkedList<Integer>();
        int s = startIndex;

        queue.add(s);
        //Mark the source
        marked[s] = true;
        nodesVisited.add(graph.getStation(s));

        while (!queue.isEmpty()) {
            int v = queue.remove();

            for (int w : graph.getAdjacentVertices(v)) {
                Station station = graph.getStation(w);
                if (!nodesVisited.contains(station)) {
                    nodesVisited.add(graph.getStation(w));
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.add(w);
                }
            }
        }
        //Why this?
        //Endindex is 1 te hoog
        pathTo(endIndex);
    }

}
