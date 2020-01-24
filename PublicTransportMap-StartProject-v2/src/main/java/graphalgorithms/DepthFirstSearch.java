package graphalgorithms;

import model.TransportGraph;

public class DepthFirstSearch extends AbstractPathSearch{

    public DepthFirstSearch(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        edgeTo[startIndex] = -1;
        nodesVisited.add(graph.getStation(startIndex));
        depthFirstSearch(this.startIndex);
        pathTo(endIndex);


    }

    private void depthFirstSearch(int v) {
        if (marked[v]) return;
        

        marked[v] = true;
        for (int w : graph.getAdjacentVertices(v)) {
            if (!marked[w]) {
                nodesVisited.add(graph.getStation(w));
                edgeTo[w] = v;

                depthFirstSearch(w);
            }
        }
    }
}
