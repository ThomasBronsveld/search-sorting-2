package graphalgorithms;

import model.TransportGraph;

public class DepthFirstSearch extends AbstractPathSearch{

    public DepthFirstSearch(TransportGraph graph, String start, String end) {
        super(graph, start, end);
    }

    @Override
    public void search() {
        depthFirstSearch(this.startIndex);
        pathTo(endIndex);
    }

    private void depthFirstSearch(int v) {
        if (marked[v]) return;

        marked[v] = true;

        for (int a : graph.getAdjacentVertices(v)) {
            if (!marked[a]) {
                nodesVisited.add(graph.getStation(a));
                edgeTo[a] = v;

                // Recursive
                depthFirstSearch(a);
            }
        }
    }
}
