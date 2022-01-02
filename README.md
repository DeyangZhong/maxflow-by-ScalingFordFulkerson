# maxflow
## Overview
This project aids in the computation of a network flow's maximum flow. Bipartite graphs, mesh graphs, and random graphs are the three types of graphs, each with its own java file to construct them.
The "Ford-Fulkerson" method is a general but unsuccessful algorithm, and my project employs the "scaling Ford-Fulkerson" technique, which improves significantly.
## usage
There are four .sh files in "maxflow/TestSet/". The "run.sh" allows you to build graphs in a single step. The other three files contains specific requirements of graphs.
The "maxflow/scalingFordFulkerson.java" computes the max-flow of a graph and can also print the details of each edges in the graph.
