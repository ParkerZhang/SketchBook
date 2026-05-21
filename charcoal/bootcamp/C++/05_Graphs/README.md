# Phase 5: Graph Theory

## Graph Representations
- **Adjacency List**: `vector<int> adj[N]` (Most common).
- **Adjacency Matrix**: `int adj[N][N]` (For small $N \le 500$).

## Core Algorithms
1.  **DFS (Depth First Search)**: Recursion-based traversal.
2.  **BFS (Breadth First Search)**: Queue-based traversal (finds shortest path in unweighted graphs).
3.  **Dijkstra**: Shortest path in weighted graphs (with priority queue, $O(E \log V)$).
4.  **Kruskal's / Prim's**: Minimum Spanning Tree ($O(E \log E)$).
5.  **Floyd-Warshall**: All-pairs shortest paths ($O(V^3)$).

## Graph Properties
- Connectivity, Bipartiteness, Cycles, DAGs (Topological Sort).
